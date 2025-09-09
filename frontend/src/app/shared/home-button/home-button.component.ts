import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-home-button',
  imports: [
    RouterLink
  ],
  templateUrl: './home-button.component.html',
  styleUrl: './home-button.component.css',
  standalone: true,
})
export class HomeButtonComponent {

}
