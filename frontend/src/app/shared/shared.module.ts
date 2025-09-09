import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HTTP_INTERCEPTORS, provideHttpClient} from '@angular/common/http';
import { authInterceptor } from './interceptors/auth.interceptor';


export interface UserDTO {
  id: number;
  email: string;
  role: string;
  municipalityId?: number | null;
  municipalityName?: string | null;
}

export interface AuthResponse {
  token: string;
  user: UserDTO;
}
