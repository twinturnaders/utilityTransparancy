import {Component, inject} from '@angular/core';
import {CanActivateFn, Router, RouterLink, RouterOutlet} from '@angular/router';
import {AuthService} from '../shared/services/auth.service';
import {TokenService} from '../shared/services/token.service';
import {CommonModule} from '@angular/common';
export const landingRedirectGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  return auth.isAuthenticated()
    ? router.createUrlTree(['/user-account'])   // account home
    : router.createUrlTree(['/login']); // login screen
};
@Component({
  selector: 'app-app-home',
  imports: [
    RouterLink,
    RouterOutlet,
    CommonModule
  ],
  templateUrl: './app-home.component.html',
  styleUrl: './app-home.component.css'
})


export class AppHomeComponent {


}
