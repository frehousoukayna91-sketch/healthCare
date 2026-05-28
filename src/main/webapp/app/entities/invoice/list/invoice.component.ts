import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';

import { IInvoice } from '../invoice.model';
import { InvoiceService } from '../service/invoice.service';
import { InvoiceStatus } from 'app/entities/enumerations/invoice-status.model';

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
    { value: 'ALL', label: 'All Status' },
    { value: InvoiceStatus.DRAFT, label: 'Draft' },
    { value: InvoiceStatus.SENT, label: 'Sent' },
    { value: InvoiceStatus.PAID, label: 'Paid' },
    { value: InvoiceStatus.OVERDUE, label: 'Overdue' },
    { value: InvoiceStatus.CANCELLED, label: 'Cancelled' },
  ];

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

  trackId = (item: IInvoice): number => this.invoiceService.getInvoiceIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  statusLabel(status: string | null | undefined): string {
    if (!status) return '';
    return status.toLowerCase();
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
    this.router.navigate(['/invoice/new']);
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
