import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAppointement, NewAppointement } from '../appointement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAppointement for edit and NewAppointementFormGroupInput for create.
 */
type AppointementFormGroupInput = IAppointement | PartialWithRequiredKeyOf<NewAppointement>;

type AppointementFormDefaults = Pick<NewAppointement, 'id'>;

type AppointementFormGroupContent = {
  id: FormControl<IAppointement['id'] | NewAppointement['id']>;
  patientId: FormControl<IAppointement['patientId']>;
  duration: FormControl<IAppointement['duration']>;
  status: FormControl<IAppointement['status']>;
  type: FormControl<IAppointement['type']>;
  patient: FormControl<IAppointement['patient']>;
};

export type AppointementFormGroup = FormGroup<AppointementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AppointementFormService {
  createAppointementFormGroup(appointement: AppointementFormGroupInput = { id: null }): AppointementFormGroup {
    const appointementRawValue = {
      ...this.getFormDefaults(),
      ...appointement,
    };
    return new FormGroup<AppointementFormGroupContent>({
      id: new FormControl(
        { value: appointementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      patientId: new FormControl(appointementRawValue.patientId),
      duration: new FormControl(appointementRawValue.duration),
      status: new FormControl(appointementRawValue.status),
      type: new FormControl(appointementRawValue.type),
      patient: new FormControl(appointementRawValue.patient),
    });
  }

  getAppointement(form: AppointementFormGroup): IAppointement | NewAppointement {
    return form.getRawValue() as IAppointement | NewAppointement;
  }

  resetForm(form: AppointementFormGroup, appointement: AppointementFormGroupInput): void {
    const appointementRawValue = { ...this.getFormDefaults(), ...appointement };
    form.reset(
      {
        ...appointementRawValue,
        id: { value: appointementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AppointementFormDefaults {
    return {
      id: null,
    };
  }
}
