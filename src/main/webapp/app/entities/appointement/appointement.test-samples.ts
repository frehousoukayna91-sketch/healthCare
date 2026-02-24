import { IAppointement, NewAppointement } from './appointement.model';

export const sampleWithRequiredData: IAppointement = {
  id: 29519,
};

export const sampleWithPartialData: IAppointement = {
  id: 27036,
  patientId: 17507,
  status: 'CONFIRMED',
  type: 'PROCEDURE',
};

export const sampleWithFullData: IAppointement = {
  id: 21660,
  patientId: 4865,
  duration: 8012,
  status: 'COMPLETED',
  type: 'ROUTINE_CHECKUP',
};

export const sampleWithNewData: NewAppointement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
