
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import {landingRedirectGuard} from '../../app-home/app-home.component';

export const LandingRedirectGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  return auth.isAuthenticated()
    ? router.createUrlTree(['/user-account'])
    : router.createUrlTree(['/login']);
};
