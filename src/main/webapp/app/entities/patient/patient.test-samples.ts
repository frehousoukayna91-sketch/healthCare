import dayjs from 'dayjs/esm';

import { IPatient, NewPatient } from './patient.model';

export const sampleWithRequiredData: IPatient = {
  id: 2514,
};

export const sampleWithPartialData: IPatient = {
  id: 17415,
  nom: 'lunatique après que',
  lastName: 'Lacroix',
  dateBirth: dayjs('2026-01-29'),
  phone: '+33 783689355',
  email: 'Antoine.Richard30@hotmail.fr',
  address: 'succéder aussitôt que',
  notes: 'sauf à condition que',
  emergencyContactName: "à l'exception de",
  chronicdiseases: 'de par',
};

export const sampleWithFullData: IPatient = {
  id: 9819,
  nom: 'entre',
  lastName: 'Marchal',
  firstName: 'Joanny',
  dateBirth: dayjs('2026-01-29'),
  phone: '0799536895',
  email: 'Coralie_Laurent@gmail.com',
  address: 'crac totalement puisque',
  bloodType: 'affable recueillir',
  allergies: "ouch à l'encontre de",
  notes: 'communauté étudiante ci capter',
  emergencyContactName: 'pourvu que',
  emeregencyContactPhone: 'lorsque foule sans que',
  chronicdiseases: 'parce que souvenir',
  medicalHistory: 'au-delà',
};

export const sampleWithNewData: NewPatient = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
