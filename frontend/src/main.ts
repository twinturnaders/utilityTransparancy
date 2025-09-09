import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';

import {provideHttpClient, withInterceptors} from '@angular/common/http';
import {provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';
import {routes} from './app/app.routes';
import {authInterceptor} from './app/shared/interceptors/auth.interceptor';

bootstrapApplication(AppComponent, {
  providers: [provideHttpClient(), provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes), provideHttpClient(withInterceptors([authInterceptor]))]
})
  .catch(err => console.error(err));
