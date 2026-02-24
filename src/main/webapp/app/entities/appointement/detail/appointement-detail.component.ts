import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IAppointement } from '../appointement.model';

@Component({
  selector: 'jhi-appointement-detail',
  templateUrl: './appointement-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class AppointementDetailComponent {
  appointement = input<IAppointement | null>(null);

  previousState(): void {
    window.history.back();
  }
}
