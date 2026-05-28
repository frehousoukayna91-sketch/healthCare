import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPatient } from '../patient.model';
import { PatientService } from '../service/patient.service';
import { PatientFormGroup, PatientFormService } from './patient-form.service';

export const PATIENT_SAVED_EVENT = 'saved';

@Component({
  selector: 'jhi-patient-update',
  templateUrl: './patient-update.component.html',
  styleUrl: './patient-update.component.scss',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PatientUpdateComponent implements OnInit {
  isSaving = false;
  patient: IPatient | null = null;

  protected patientService = inject(PatientService);
  protected patientFormService = inject(PatientFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected activeModal = inject(NgbActiveModal, { optional: true });

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PatientFormGroup = this.patientFormService.createPatientFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ patient }) => {
      this.patient = patient;
      if (patient) {
        this.updateForm(patient);
      }
    });
  }

  previousState(): void {
    if (this.activeModal) {
      this.activeModal.dismiss();
    } else {
      window.history.back();
    }
  }

  save(): void {
    this.editForm.markAllAsTouched();
    if (this.editForm.invalid) {
      return;
    }
    this.isSaving = true;
    const patient = this.patientFormService.getPatient(this.editForm);
    if (patient.id !== null) {
      this.subscribeToSaveResponse(this.patientService.update(patient));
    } else {
      this.subscribeToSaveResponse(this.patientService.create(patient));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPatient>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    if (this.activeModal) {
      this.activeModal.close(PATIENT_SAVED_EVENT);
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

  protected updateForm(patient: IPatient): void {
    this.patient = patient;
    this.patientFormService.resetForm(this.editForm, patient);
  }
}
