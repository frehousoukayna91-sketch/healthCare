import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { forkJoin } from 'rxjs';
import dayjs from 'dayjs/esm';

import SharedModule from 'app/shared/shared.module';
import { FormatMediumDatePipe } from 'app/shared/date';

import { IPatient } from '../patient.model';
import { PatientService } from '../service/patient.service';
import { AppointementService } from 'app/entities/appointement/service/appointement.service';
import { PrescriptionService } from 'app/entities/prescription/service/prescription.service';
import { InvoiceService } from 'app/entities/invoice/service/invoice.service';

import { IAppointement } from 'app/entities/appointement/appointement.model';
import { IPrescription } from 'app/entities/prescription/prescription.model';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { InvoiceStatus } from 'app/entities/enumerations/invoice-status.model';

type Tab = 'appointments' | 'prescriptions' | 'invoices';

@Component({
  selector: 'jhi-patient-history',
  templateUrl: './patient-history.component.html',
  styleUrl: './patient-history.component.scss',
  imports: [SharedModule, RouterModule, CommonModule, FormatMediumDatePipe],
})
export class PatientHistoryComponent implements OnInit {
  patient = signal<IPatient | null>(null);
  appointments = signal<IAppointement[]>([]);
  prescriptions = signal<IPrescription[]>([]);
  invoices = signal<IInvoice[]>([]);
  loading = signal(true);
  activeTab = signal<Tab>('appointments');

  totalAppointments = computed(() => this.appointments().length);
  completedVisits = computed(() => this.appointments().filter(a => a.status === 'COMPLETED').length);
  totalPrescriptions = computed(() => this.prescriptions().length);
  totalPaid = computed(() =>
    this.invoices()
      .filter(i => i.status === InvoiceStatus.PAID)
      .reduce((sum, i) => sum + (i.total ?? 0), 0),
  );

  private readonly route = inject(ActivatedRoute);
  private readonly patientService = inject(PatientService);
  private readonly appointementService = inject(AppointementService);
  private readonly prescriptionService = inject(PrescriptionService);
  private readonly invoiceService = inject(InvoiceService);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.params['id']);
    if (!id) {
      this.loading.set(false);
      return;
    }

    forkJoin({
      patient: this.patientService.find(id),
      appointments: this.appointementService.query({ size: 1000 }),
      prescriptions: this.prescriptionService.query({ size: 1000 }),
      invoices: this.invoiceService.query({ size: 1000 }),
    }).subscribe({
      next: ({ patient, appointments, prescriptions, invoices }) => {
        this.patient.set(patient.body);
        this.appointments.set(
          (appointments.body ?? [])
            .filter(a => a.patientId === id)
            .sort((a, b) => {
              const da = a.appointementDate ? dayjs(a.appointementDate).valueOf() : 0;
              const db = b.appointementDate ? dayjs(b.appointementDate).valueOf() : 0;
              return db - da;
            }),
        );
        this.prescriptions.set(
          (prescriptions.body ?? [])
            .filter(p => p.patientId === id)
            .sort((a, b) => {
              const da = a.prescriptionDate ? dayjs(a.prescriptionDate).valueOf() : 0;
              const db = b.prescriptionDate ? dayjs(b.prescriptionDate).valueOf() : 0;
              return db - da;
            }),
        );
        this.invoices.set(
          (invoices.body ?? [])
            .filter(inv => inv.patientId === id)
            .sort((a, b) => {
              const da = a.date ? dayjs(a.date).valueOf() : 0;
              const db = b.date ? dayjs(b.date).valueOf() : 0;
              return db - da;
            }),
        );
        this.loading.set(false);
      },
      error: () => this.loading.set(false),
    });
  }

  getAge(p: IPatient | null): number | null {
    if (!p?.dateBirth) return null;
    return dayjs().diff(dayjs(p.dateBirth), 'year');
  }

  getInitials(p: IPatient | null): string {
    if (!p) return '';
    const f = (p.firstName ?? '').charAt(0).toUpperCase();
    const l = (p.nom ?? p.lastName ?? '').charAt(0).toUpperCase();
    return `${f}${l}`;
  }

  setTab(t: Tab): void {
    this.activeTab.set(t);
  }

  previousState(): void {
    window.history.back();
  }
}
