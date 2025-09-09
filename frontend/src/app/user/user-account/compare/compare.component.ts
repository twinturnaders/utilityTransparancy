import {Component} from '@angular/core';

import {SearchComponent} from '../../../shared/search/search.component';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-user-compare',
  templateUrl: './compare.component.html',
  imports: [CommonModule,
   SearchComponent
  ],
  styleUrls: ['./compare.component.css'],
  standalone: true,
})
export class CompareComponent  {

}
