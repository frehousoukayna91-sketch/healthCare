import dayjs from 'dayjs/esm';

export interface IPatient {
  id: number;
  nom?: string | null;
  lastName?: string | null;
  firstName?: string | null;
  dateBirth?: dayjs.Dayjs | null;
  phone?: string | null;
  email?: string | null;
  address?: string | null;
  bloodType?: string | null;
  allergies?: string | null;
  notes?: string | null;
  emergencyContactName?: string | null;
  emeregencyContactPhone?: string | null;
  chronicdiseases?: string | null;
  medicalHistory?: string | null;
}

export type NewPatient = Omit<IPatient, 'id'> & { id: null };
