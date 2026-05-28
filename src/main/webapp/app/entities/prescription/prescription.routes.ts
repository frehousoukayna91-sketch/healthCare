import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import PrescriptionResolve from './route/prescription-routing-resolve.service';

const prescriptionRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/prescription.component').then(m => m.PrescriptionComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
  },
  {
    path: 'new',
    loadComponent: () => import('./update/prescription-update.component').then(m => m.PrescriptionUpdateComponent),
    resolve: {
      prescription: PrescriptionResolve,
    },
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/prescription-update.component').then(m => m.PrescriptionUpdateComponent),
    resolve: {
      prescription: PrescriptionResolve,
    },
  },
];

export default prescriptionRoute;
