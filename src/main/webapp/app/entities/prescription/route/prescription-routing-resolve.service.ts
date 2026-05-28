import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrescription } from '../prescription.model';
import { PrescriptionService } from '../service/prescription.service';

const prescriptionResolve = (route: ActivatedRouteSnapshot): Observable<null | IPrescription> => {
  const id = route.params.id;
  if (id) {
    return inject(PrescriptionService)
      .find(id)
      .pipe(
        mergeMap((res: HttpResponse<IPrescription>) => {
          if (res.body) {
            return of(res.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default prescriptionResolve;
