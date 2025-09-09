import { Component, Input } from '@angular/core';
import { NgForOf, NgClass } from '@angular/common';

@Component({
  selector: 'app-star-rating',
  standalone: true,
  imports: [NgForOf, NgClass],
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.css']
})
export class StarRatingComponent {

  @Input() value: number = 0;


  @Input() max: number = 5;

  get stars(): number[] {
    return Array.from({ length: this.max }, (_,_i) => _i + 1);
  }
}
