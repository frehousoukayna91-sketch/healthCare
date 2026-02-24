import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAppointement } from '../appointement.model';
import { AppointementService } from '../service/appointement.service';

@Component({
  templateUrl: './appointement-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AppointementDeleteDialogComponent {
  appointement?: IAppointement;

  protected appointementService = inject(AppointementService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.appointementService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
