import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAppointement } from '../appointement.model';
import { AppointementService } from '../service/appointement.service';

const appointementResolve = (route: ActivatedRouteSnapshot): Observable<null | IAppointement> => {
  const id = route.params.id;
  if (id) {
    return inject(AppointementService)
      .find(id)
      .pipe(
        mergeMap((appointement: HttpResponse<IAppointement>) => {
          if (appointement.body) {
            return of(appointement.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default appointementResolve;
