import dayjs from 'dayjs/esm';
import { IPatient } from 'app/entities/patient/patient.model';
import { statusAppointement } from 'app/entities/enumerations/status-appointement.model';
import { typeAppointement } from 'app/entities/enumerations/type-appointement.model';

export interface IAppointement {
  id: number;
  patientId?: number | null;
  duration?: number | null;
  status?: keyof typeof statusAppointement | null;
  type?: keyof typeof typeAppointement | null;
  appointementDate?: dayjs.Dayjs | null;
  appointementTime?: string | null;
  reasonAppointement?: string | null;
  notes?: string | null;
  patient?: IPatient | null;
}

export type NewAppointement = Omit<IAppointement, 'id'> & { id: null };
