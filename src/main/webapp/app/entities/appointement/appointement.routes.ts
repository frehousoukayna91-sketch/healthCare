import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import AppointementResolve from './route/appointement-routing-resolve.service';

const APPOINTEMENT_AUTHORITIES = [Authority.MEDECIN, Authority.SECRETAIRE, Authority.ADMIN];

const appointementRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/appointement.component').then(m => m.AppointementComponent),
    data: {
      defaultSort: `id,${ASC}`,
      authorities: APPOINTEMENT_AUTHORITIES,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/appointement-detail.component').then(m => m.AppointementDetailComponent),
    resolve: {
      appointement: AppointementResolve,
    },
    data: { authorities: APPOINTEMENT_AUTHORITIES },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/appointement-update.component').then(m => m.AppointementUpdateComponent),
    resolve: {
      appointement: AppointementResolve,
    },
    data: { authorities: APPOINTEMENT_AUTHORITIES },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/appointement-update.component').then(m => m.AppointementUpdateComponent),
    resolve: {
      appointement: AppointementResolve,
    },
    data: { authorities: APPOINTEMENT_AUTHORITIES },
    canActivate: [UserRouteAccessService],
  },
];

export default appointementRoute;
