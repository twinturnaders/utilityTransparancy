import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlashBannerComponent } from './flash-banner.component';

describe('FlashBannerComponent', () => {
  let component: FlashBannerComponent;
  let fixture: ComponentFixture<FlashBannerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FlashBannerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FlashBannerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
