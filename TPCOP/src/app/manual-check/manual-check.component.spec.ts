import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManualCheckComponent } from './manual-check.component';

describe('ManualCheckComponent', () => {
  let component: ManualCheckComponent;
  let fixture: ComponentFixture<ManualCheckComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManualCheckComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManualCheckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
