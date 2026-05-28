import { Component, NgZone, OnInit, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Subscription, filter, tap } from 'rxjs';
import { NgbDropdownModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import dayjs from 'dayjs/esm';
import isoWeek from 'dayjs/esm/plugin/isoWeek';

import SharedModule from 'app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAppointement } from '../appointement.model';
import { statusAppointement } from 'app/entities/enumerations/status-appointement.model';
import { typeAppointement } from 'app/entities/enumerations/type-appointement.model';

import { AppointementService } from '../service/appointement.service';
import { AppointementDeleteDialogComponent } from '../delete/appointement-delete-dialog.component';
import { APPOINTEMENT_SAVED_EVENT, AppointementUpdateComponent } from '../update/appointement-update.component';

dayjs.extend(isoWeek);

interface DayCell {
  date: dayjs.Dayjs;
  label: string; // MON / TUE …
  dayNumber: number;
  isToday: boolean;
  appointments: IAppointement[];
}

const STATUS_OPTIONS: { value: 'ALL' | keyof typeof statusAppointement; label: string }[] = [
  { value: 'ALL', label: 'Tous les statuts' },
  { value: 'SCHEDULED', label: 'Planifié' },
  { value: 'CONFIRMED', label: 'Confirmé' },
  { value: 'INPROGRESS', label: 'En cours' },
  { value: 'COMPLETED', label: 'Terminé' },
  { value: 'CANCELED', label: 'Annulé' },
];

@Component({
  selector: 'jhi-appointement',
  templateUrl: './appointement.component.html',
  styleUrl: './appointement.component.scss',
  imports: [RouterModule, FormsModule, SharedModule, NgbDropdownModule],
})
export class AppointementComponent implements OnInit {
  subscription: Subscription | null = null;
  appointements = signal<IAppointement[]>([]);
  isLoading = false;

  weekStart = signal<dayjs.Dayjs>(dayjs().startOf('isoWeek'));
  selectedStatus = signal<'ALL' | keyof typeof statusAppointement>('ALL');

  readonly statusOptions = STATUS_OPTIONS;

  weekRangeLabel = computed(() => {
    const start = this.weekStart();
    const end = start.add(6, 'day');
    if (start.month() === end.month()) {
      return `${start.format('MMM D')} - ${end.format('D, YYYY')}`;
    }
    return `${start.format('MMM D')} - ${end.format('MMM D, YYYY')}`;
  });

  selectedStatusLabel = computed(() => this.statusOptions.find(o => o.value === this.selectedStatus())?.label ?? 'Tous les statuts');

  days = computed<DayCell[]>(() => {
    const start = this.weekStart();
    const today = dayjs().startOf('day');
    const filteredAppointments = this.filteredAppointments();
    const labels = ['LUN', 'MAR', 'MER', 'JEU', 'VEN', 'SAM', 'DIM'];

    return Array.from({ length: 7 }).map((_, i) => {
      const date = start.add(i, 'day');
      const dayApps = filteredAppointments
        .filter(a => a.appointementDate && dayjs(a.appointementDate).isSame(date, 'day'))
        .sort((a, b) => (a.appointementTime ?? '').localeCompare(b.appointementTime ?? ''));
      return {
        date,
        label: labels[i],
        dayNumber: date.date(),
        isToday: date.isSame(today, 'day'),
        appointments: dayApps,
      };
    });
  });

  filteredAppointments = computed(() => {
    const status = this.selectedStatus();
    if (status === 'ALL') return this.appointements();
    return this.appointements().filter(a => a.status === status);
  });

  public readonly router = inject(Router);
  protected readonly appointementService = inject(AppointementService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: IAppointement): number => this.appointementService.getAppointementIdentifier(item);
  trackDay = (item: DayCell): string => item.date.format('YYYY-MM-DD');

  ngOnInit(): void {
    this.load();
  }

  previousWeek(): void {
    this.weekStart.update(d => d.subtract(7, 'day'));
  }

  nextWeek(): void {
    this.weekStart.update(d => d.add(7, 'day'));
  }

  goToday(): void {
    this.weekStart.set(dayjs().startOf('isoWeek'));
  }

  selectStatus(value: 'ALL' | keyof typeof statusAppointement): void {
    this.selectedStatus.set(value);
  }

  openNewAppointmentDialog(): void {
    const modalRef = this.modalService.open(AppointementUpdateComponent, { size: 'lg', backdrop: 'static', scrollable: true });
    modalRef.closed
      .pipe(
        filter(reason => reason === APPOINTEMENT_SAVED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  openEditDialog(appointement: IAppointement): void {
    const modalRef = this.modalService.open(AppointementUpdateComponent, { size: 'lg', backdrop: 'static', scrollable: true });
    modalRef.componentInstance.appointement = appointement;
    modalRef.closed
      .pipe(
        filter(reason => reason === APPOINTEMENT_SAVED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  markAsComplete(appointement: IAppointement): void {
    if (appointement.status === 'COMPLETED') return;
    this.appointementService.partialUpdate({ id: appointement.id, status: 'COMPLETED' }).subscribe({
      next: () => this.load(),
    });
  }

  patientName(app: IAppointement): string {
    if (!app.patient) return `Patient #${app.patientId ?? '-'}`;
    const first = app.patient.firstName ?? '';
    const last = app.patient.nom ?? app.patient.lastName ?? '';
    const full = `${first} ${last}`.trim();
    return full || `Patient #${app.patient.id}`;
  }

  typeLabel(type?: keyof typeof typeAppointement | null): string {
    switch (type) {
      case 'CONSULTATION':
        return 'Consultation';
      case 'FOLLOW_UP':
        return 'Suivi';
      case 'EMERGENCY':
        return 'Urgence';
      case 'ROUTINE_CHECKUP':
        return 'Contrôle de routine';
      case 'PROCEDURE':
        return 'Procédure';
      default:
        return '';
    }
  }

  statusLabel(status?: keyof typeof statusAppointement | null): string {
    switch (status) {
      case 'SCHEDULED':
        return 'planifié';
      case 'CONFIRMED':
        return 'confirmé';
      case 'INPROGRESS':
        return 'en cours';
      case 'COMPLETED':
        return 'terminé';
      case 'CANCELED':
        return 'annulé';
      default:
        return '';
    }
  }

  statusClass(status?: keyof typeof statusAppointement | null): string {
    return `status-badge status-${(status ?? 'unknown').toLowerCase()}`;
  }

  delete(appointement: IAppointement): void {
    const modalRef = this.modalService.open(AppointementDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.appointement = appointement;
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  load(): void {
    this.isLoading = true;
    this.appointementService.query({ size: 1000, sort: 'appointementDate,asc' }).subscribe({
      next: res => {
        this.appointements.set(res.body ?? []);
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }
}
