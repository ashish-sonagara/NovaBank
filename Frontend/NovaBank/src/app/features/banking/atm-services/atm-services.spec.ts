import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AtmServices } from './atm-services';

describe('AtmServices', () => {
  let component: AtmServices;
  let fixture: ComponentFixture<AtmServices>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AtmServices],
    }).compileComponents();

    fixture = TestBed.createComponent(AtmServices);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
