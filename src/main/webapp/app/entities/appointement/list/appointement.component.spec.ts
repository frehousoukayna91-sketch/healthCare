import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpHeaders, HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subject, of } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { sampleWithRequiredData } from '../appointement.test-samples';
import { AppointementService } from '../service/appointement.service';

import { AppointementComponent } from './appointement.component';

describe('Appointement Management Component', () => {
  let comp: AppointementComponent;
  let fixture: ComponentFixture<AppointementComponent>;
  let service: AppointementService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AppointementComponent],
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({}),
            queryParamMap: of(jest.requireActual('@angular/router').convertToParamMap({})),
            snapshot: { queryParams: {}, queryParamMap: jest.requireActual('@angular/router').convertToParamMap({}) },
          },
        },
      ],
    })
      .overrideTemplate(AppointementComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AppointementComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AppointementService);

    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 8117 }],
          headers: new HttpHeaders(),
        }),
      ),
    );
  });

  it('should call load all on init', () => {
    comp.ngOnInit();
    expect(service.query).toHaveBeenCalled();
    expect(comp.appointements()[0]).toEqual(expect.objectContaining({ id: 8117 }));
  });

  describe('trackId', () => {
    it('should forward to appointementService', () => {
      const entity = { id: 8117 };
      jest.spyOn(service, 'getAppointementIdentifier');
      const id = comp.trackId(entity);
      expect(service.getAppointementIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });

  describe('week navigation', () => {
    it('previousWeek should subtract 7 days', () => {
      const before = comp.weekStart();
      comp.previousWeek();
      expect(comp.weekStart().diff(before, 'day')).toBe(-7);
    });

    it('nextWeek should add 7 days', () => {
      const before = comp.weekStart();
      comp.nextWeek();
      expect(comp.weekStart().diff(before, 'day')).toBe(7);
    });
  });

  describe('selectStatus', () => {
    it('should update selected status', () => {
      comp.selectStatus('CONFIRMED');
      expect(comp.selectedStatus()).toBe('CONFIRMED');
    });
  });

  describe('delete', () => {
    let ngbModal: NgbModal;
    let deleteModalMock: any;

    beforeEach(() => {
      deleteModalMock = { componentInstance: {}, closed: new Subject() };
      ngbModal = (comp as any).modalService;
      jest.spyOn(ngbModal, 'open').mockReturnValue(deleteModalMock);
    });

    it('on confirm should call load', inject(
      [],
      fakeAsync(() => {
        jest.spyOn(comp, 'load');
        comp.delete(sampleWithRequiredData);
        deleteModalMock.closed.next('deleted');
        tick();
        expect(ngbModal.open).toHaveBeenCalled();
        expect(comp.load).toHaveBeenCalled();
      }),
    ));

    it('on dismiss should call load', inject(
      [],
      fakeAsync(() => {
        jest.spyOn(comp, 'load');
        comp.delete(sampleWithRequiredData);
        deleteModalMock.closed.next();
        tick();
        expect(ngbModal.open).toHaveBeenCalled();
        expect(comp.load).not.toHaveBeenCalled();
      }),
    ));
  });
});
