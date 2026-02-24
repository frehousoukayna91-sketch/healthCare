import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'healthCareApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'patient',
    data: { pageTitle: 'healthCareApp.patient.home.title' },
    loadChildren: () => import('./patient/patient.routes'),
  },
  {
    path: 'appointement',
    data: { pageTitle: 'healthCareApp.appointement.home.title' },
    loadChildren: () => import('./appointement/appointement.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
