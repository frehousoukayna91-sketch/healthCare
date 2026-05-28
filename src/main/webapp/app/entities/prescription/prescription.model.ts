import dayjs from 'dayjs/esm';

export interface IPrescriptionItem {
  id: number | null;
  medicationName?: string | null;
  medicationDosage?: number | null;
  frequency?: number | null;
  duration?: number | null;
  instructions?: string | null;
}

export type NewPrescriptionItem = Omit<IPrescriptionItem, 'id'> & { id: null };

export interface IPrescription {
  id: number;
  patientId?: number | null;
  prescriptionDate?: dayjs.Dayjs | null;
  followupDate?: dayjs.Dayjs | null;
  diagnosis?: string | null;
  notes?: string | null;
  prescriptionItems?: IPrescriptionItem[] | null;
}

export type NewPrescription = Omit<IPrescription, 'id'> & { id: null };
