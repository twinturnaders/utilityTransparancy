import { Component } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import {RedirectCommand, Router, UrlTree} from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { AuthService} from '../services/auth.service';

import {landingRedirectGuard} from '../../app-home/app-home.component';

@Component({ standalone: true, template: '' })
class DummyComponent {}

class AuthStub {
  isAuthenticated(): boolean { return false; }
}

describe('loginLandingGuard', () => {
  let router: Router;
  let auth: AuthService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'user-account', component: DummyComponent },
          { path: 'login', component: DummyComponent },
        ]),

        DummyComponent
      ],
      providers: [{ provide: AuthService, useClass: AuthStub }],
    }).compileComponents();

    router = TestBed.inject(Router);
    auth = TestBed.inject(AuthService);


    router.initialNavigation();
  });


  function runGuard(): UrlTree {
    return TestBed.runInInjectionContext(() =>
      landingRedirectGuard({} as any, {} as any) as UrlTree
    );
  }

  it('redirects authenticated users to /user-account', () => {
    spyOn(auth, 'isAuthenticated').and.returnValue(true);
    const tree = runGuard();
    expect(router.serializeUrl(tree)).toBe('/user-account');
  });

  it('redirects unauthenticated users to /login', () => {
    spyOn(auth, 'isAuthenticated').and.returnValue(false);
    const tree = runGuard();
    expect(router.serializeUrl(tree)).toBe('/login');
  });
});
