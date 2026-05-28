import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPatient, NewPatient } from '../patient.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPatient for edit and NewPatientFormGroupInput for create.
 */
type PatientFormGroupInput = IPatient | PartialWithRequiredKeyOf<NewPatient>;

type PatientFormDefaults = Pick<NewPatient, 'id'>;

type PatientFormGroupContent = {
  id: FormControl<IPatient['id'] | NewPatient['id']>;
  nom: FormControl<IPatient['nom']>;
  lastName: FormControl<IPatient['lastName']>;
  firstName: FormControl<IPatient['firstName']>;
  dateBirth: FormControl<IPatient['dateBirth']>;
  phone: FormControl<IPatient['phone']>;
  email: FormControl<IPatient['email']>;
  address: FormControl<IPatient['address']>;
  bloodType: FormControl<IPatient['bloodType']>;
  allergies: FormControl<IPatient['allergies']>;
  notes: FormControl<IPatient['notes']>;
  emergencyContactName: FormControl<IPatient['emergencyContactName']>;
  emeregencyContactPhone: FormControl<IPatient['emeregencyContactPhone']>;
  chronicdiseases: FormControl<IPatient['chronicdiseases']>;
  medicalHistory: FormControl<IPatient['medicalHistory']>;
};

export type PatientFormGroup = FormGroup<PatientFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PatientFormService {
  createPatientFormGroup(patient: PatientFormGroupInput = { id: null }): PatientFormGroup {
    const patientRawValue = {
      ...this.getFormDefaults(),
      ...patient,
    };
    return new FormGroup<PatientFormGroupContent>({
      id: new FormControl(
        { value: patientRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nom: new FormControl(patientRawValue.nom, [Validators.required]),
      lastName: new FormControl(patientRawValue.lastName),
      firstName: new FormControl(patientRawValue.firstName, [Validators.required]),
      dateBirth: new FormControl(patientRawValue.dateBirth, [Validators.required]),
      phone: new FormControl(patientRawValue.phone, [Validators.required]),
      email: new FormControl(patientRawValue.email),
      address: new FormControl(patientRawValue.address),
      bloodType: new FormControl(patientRawValue.bloodType),
      allergies: new FormControl(patientRawValue.allergies),
      notes: new FormControl(patientRawValue.notes),
      emergencyContactName: new FormControl(patientRawValue.emergencyContactName),
      emeregencyContactPhone: new FormControl(patientRawValue.emeregencyContactPhone),
      chronicdiseases: new FormControl(patientRawValue.chronicdiseases),
      medicalHistory: new FormControl(patientRawValue.medicalHistory),
    });
  }

  getPatient(form: PatientFormGroup): IPatient | NewPatient {
    return form.getRawValue() as IPatient | NewPatient;
  }

  resetForm(form: PatientFormGroup, patient: PatientFormGroupInput): void {
    const patientRawValue = { ...this.getFormDefaults(), ...patient };
    form.reset(
      {
        ...patientRawValue,
        id: { value: patientRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PatientFormDefaults {
    return {
      id: null,
    };
  }
}
