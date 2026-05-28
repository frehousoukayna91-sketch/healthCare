import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgbDropdownModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { filter, tap } from 'rxjs';

import SharedModule from 'app/shared/shared.module';

import { IInvoice } from '../invoice.model';
import { InvoiceService } from '../service/invoice.service';
import { InvoiceStatus } from 'app/entities/enumerations/invoice-status.model';
import { INVOICE_SAVED_EVENT, InvoiceUpdateComponent } from '../update/invoice-update.component';

@Component({
  selector: 'jhi-invoice',
  templateUrl: './invoice.component.html',
  styleUrl: './invoice.component.scss',
  imports: [RouterModule, FormsModule, SharedModule, NgbDropdownModule],
})
export class InvoiceComponent implements OnInit {
  invoices = signal<IInvoice[]>([]);
  searchTerm = signal<string>('');
  statusFilter = signal<string>('ALL');
  isLoading = false;

  readonly statusOptions: { value: string; label: string }[] = [
    { value: 'ALL', label: 'Tous les statuts' },
    { value: InvoiceStatus.DRAFT, label: 'Brouillon' },
    { value: InvoiceStatus.SENT, label: 'Envoyée' },
    { value: InvoiceStatus.PAID, label: 'Payée' },
    { value: InvoiceStatus.OVERDUE, label: 'En retard' },
    { value: InvoiceStatus.CANCELLED, label: 'Annulée' },
  ];

  private readonly statusLabels: Record<string, string> = {
    [InvoiceStatus.DRAFT]: 'brouillon',
    [InvoiceStatus.SENT]: 'envoyée',
    [InvoiceStatus.PAID]: 'payée',
    [InvoiceStatus.OVERDUE]: 'en retard',
    [InvoiceStatus.CANCELLED]: 'annulée',
  };

  filteredInvoices = computed(() => {
    const term = this.searchTerm().trim().toLowerCase();
    const status = this.statusFilter();
    return this.invoices().filter(inv => {
      if (status !== 'ALL' && inv.status !== status) {
        return false;
      }
      if (!term) return true;
      const num = (inv.invoiceNumber ?? '').toLowerCase();
      const patient = (inv.patientName ?? '').toLowerCase();
      return num.includes(term) || patient.includes(term);
    });
  });

  totalPaid = computed(() =>
    this.invoices()
      .filter(i => i.status === InvoiceStatus.PAID)
      .reduce((sum, i) => sum + (Number(i.total) || 0), 0),
  );

  totalPending = computed(() =>
    this.invoices()
      .filter(i => i.status === InvoiceStatus.SENT || i.status === InvoiceStatus.DRAFT)
      .reduce((sum, i) => sum + (Number(i.total) || 0), 0),
  );

  totalOverdue = computed(() =>
    this.invoices()
      .filter(i => i.status === InvoiceStatus.OVERDUE)
      .reduce((sum, i) => sum + (Number(i.total) || 0), 0),
  );

  public readonly router = inject(Router);
  protected readonly invoiceService = inject(InvoiceService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected readonly modalService = inject(NgbModal);

  trackId = (item: IInvoice): number => this.invoiceService.getInvoiceIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  statusLabel(status: string | null | undefined): string {
    if (!status) return '';
    return this.statusLabels[status] ?? status.toLowerCase();
  }

  statusClass(status: string | null | undefined): string {
    if (!status) return '';
    switch (status) {
      case InvoiceStatus.PAID:
        return 'status-pill status-paid';
      case InvoiceStatus.SENT:
        return 'status-pill status-sent';
      case InvoiceStatus.OVERDUE:
        return 'status-pill status-overdue';
      case InvoiceStatus.DRAFT:
        return 'status-pill status-draft';
      case InvoiceStatus.CANCELLED:
        return 'status-pill status-cancelled';
      default:
        return 'status-pill';
    }
  }

  openNewInvoice(): void {
    const modalRef = this.modalService.open(InvoiceUpdateComponent, { size: 'lg', backdrop: 'static', scrollable: true });
    modalRef.closed
      .pipe(
        filter(reason => reason === INVOICE_SAVED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  openEditInvoice(invoice: IInvoice): void {
    this.invoiceService.find(invoice.id).subscribe(res => {
      const full = res.body ?? invoice;
      const modalRef = this.modalService.open(InvoiceUpdateComponent, { size: 'lg', backdrop: 'static', scrollable: true });
      modalRef.componentInstance.invoice = full;
      modalRef.closed
        .pipe(
          filter(reason => reason === INVOICE_SAVED_EVENT),
          tap(() => this.load()),
        )
        .subscribe();
    });
  }

  protected load(): void {
    this.isLoading = true;
    this.invoiceService.query({ size: 1000, sort: 'date,desc' }).subscribe({
      next: (res: HttpResponse<IInvoice[]>) => {
        this.invoices.set(res.body ?? []);
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }
}
