import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { DATE_FORMAT } from 'app/config/input.constants';
import { IInvoice, NewInvoice } from '../invoice.model';

export type PartialUpdateInvoice = Partial<IInvoice> & Pick<IInvoice, 'id'>;

type RestOf<T extends IInvoice | NewInvoice> = Omit<T, 'date' | 'dueDate'> & {
  date?: string | null;
  dueDate?: string | null;
};

export type RestInvoice = RestOf<IInvoice>;
export type NewRestInvoice = RestOf<NewInvoice>;

export type EntityResponseType = HttpResponse<IInvoice>;
export type EntityArrayResponseType = HttpResponse<IInvoice[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/invoices');

  create(invoice: NewInvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoice);
    return this.http
      .post<RestInvoice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(invoice: IInvoice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoice);
    return this.http
      .put<RestInvoice>(`${this.resourceUrl}/${this.getInvoiceIdentifier(invoice)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestInvoice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestInvoice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertArrayResponseFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInvoiceIdentifier(invoice: Pick<IInvoice, 'id'>): number {
    return invoice.id;
  }

  compareInvoice(o1: Pick<IInvoice, 'id'> | null, o2: Pick<IInvoice, 'id'> | null): boolean {
    return o1 && o2 ? this.getInvoiceIdentifier(o1) === this.getInvoiceIdentifier(o2) : o1 === o2;
  }

  addInvoiceToCollectionIfMissing<Type extends Pick<IInvoice, 'id'>>(collection: Type[], ...toCheck: (Type | null | undefined)[]): Type[] {
    const items: Type[] = toCheck.filter(isPresent);
    if (items.length > 0) {
      const collectionIds = collection.map(it => this.getInvoiceIdentifier(it));
      const toAdd = items.filter(it => {
        const id = this.getInvoiceIdentifier(it);
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

  protected convertDateFromClient<T extends IInvoice | NewInvoice | PartialUpdateInvoice>(invoice: T): RestOf<T> {
    return {
      ...invoice,
      date: this.formatDate(invoice.date),
      dueDate: this.formatDate(invoice.dueDate),
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

  protected convertDateFromServer(rest: RestInvoice): IInvoice {
    return {
      ...rest,
      date: rest.date ? dayjs(rest.date) : null,
      dueDate: rest.dueDate ? dayjs(rest.dueDate) : null,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestInvoice>): EntityResponseType {
    return res.clone({ body: res.body ? this.convertDateFromServer(res.body) : null });
  }

  protected convertArrayResponseFromServer(res: HttpResponse<RestInvoice[]>): EntityArrayResponseType {
    return res.clone({ body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null });
  }
}
