import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



// @NgModule({
//   declarations: [],
//   imports: [
//     CommonModule
//   ]
// })
// export class MunicipalityModule { }
export interface MunicipalityResult {
  id: number;
  name: string;
  distanceMiles: number;
  waterBaseRate?: number | null;
  sewerBaseRate?: number | null;
  confidence: 1 | 2 | 3 | 4 | 5;
}
