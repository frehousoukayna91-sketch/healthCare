import dayjs from 'dayjs/esm';
import { InvoiceStatus } from 'app/entities/enumerations/invoice-status.model';
import { PaymentMethod } from 'app/entities/enumerations/payment-method.model';

export interface IInvoiceItem {
  id: number | null;
  description?: string | null;
  quantity?: number | null;
  unitPrice?: number | null;
  total?: number | null;
}

export type NewInvoiceItem = Omit<IInvoiceItem, 'id'> & { id: null };

export interface IInvoice {
  id: number;
  invoiceNumber?: string | null;
  patientId?: number | null;
  patientName?: string | null;
  appointmentId?: string | null;
  date?: dayjs.Dayjs | null;
  dueDate?: dayjs.Dayjs | null;
  subtotal?: number | null;
  tax?: number | null;
  total?: number | null;
  status?: InvoiceStatus | null;
  paymentMethod?: PaymentMethod | null;
  items?: IInvoiceItem[] | null;
}

export type NewInvoice = Omit<IInvoice, 'id'> & { id: null };
