import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

export class AuthService {

  isAuthenticated$(): Observable<boolean> {
    const hasToken = !!localStorage.getItem('jwt');
    return of(hasToken).pipe(map(Boolean));
  }
}
