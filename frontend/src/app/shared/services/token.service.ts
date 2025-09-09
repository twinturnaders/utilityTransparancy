import { Injectable } from '@angular/core';

const KEY = 'auth_token';
@Injectable({ providedIn: 'root' })
export class TokenService {
  get(): string | null { return localStorage.getItem(KEY); }
  set(token: string) { localStorage.setItem(KEY, token); }
  clear() { localStorage.removeItem(KEY); }
  isAuthenticated(): boolean { return !!this.get(); }
}
