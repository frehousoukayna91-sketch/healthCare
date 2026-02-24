import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';
import { statusAppointement } from 'app/entities/enumerations/status-appointement.model';
import { typeAppointement } from 'app/entities/enumerations/type-appointement.model';
import { AppointementService } from '../service/appointement.service';
import { IAppointement } from '../appointement.model';
import { AppointementFormGroup, AppointementFormService } from './appointement-form.service';

@Component({
  selector: 'jhi-appointement-update',
  templateUrl: './appointement-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AppointementUpdateComponent implements OnInit {
  isSaving = false;
  appointement: IAppointement | null = null;
  statusAppointementValues = Object.keys(statusAppointement);
  typeAppointementValues = Object.keys(typeAppointement);

  patientsSharedCollection: IPatient[] = [];

  protected appointementService = inject(AppointementService);
  protected appointementFormService = inject(AppointementFormService);
  protected patientService = inject(PatientService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AppointementFormGroup = this.appointementFormService.createAppointementFormGroup();

  comparePatient = (o1: IPatient | null, o2: IPatient | null): boolean => this.patientService.comparePatient(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appointement }) => {
      this.appointement = appointement;
      if (appointement) {
        this.updateForm(appointement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appointement = this.appointementFormService.getAppointement(this.editForm);
    if (appointement.id !== null) {
      this.subscribeToSaveResponse(this.appointementService.update(appointement));
    } else {
      this.subscribeToSaveResponse(this.appointementService.create(appointement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppointement>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(appointement: IAppointement): void {
    this.appointement = appointement;
    this.appointementFormService.resetForm(this.editForm, appointement);

    this.patientsSharedCollection = this.patientService.addPatientToCollectionIfMissing<IPatient>(
      this.patientsSharedCollection,
      appointement.patient,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.patientService
      .query()
      .pipe(map((res: HttpResponse<IPatient[]>) => res.body ?? []))
      .pipe(
        map((patients: IPatient[]) => this.patientService.addPatientToCollectionIfMissing<IPatient>(patients, this.appointement?.patient)),
      )
      .subscribe((patients: IPatient[]) => (this.patientsSharedCollection = patients));
  }
}
