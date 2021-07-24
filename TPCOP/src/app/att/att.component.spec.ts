import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttComponent } from './att.component';

describe('AttComponent', () => {
  let component: AttComponent;
  let fixture: ComponentFixture<AttComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AttComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
