import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';

import SharedModule from 'app/shared/shared.module';

import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';

import { IInvoice, NewInvoice } from '../invoice.model';
import { InvoiceService } from '../service/invoice.service';
import { InvoiceStatus } from 'app/entities/enumerations/invoice-status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

export const INVOICE_SAVED_EVENT = 'saved';

@Component({
  selector: 'jhi-invoice-update',
  templateUrl: './invoice-update.component.html',
  styleUrl: './invoice-update.component.scss',
  imports: [SharedModule, ReactiveFormsModule],
})
export class InvoiceUpdateComponent implements OnInit {
  isSaving = false;
  invoice: IInvoice | null = null;
  patientsSharedCollection: IPatient[] = [];

  readonly statusOptions = [
    { value: InvoiceStatus.DRAFT, label: 'Brouillon' },
    { value: InvoiceStatus.SENT, label: 'Envoyée' },
    { value: InvoiceStatus.PAID, label: 'Payée' },
    { value: InvoiceStatus.OVERDUE, label: 'En retard' },
    { value: InvoiceStatus.CANCELLED, label: 'Annulée' },
  ];

  readonly paymentMethodOptions = [
    { value: PaymentMethod.CASH, label: 'Espèces' },
    { value: PaymentMethod.CARD, label: 'Carte' },
    { value: PaymentMethod.INSURANCE, label: 'Assurance' },
    { value: PaymentMethod.BANK_TRANSFER, label: 'Virement' },
    { value: PaymentMethod.OTHER, label: 'Autre' },
  ];

  protected fb = inject(FormBuilder);
  protected invoiceService = inject(InvoiceService);
  protected patientService = inject(PatientService);
  protected activeModal = inject(NgbActiveModal, { optional: true });

  editForm: FormGroup = this.fb.group({
    id: [null as number | null],
    invoiceNumber: [''],
    patientId: [null as number | null, Validators.required],
    patientName: [''],
    date: ['', Validators.required],
    dueDate: [''],
    status: [InvoiceStatus.DRAFT, Validators.required],
    paymentMethod: [PaymentMethod.CASH],
    tax: [0],
    items: this.fb.array([]),
  });

  ngOnInit(): void {
    if (this.invoice) {
      this.patchFromInvoice(this.invoice);
    } else {
      this.editForm.patchValue({
        date: dayjs().format('YYYY-MM-DD'),
        dueDate: dayjs().add(15, 'day').format('YYYY-MM-DD'),
        invoiceNumber: this.generateInvoiceNumber(),
      });
      this.addItem();
    }
    this.loadPatients();
  }

  get items(): FormArray<FormGroup> {
    return this.editForm.get('items') as FormArray<FormGroup>;
  }

  addItem(): void {
    this.items.push(
      this.fb.group({
        id: [null as number | null],
        description: ['', Validators.required],
        quantity: [1, [Validators.required, Validators.min(0)]],
        unitPrice: [0, [Validators.required, Validators.min(0)]],
      }),
    );
  }

  removeItem(index: number): void {
    this.items.removeAt(index);
  }

  itemTotal(group: FormGroup): number {
    const qty = Number(group.get('quantity')?.value) || 0;
    const price = Number(group.get('unitPrice')?.value) || 0;
    return qty * price;
  }

  subtotal(): number {
    return this.items.controls.reduce((sum, g) => sum + this.itemTotal(g as FormGroup), 0);
  }

  taxAmount(): number {
    const taxPct = Number(this.editForm.get('tax')?.value) || 0;
    return (this.subtotal() * taxPct) / 100;
  }

  totalAmount(): number {
    return this.subtotal() + this.taxAmount();
  }

  patientLabel(patient: IPatient): string {
    const first = patient.firstName ?? '';
    const last = patient.nom ?? patient.lastName ?? '';
    const full = `${first} ${last}`.trim();
    return full || `Patient #${patient.id}`;
  }

  onPatientChange(patientId: number | null): void {
    const patient = this.patientsSharedCollection.find(p => p.id === patientId);
    if (patient) {
      this.editForm.patchValue({ patientName: this.patientLabel(patient) });
    }
  }

  previousState(): void {
    this.activeModal?.dismiss();
  }

  save(): void {
    if (this.editForm.invalid) {
      this.editForm.markAllAsTouched();
      return;
    }
    this.isSaving = true;

    const raw = this.editForm.getRawValue();
    const itemsPayload = (raw.items as any[]).map(it => ({
      id: it.id,
      description: it.description,
      quantity: Number(it.quantity) || 0,
      unitPrice: Number(it.unitPrice) || 0,
      total: (Number(it.quantity) || 0) * (Number(it.unitPrice) || 0),
    }));

    const payload: IInvoice | NewInvoice = {
      id: raw.id,
      invoiceNumber: raw.invoiceNumber || this.generateInvoiceNumber(),
      patientId: raw.patientId,
      patientName: raw.patientName || this.lookupPatientName(raw.patientId),
      date: raw.date ? dayjs(raw.date) : null,
      dueDate: raw.dueDate ? dayjs(raw.dueDate) : null,
      subtotal: this.subtotal(),
      tax: this.taxAmount(),
      total: this.totalAmount(),
      status: raw.status,
      paymentMethod: raw.paymentMethod,
      items: itemsPayload,
    } as IInvoice | NewInvoice;

    const obs: Observable<HttpResponse<IInvoice>> =
      payload.id !== null && payload.id !== undefined
        ? this.invoiceService.update(payload as IInvoice)
        : this.invoiceService.create(payload as NewInvoice);

    obs.pipe(finalize(() => (this.isSaving = false))).subscribe({
      next: () => this.activeModal?.close(INVOICE_SAVED_EVENT),
      error: () => {
        /* alert pipeline handles display */
      },
    });
  }

  protected patchFromInvoice(invoice: IInvoice): void {
    this.editForm.patchValue({
      id: invoice.id,
      invoiceNumber: invoice.invoiceNumber,
      patientId: invoice.patientId ?? null,
      patientName: invoice.patientName ?? '',
      date: invoice.date ? invoice.date.format('YYYY-MM-DD') : '',
      dueDate: invoice.dueDate ? invoice.dueDate.format('YYYY-MM-DD') : '',
      status: invoice.status ?? InvoiceStatus.DRAFT,
      paymentMethod: invoice.paymentMethod ?? PaymentMethod.CASH,
      tax:
        invoice.subtotal && Number(invoice.subtotal) > 0
          ? Math.round(((Number(invoice.tax) || 0) / Number(invoice.subtotal)) * 10000) / 100
          : 0,
    });
    this.items.clear();
    (invoice.items ?? []).forEach(it => {
      this.items.push(
        this.fb.group({
          id: [it.id],
          description: [it.description ?? '', Validators.required],
          quantity: [it.quantity ?? 1, [Validators.required, Validators.min(0)]],
          unitPrice: [it.unitPrice ?? 0, [Validators.required, Validators.min(0)]],
        }),
      );
    });
    if (this.items.length === 0) {
      this.addItem();
    }
  }

  protected loadPatients(): void {
    this.patientService
      .query({ size: 1000 })
      .pipe(map((res: HttpResponse<IPatient[]>) => res.body ?? []))
      .subscribe(patients => (this.patientsSharedCollection = patients));
  }

  protected lookupPatientName(patientId: number | null): string {
    if (patientId == null) return '';
    const patient = this.patientsSharedCollection.find(p => p.id === patientId);
    return patient ? this.patientLabel(patient) : '';
  }

  protected generateInvoiceNumber(): string {
    const ymd = dayjs().format('YYYYMMDD');
    const rand = Math.floor(Math.random() * 900 + 100);
    return `INV-${ymd}${rand}`;
  }
}
