import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import AppointementResolve from './route/appointement-routing-resolve.service';

const appointementRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/appointement.component').then(m => m.AppointementComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/appointement-detail.component').then(m => m.AppointementDetailComponent),
    resolve: {
      appointement: AppointementResolve,
    },
  },
  {
    path: 'new',
    loadComponent: () => import('./update/appointement-update.component').then(m => m.AppointementUpdateComponent),
    resolve: {
      appointement: AppointementResolve,
    },
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/appointement-update.component').then(m => m.AppointementUpdateComponent),
    resolve: {
      appointement: AppointementResolve,
    },
  },
];

export default appointementRoute;
