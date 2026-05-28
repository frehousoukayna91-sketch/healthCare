import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import dayjs from 'dayjs/esm';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';

import { IPrescription } from '../prescription.model';
import { PrescriptionService } from '../service/prescription.service';
import { PrescriptionFormGroup, PrescriptionFormService } from './prescription-form.service';

export const PRESCRIPTION_SAVED_EVENT = 'saved';

@Component({
  selector: 'jhi-prescription-update',
  templateUrl: './prescription-update.component.html',
  styleUrl: './prescription-update.component.scss',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PrescriptionUpdateComponent implements OnInit {
  isSaving = false;
  prescription: IPrescription | null = null;
  patientsSharedCollection: IPatient[] = [];

  protected prescriptionService = inject(PrescriptionService);
  protected prescriptionFormService = inject(PrescriptionFormService);
  protected patientService = inject(PatientService);
  protected activatedRoute = inject(ActivatedRoute);
  protected activeModal = inject(NgbActiveModal, { optional: true });

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PrescriptionFormGroup = this.prescriptionFormService.createPrescriptionFormGroup();

  ngOnInit(): void {
    if (this.activeModal) {
      if (this.prescription) {
        this.updateForm(this.prescription);
      } else {
        this.editForm.patchValue({ prescriptionDate: dayjs().format('YYYY-MM-DD') as any });
        this.addMedication();
      }
      this.loadRelationshipsOptions();
      return;
    }

    this.activatedRoute.data.subscribe(({ prescription }) => {
      this.prescription = prescription;
      if (prescription) {
        this.updateForm(prescription);
      } else {
        this.editForm.patchValue({ prescriptionDate: dayjs().format('YYYY-MM-DD') as any });
        this.addMedication();
      }
      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    if (this.activeModal) {
      this.activeModal.dismiss();
    } else {
      window.history.back();
    }
  }

  addMedication(): void {
    this.editForm.controls.prescriptionItems.push(this.prescriptionFormService.createItemFormGroup());
  }

  removeMedication(index: number): void {
    this.editForm.controls.prescriptionItems.removeAt(index);
  }

  patientLabel(patient: IPatient): string {
    const first = patient.firstName ?? '';
    const last = patient.nom ?? patient.lastName ?? '';
    const full = `${first} ${last}`.trim();
    return full || `Patient #${patient.id}`;
  }

  save(): void {
    if (this.editForm.invalid) {
      this.editForm.markAllAsTouched();
      return;
    }
    this.isSaving = true;
    const prescription = this.prescriptionFormService.getPrescription(this.editForm);
    if (prescription.id !== null) {
      this.subscribeToSaveResponse(this.prescriptionService.update(prescription));
    } else {
      this.subscribeToSaveResponse(this.prescriptionService.create(prescription));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrescription>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    if (this.activeModal) {
      this.activeModal.close(PRESCRIPTION_SAVED_EVENT);
    } else {
      this.previousState();
    }
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(prescription: IPrescription): void {
    this.prescription = prescription;
    this.prescriptionFormService.resetForm(this.editForm, prescription);
    if (this.editForm.controls.prescriptionItems.length === 0) {
      this.addMedication();
    }
  }

  protected loadRelationshipsOptions(): void {
    this.patientService
      .query({ size: 1000 })
      .pipe(map((res: HttpResponse<IPatient[]>) => res.body ?? []))
      .subscribe(patients => (this.patientsSharedCollection = patients));
  }
}
