import { Injectable } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import dayjs from 'dayjs/esm';

import { IPrescription, IPrescriptionItem, NewPrescription } from '../prescription.model';

const toDateString = (value: dayjs.Dayjs | string | null | undefined): string | null => {
  if (value == null || value === '') {
    return null;
  }
  if (typeof value === 'string') {
    return value;
  }
  return dayjs.isDayjs(value) ? value.format('YYYY-MM-DD') : null;
};

type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

type PrescriptionFormGroupInput = IPrescription | PartialWithRequiredKeyOf<NewPrescription>;

type PrescriptionFormDefaults = Pick<NewPrescription, 'id'>;

export type PrescriptionItemFormGroup = FormGroup<{
  id: FormControl<number | null>;
  medicationName: FormControl<string | null>;
  medicationDosage: FormControl<number | null>;
  frequency: FormControl<number | null>;
  duration: FormControl<number | null>;
  instructions: FormControl<string | null>;
}>;

type PrescriptionFormGroupContent = {
  id: FormControl<IPrescription['id'] | NewPrescription['id']>;
  patientId: FormControl<IPrescription['patientId']>;
  prescriptionDate: FormControl<IPrescription['prescriptionDate']>;
  followupDate: FormControl<IPrescription['followupDate']>;
  diagnosis: FormControl<IPrescription['diagnosis']>;
  notes: FormControl<IPrescription['notes']>;
  prescriptionItems: FormArray<PrescriptionItemFormGroup>;
};

export type PrescriptionFormGroup = FormGroup<PrescriptionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrescriptionFormService {
  createPrescriptionFormGroup(prescription: PrescriptionFormGroupInput = { id: null }): PrescriptionFormGroup {
    const raw = {
      ...this.getFormDefaults(),
      ...prescription,
    };
    return new FormGroup<PrescriptionFormGroupContent>({
      id: new FormControl(
        { value: raw.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      patientId: new FormControl(raw.patientId ?? null, { validators: [Validators.required] }),
      prescriptionDate: new FormControl(raw.prescriptionDate ?? null, { validators: [Validators.required] }),
      followupDate: new FormControl(raw.followupDate ?? null),
      diagnosis: new FormControl(raw.diagnosis ?? null, { validators: [Validators.required] }),
      notes: new FormControl(raw.notes ?? null),
      prescriptionItems: new FormArray<PrescriptionItemFormGroup>(
        (raw.prescriptionItems ?? []).map(item => this.createItemFormGroup(item)),
      ),
    });
  }

  createItemFormGroup(item?: IPrescriptionItem | null): PrescriptionItemFormGroup {
    return new FormGroup({
      id: new FormControl<number | null>(item?.id ?? null),
      medicationName: new FormControl<string | null>(item?.medicationName ?? null, { validators: [Validators.required] }),
      medicationDosage: new FormControl<number | null>(item?.medicationDosage ?? null),
      frequency: new FormControl<number | null>(item?.frequency ?? null),
      duration: new FormControl<number | null>(item?.duration ?? null),
      instructions: new FormControl<string | null>(item?.instructions ?? null),
    });
  }

  getPrescription(form: PrescriptionFormGroup): IPrescription | NewPrescription {
    return form.getRawValue() as IPrescription | NewPrescription;
  }

  resetForm(form: PrescriptionFormGroup, prescription: PrescriptionFormGroupInput): void {
    const raw = { ...this.getFormDefaults(), ...prescription };
    form.controls.prescriptionItems.clear();
    (raw.prescriptionItems ?? []).forEach(item => form.controls.prescriptionItems.push(this.createItemFormGroup(item)));
    form.patchValue({
      id: raw.id,
      patientId: raw.patientId ?? null,
      prescriptionDate: toDateString(raw.prescriptionDate) as any,
      followupDate: toDateString(raw.followupDate) as any,
      diagnosis: raw.diagnosis ?? null,
      notes: raw.notes ?? null,
    } as any);
  }

  private getFormDefaults(): PrescriptionFormDefaults {
    return { id: null };
  }
}
