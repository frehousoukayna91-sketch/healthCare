import { Component, NgZone, OnInit, computed, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription, filter, tap } from 'rxjs';
import { NgbDropdownModule, NgbModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { FormsModule } from '@angular/forms';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';

import { IPrescription, IPrescriptionItem } from '../prescription.model';
import { PrescriptionService } from '../service/prescription.service';
import { PrescriptionDeleteDialogComponent } from '../delete/prescription-delete-dialog.component';
import { PRESCRIPTION_SAVED_EVENT, PrescriptionUpdateComponent } from '../update/prescription-update.component';
import { PrescriptionPrintDialogComponent } from '../print/prescription-print-dialog.component';

@Component({
  selector: 'jhi-prescription',
  templateUrl: './prescription.component.html',
  styleUrl: './prescription.component.scss',
  imports: [RouterModule, FormsModule, SharedModule, NgbDropdownModule],
})
export class PrescriptionComponent implements OnInit {
  subscription: Subscription | null = null;
  prescriptions = signal<IPrescription[]>([]);
  patients = signal<IPatient[]>([]);
  searchTerm = signal<string>('');
  isLoading = false;

  filteredPrescriptions = computed(() => {
    const term = this.searchTerm().trim().toLowerCase();
    const list = this.prescriptions();
    if (!term) return list;
    return list.filter(p => {
      const name = this.patientName(p).toLowerCase();
      const diag = (p.diagnosis ?? '').toLowerCase();
      return name.includes(term) || diag.includes(term);
    });
  });

  public readonly router = inject(Router);
  protected readonly prescriptionService = inject(PrescriptionService);
  protected readonly patientService = inject(PatientService);
  protected readonly activatedRoute = inject(ActivatedRoute);
  protected modalService = inject(NgbModal);
  protected ngZone = inject(NgZone);

  trackId = (item: IPrescription): number => this.prescriptionService.getPrescriptionIdentifier(item);

  ngOnInit(): void {
    this.loadPatients();
    this.load();
  }

  openNewPrescriptionDialog(): void {
    const modalRef = this.modalService.open(PrescriptionUpdateComponent, { size: 'lg', backdrop: 'static', scrollable: true });
    modalRef.closed
      .pipe(
        filter(reason => reason === PRESCRIPTION_SAVED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  openEditDialog(prescription: IPrescription): void {
    const modalRef = this.modalService.open(PrescriptionUpdateComponent, { size: 'lg', backdrop: 'static', scrollable: true });
    modalRef.componentInstance.prescription = prescription;
    modalRef.closed
      .pipe(
        filter(reason => reason === PRESCRIPTION_SAVED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  openPrintDialog(prescription: IPrescription, autoPrint = false): void {
    const modalRef = this.modalService.open(PrescriptionPrintDialogComponent, { size: 'lg', backdrop: 'static', scrollable: true });
    modalRef.componentInstance.prescription = prescription;
    if (autoPrint) {
      setTimeout(() => modalRef.componentInstance.print(), 250);
    }
  }

  delete(prescription: IPrescription): void {
    const modalRef = this.modalService.open(PrescriptionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.prescription = prescription;
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        tap(() => this.load()),
      )
      .subscribe();
  }

  patientName(p: IPrescription): string {
    if (p.patientId == null) return 'Patient';
    const patient = this.patients().find(pt => pt.id === p.patientId);
    if (!patient) return `Patient #${p.patientId}`;
    const first = patient.firstName ?? '';
    const last = patient.nom ?? patient.lastName ?? '';
    const full = `${first} ${last}`.trim();
    return full || `Patient #${patient.id}`;
  }

  patientInitial(p: IPrescription): string {
    const name = this.patientName(p).trim();
    return name.length > 0 ? name.charAt(0).toUpperCase() : 'P';
  }

  itemSummary(item: IPrescriptionItem): string {
    const name = item.medicationName ?? '';
    if (item.medicationDosage != null) {
      return `${name} - ${item.medicationDosage}mg`;
    }
    return name;
  }

  itemCount(p: IPrescription): number {
    return p.prescriptionItems?.length ?? 0;
  }

  protected load(): void {
    this.isLoading = true;
    this.prescriptionService.query({ size: 1000, sort: 'prescriptionDate,desc' }).subscribe({
      next: (res: HttpResponse<IPrescription[]>) => {
        this.prescriptions.set(res.body ?? []);
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  protected loadPatients(): void {
    this.patientService.query({ size: 1000 }).subscribe({
      next: (res: HttpResponse<IPatient[]>) => this.patients.set(res.body ?? []),
    });
  }
}
