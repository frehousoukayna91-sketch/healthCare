import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import PrescriptionResolve from './route/prescription-routing-resolve.service';

const PRESCRIPTION_AUTHORITIES = [Authority.MEDECIN, Authority.ADMIN];

const prescriptionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/prescription.component').then(m => m.PrescriptionComponent),
    data: {
      defaultSort: `id,${ASC}`,
      authorities: PRESCRIPTION_AUTHORITIES,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/prescription-update.component').then(m => m.PrescriptionUpdateComponent),
    resolve: {
      prescription: PrescriptionResolve,
    },
    data: { authorities: PRESCRIPTION_AUTHORITIES },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/prescription-update.component').then(m => m.PrescriptionUpdateComponent),
    resolve: {
      prescription: PrescriptionResolve,
    },
    data: { authorities: PRESCRIPTION_AUTHORITIES },
    canActivate: [UserRouteAccessService],
  },
];

export default prescriptionRoute;
