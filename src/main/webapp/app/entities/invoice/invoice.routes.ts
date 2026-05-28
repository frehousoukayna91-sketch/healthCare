import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';

const invoiceRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/invoice.component').then(m => m.InvoiceComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
  },
];

export default invoiceRoute;
