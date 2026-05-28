import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import PatientResolve from './route/patient-routing-resolve.service';

const patientRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/patient.component').then(m => m.PatientComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/patient-detail.component').then(m => m.PatientDetailComponent),
    resolve: {
      patient: PatientResolve,
    },
  },
  {
    path: ':id/history',
    loadComponent: () => import('./history/patient-history.component').then(m => m.PatientHistoryComponent),
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/patient-update.component').then(m => m.PatientUpdateComponent),
    resolve: {
      patient: PatientResolve,
    },
  },
];

export default patientRoute;
