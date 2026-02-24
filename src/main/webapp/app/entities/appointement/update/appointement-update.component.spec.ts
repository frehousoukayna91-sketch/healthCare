import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IPatient } from 'app/entities/patient/patient.model';
import { PatientService } from 'app/entities/patient/service/patient.service';
import { AppointementService } from '../service/appointement.service';
import { IAppointement } from '../appointement.model';
import { AppointementFormService } from './appointement-form.service';

import { AppointementUpdateComponent } from './appointement-update.component';

describe('Appointement Management Update Component', () => {
  let comp: AppointementUpdateComponent;
  let fixture: ComponentFixture<AppointementUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let appointementFormService: AppointementFormService;
  let appointementService: AppointementService;
  let patientService: PatientService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AppointementUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AppointementUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AppointementUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    appointementFormService = TestBed.inject(AppointementFormService);
    appointementService = TestBed.inject(AppointementService);
    patientService = TestBed.inject(PatientService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should call Patient query and add missing value', () => {
      const appointement: IAppointement = { id: 25002 };
      const patient: IPatient = { id: 16668 };
      appointement.patient = patient;

      const patientCollection: IPatient[] = [{ id: 16668 }];
      jest.spyOn(patientService, 'query').mockReturnValue(of(new HttpResponse({ body: patientCollection })));
      const additionalPatients = [patient];
      const expectedCollection: IPatient[] = [...additionalPatients, ...patientCollection];
      jest.spyOn(patientService, 'addPatientToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ appointement });
      comp.ngOnInit();

      expect(patientService.query).toHaveBeenCalled();
      expect(patientService.addPatientToCollectionIfMissing).toHaveBeenCalledWith(
        patientCollection,
        ...additionalPatients.map(expect.objectContaining),
      );
      expect(comp.patientsSharedCollection).toEqual(expectedCollection);
    });

    it('should update editForm', () => {
      const appointement: IAppointement = { id: 25002 };
      const patient: IPatient = { id: 16668 };
      appointement.patient = patient;

      activatedRoute.data = of({ appointement });
      comp.ngOnInit();

      expect(comp.patientsSharedCollection).toContainEqual(patient);
      expect(comp.appointement).toEqual(appointement);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppointement>>();
      const appointement = { id: 8117 };
      jest.spyOn(appointementFormService, 'getAppointement').mockReturnValue(appointement);
      jest.spyOn(appointementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appointement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: appointement }));
      saveSubject.complete();

      // THEN
      expect(appointementFormService.getAppointement).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(appointementService.update).toHaveBeenCalledWith(expect.objectContaining(appointement));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppointement>>();
      const appointement = { id: 8117 };
      jest.spyOn(appointementFormService, 'getAppointement').mockReturnValue({ id: null });
      jest.spyOn(appointementService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appointement: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: appointement }));
      saveSubject.complete();

      // THEN
      expect(appointementFormService.getAppointement).toHaveBeenCalled();
      expect(appointementService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAppointement>>();
      const appointement = { id: 8117 };
      jest.spyOn(appointementService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ appointement });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(appointementService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePatient', () => {
      it('should forward to patientService', () => {
        const entity = { id: 16668 };
        const entity2 = { id: 16914 };
        jest.spyOn(patientService, 'comparePatient');
        comp.comparePatient(entity, entity2);
        expect(patientService.comparePatient).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
