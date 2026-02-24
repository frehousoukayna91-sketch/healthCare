import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAppointement, NewAppointement } from '../appointement.model';

export type PartialUpdateAppointement = Partial<IAppointement> & Pick<IAppointement, 'id'>;

export type EntityResponseType = HttpResponse<IAppointement>;
export type EntityArrayResponseType = HttpResponse<IAppointement[]>;

@Injectable({ providedIn: 'root' })
export class AppointementService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/appointements');

  create(appointement: NewAppointement): Observable<EntityResponseType> {
    return this.http.post<IAppointement>(this.resourceUrl, appointement, { observe: 'response' });
  }

  update(appointement: IAppointement): Observable<EntityResponseType> {
    return this.http.put<IAppointement>(`${this.resourceUrl}/${this.getAppointementIdentifier(appointement)}`, appointement, {
      observe: 'response',
    });
  }

  partialUpdate(appointement: PartialUpdateAppointement): Observable<EntityResponseType> {
    return this.http.patch<IAppointement>(`${this.resourceUrl}/${this.getAppointementIdentifier(appointement)}`, appointement, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppointement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppointement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAppointementIdentifier(appointement: Pick<IAppointement, 'id'>): number {
    return appointement.id;
  }

  compareAppointement(o1: Pick<IAppointement, 'id'> | null, o2: Pick<IAppointement, 'id'> | null): boolean {
    return o1 && o2 ? this.getAppointementIdentifier(o1) === this.getAppointementIdentifier(o2) : o1 === o2;
  }

  addAppointementToCollectionIfMissing<Type extends Pick<IAppointement, 'id'>>(
    appointementCollection: Type[],
    ...appointementsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const appointements: Type[] = appointementsToCheck.filter(isPresent);
    if (appointements.length > 0) {
      const appointementCollectionIdentifiers = appointementCollection.map(appointementItem =>
        this.getAppointementIdentifier(appointementItem),
      );
      const appointementsToAdd = appointements.filter(appointementItem => {
        const appointementIdentifier = this.getAppointementIdentifier(appointementItem);
        if (appointementCollectionIdentifiers.includes(appointementIdentifier)) {
          return false;
        }
        appointementCollectionIdentifiers.push(appointementIdentifier);
        return true;
      });
      return [...appointementsToAdd, ...appointementCollection];
    }
    return appointementCollection;
  }
}
