import { Component, OnDestroy, OnInit, inject, signal, computed } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { Observable, Subject, forkJoin, of } from 'rxjs';
import { catchError, map, takeUntil } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import SharedModule from 'app/shared/shared.module';
import HasAnyAuthorityDirective from 'app/shared/auth/has-any-authority.directive';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Authority } from 'app/config/authority.constants';

import { PatientService } from 'app/entities/patient/service/patient.service';
import { AppointementService } from 'app/entities/appointement/service/appointement.service';
import { InvoiceService } from 'app/entities/invoice/service/invoice.service';
import { PrescriptionService } from 'app/entities/prescription/service/prescription.service';

import { IPatient } from 'app/entities/patient/patient.model';
import { IAppointement } from 'app/entities/appointement/appointement.model';
import { IInvoice } from 'app/entities/invoice/invoice.model';
import { IPrescription } from 'app/entities/prescription/prescription.model';
import { InvoiceStatus } from 'app/entities/enumerations/invoice-status.model';

const PATIENT_ROLES = [Authority.MEDECIN, Authority.ADMIN];
const APPOINTEMENT_ROLES = [Authority.MEDECIN, Authority.SECRETAIRE, Authority.ADMIN];
const INVOICE_ROLES = [Authority.SECRETAIRE, Authority.ADMIN];
const PRESCRIPTION_ROLES = [Authority.MEDECIN, Authority.ADMIN];

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [SharedModule, RouterModule, CommonModule, HasAnyAuthorityDirective],
})
export default class HomeComponent implements OnInit, OnDestroy {
  account = signal<Account | null>(null);

  loading = signal(true);
  totalPatients = signal(0);
  todayAppointments = signal<IAppointement[]>([]);
  pendingInvoices = signal<IInvoice[]>([]);
  monthlyRevenue = signal(0);
  recentPrescriptions = signal<IPrescription[]>([]);

  todayLabel = dayjs().format('dddd, D MMMM YYYY');
  monthLabel = dayjs().format('MMMM YYYY');

  todayCount = computed(() => this.todayAppointments().length);
  pendingCount = computed(() => this.pendingInvoices().length);
  pendingOutstanding = computed(() => this.pendingInvoices().reduce((sum, inv) => sum + (inv.total ?? 0), 0));

  private readonly destroy$ = new Subject<void>();

  private readonly accountService = inject(AccountService);
  private readonly router = inject(Router);
  private readonly patientService = inject(PatientService);
  private readonly appointementService = inject(AppointementService);
  private readonly invoiceService = inject(InvoiceService);
  private readonly prescriptionService = inject(PrescriptionService);

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => {
        this.account.set(account);
        if (account) {
          this.loadDashboard();
        }
      });
  }

  loadDashboard(): void {
    this.loading.set(true);

    const canPatient = this.accountService.hasAnyAuthority(PATIENT_ROLES);
    const canAppointement = this.accountService.hasAnyAuthority(APPOINTEMENT_ROLES);
    const canInvoice = this.accountService.hasAnyAuthority(INVOICE_ROLES);
    const canPrescription = this.accountService.hasAnyAuthority(PRESCRIPTION_ROLES);

    forkJoin({
      patients: canPatient ? this.safe(this.patientService.query({ size: 1000 })) : of<IPatient[]>([]),
      appointments: canAppointement ? this.safe(this.appointementService.query({ size: 1000 })) : of<IAppointement[]>([]),
      invoices: canInvoice ? this.safe(this.invoiceService.query({ size: 1000 })) : of<IInvoice[]>([]),
      prescriptions: canPrescription
        ? this.safe(this.prescriptionService.query({ size: 1000, sort: ['prescriptionDate,desc'] }))
        : of<IPrescription[]>([]),
    })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: ({ patients, appointments, invoices, prescriptions }) => {
          this.totalPatients.set(patients.length);

          const today = dayjs().startOf('day');
          this.todayAppointments.set(appointments.filter(a => a.appointementDate && dayjs(a.appointementDate).isSame(today, 'day')));

          this.pendingInvoices.set(
            invoices.filter(i => i.status === InvoiceStatus.SENT || i.status === InvoiceStatus.DRAFT || i.status === InvoiceStatus.OVERDUE),
          );

          const startOfMonth = dayjs().startOf('month');
          const endOfMonth = dayjs().endOf('month');
          const monthRevenue = invoices
            .filter(
              i => i.status === InvoiceStatus.PAID && i.date && dayjs(i.date).isAfter(startOfMonth) && dayjs(i.date).isBefore(endOfMonth),
            )
            .reduce((sum, inv) => sum + (inv.total ?? 0), 0);
          this.monthlyRevenue.set(monthRevenue);

          const sortedPrescriptions = prescriptions
            .slice()
            .sort((a, b) => {
              const da = a.prescriptionDate ? dayjs(a.prescriptionDate).valueOf() : 0;
              const db = b.prescriptionDate ? dayjs(b.prescriptionDate).valueOf() : 0;
              return db - da;
            })
            .slice(0, 4);
          this.recentPrescriptions.set(sortedPrescriptions);

          this.loading.set(false);
        },
        error: () => this.loading.set(false),
      });
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private safe<T>(source$: Observable<HttpResponse<T[]>>): Observable<T[]> {
    return source$.pipe(
      map(res => res.body ?? []),
      catchError(() => of<T[]>([])),
    );
  }
}
