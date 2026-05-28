import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import PatientResolve from './route/patient-routing-resolve.service';

const PATIENT_AUTHORITIES = [Authority.MEDECIN, Authority.ADMIN];

const patientRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/patient.component').then(m => m.PatientComponent),
    data: {
      defaultSort: `id,${ASC}`,
      authorities: PATIENT_AUTHORITIES,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/patient-detail.component').then(m => m.PatientDetailComponent),
    resolve: {
      patient: PatientResolve,
    },
    data: { authorities: PATIENT_AUTHORITIES },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/history',
    loadComponent: () => import('./history/patient-history.component').then(m => m.PatientHistoryComponent),
    data: { authorities: PATIENT_AUTHORITIES },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/patient-update.component').then(m => m.PatientUpdateComponent),
    resolve: {
      patient: PatientResolve,
    },
    data: { authorities: PATIENT_AUTHORITIES },
    canActivate: [UserRouteAccessService],
  },
];

export default patientRoute;
