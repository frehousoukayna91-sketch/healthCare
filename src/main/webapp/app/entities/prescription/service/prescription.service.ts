import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { DATE_FORMAT } from 'app/config/input.constants';
import { IPrescription, NewPrescription } from '../prescription.model';

export type PartialUpdatePrescription = Partial<IPrescription> & Pick<IPrescription, 'id'>;

type RestOf<T extends IPrescription | NewPrescription> = Omit<T, 'prescriptionDate' | 'followupDate'> & {
  prescriptionDate?: string | null;
  followupDate?: string | null;
};

export type RestPrescription = RestOf<IPrescription>;
export type NewRestPrescription = RestOf<NewPrescription>;

export type EntityResponseType = HttpResponse<IPrescription>;
export type EntityArrayResponseType = HttpResponse<IPrescription[]>;

@Injectable({ providedIn: 'root' })
export class PrescriptionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prescriptions');

  create(prescription: NewPrescription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prescription);
    return this.http
      .post<RestPrescription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(prescription: IPrescription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(prescription);
    return this.http
      .put<RestPrescription>(`${this.resourceUrl}/${this.getPrescriptionIdentifier(prescription)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPrescription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPrescription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertArrayResponseFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrescriptionIdentifier(prescription: Pick<IPrescription, 'id'>): number {
    return prescription.id;
  }

  comparePrescription(o1: Pick<IPrescription, 'id'> | null, o2: Pick<IPrescription, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrescriptionIdentifier(o1) === this.getPrescriptionIdentifier(o2) : o1 === o2;
  }

  addPrescriptionToCollectionIfMissing<Type extends Pick<IPrescription, 'id'>>(
    collection: Type[],
    ...toCheck: (Type | null | undefined)[]
  ): Type[] {
    const items: Type[] = toCheck.filter(isPresent);
    if (items.length > 0) {
      const collectionIds = collection.map(it => this.getPrescriptionIdentifier(it));
      const toAdd = items.filter(it => {
        const id = this.getPrescriptionIdentifier(it);
        if (collectionIds.includes(id)) {
          return false;
        }
        collectionIds.push(id);
        return true;
      });
      return [...toAdd, ...collection];
    }
    return collection;
  }

  protected convertDateFromClient<T extends IPrescription | NewPrescription | PartialUpdatePrescription>(prescription: T): RestOf<T> {
    return {
      ...prescription,
      prescriptionDate: this.formatDate(prescription.prescriptionDate),
      followupDate: this.formatDate(prescription.followupDate),
    };
  }

  protected formatDate(value: dayjs.Dayjs | string | null | undefined): string | null {
    if (value == null || value === '') {
      return null;
    }
    if (typeof value === 'string') {
      return value;
    }
    if (dayjs.isDayjs(value)) {
      return value.format(DATE_FORMAT);
    }
    return dayjs(value as any).format(DATE_FORMAT);
  }

  protected convertDateFromServer(rest: RestPrescription): IPrescription {
    return {
      ...rest,
      prescriptionDate: rest.prescriptionDate ? dayjs(rest.prescriptionDate) : null,
      followupDate: rest.followupDate ? dayjs(rest.followupDate) : null,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPrescription>): EntityResponseType {
    return res.clone({ body: res.body ? this.convertDateFromServer(res.body) : null });
  }

  protected convertArrayResponseFromServer(res: HttpResponse<RestPrescription[]>): EntityArrayResponseType {
    return res.clone({ body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null });
  }
}
