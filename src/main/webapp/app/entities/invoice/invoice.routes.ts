import { Routes } from '@angular/router';

import { ASC } from 'app/config/navigation.constants';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const INVOICE_AUTHORITIES = [Authority.SECRETAIRE, Authority.ADMIN];

const invoiceRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/invoice.component').then(m => m.InvoiceComponent),
    data: {
      defaultSort: `id,${ASC}`,
      authorities: INVOICE_AUTHORITIES,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default invoiceRoute;
