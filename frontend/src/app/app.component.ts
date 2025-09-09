import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {FlashBannerComponent} from './shared/flash-banner/flash-banner.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, FlashBannerComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'test';
}
