import { Component, OnInit, inject } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';

import { IPrescription, IPrescriptionItem } from '../prescription.model';

@Component({
  templateUrl: './prescription-print-dialog.component.html',
  styleUrl: './prescription-print-dialog.component.scss',
  imports: [SharedModule],
})
export class PrescriptionPrintDialogComponent implements OnInit {
  prescription?: IPrescription;
  patient: IPatient | null = null;

  protected activeModal = inject(NgbActiveModal);
  protected patientService = inject(PatientService);

  ngOnInit(): void {
    if (this.prescription?.patientId != null) {
      this.patientService.find(this.prescription.patientId).subscribe({
        next: res => (this.patient = res.body),
      });
    }
  }

  close(): void {
    this.activeModal.dismiss();
  }

  print(): void {
    const sheet = document.getElementById('prescriptionPrintArea');
    if (!sheet) {
      window.print();
      return;
    }
    const printWindow = window.open('', '_blank', 'width=900,height=900');
    if (!printWindow) {
      window.print();
      return;
    }
    const headStyles = Array.from(document.querySelectorAll('link[rel="stylesheet"], style'))
      .map(node => node.outerHTML)
      .join('\n');
    printWindow.document.open();
    printWindow.document.write(`<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title>Prescription</title>
  ${headStyles}
  <style>
    body { margin: 0; padding: 1.5rem 2rem; background: #fff; color: #111827; font-family: Inter, system-ui, -apple-system, "Segoe UI", Roboto, sans-serif; }
    #prescriptionPrintArea { max-height: none !important; overflow: visible !important; padding: 0 !important; }
  </style>
</head>
<body>
  ${sheet.outerHTML}
  <script>
    window.addEventListener('load', function () {
      setTimeout(function () {
        window.focus();
        window.print();
        window.close();
      }, 300);
    });
  </script>
</body>
</html>`);
    printWindow.document.close();
  }

  patientName(): string {
    if (!this.patient) return `Patient #${this.prescription?.patientId ?? '-'}`;
    const first = this.patient.firstName ?? '';
    const last = this.patient.nom ?? this.patient.lastName ?? '';
    const full = `${first} ${last}`.trim();
    return full || `Patient #${this.patient.id}`;
  }

  patientDateOfBirth(): string | null {
    return this.patient?.dateBirth ? this.patient.dateBirth.format('MMMM D, YYYY') : null;
  }

  frequencyLabel(item: IPrescriptionItem): string {
    if (item.frequency == null) return '—';
    const n = item.frequency;
    if (n === 1) return 'Once daily';
    if (n === 2) return 'Twice daily';
    if (n === 3) return 'Three times daily';
    if (n === 4) return 'Four times daily';
    return `${n} times daily`;
  }
}
