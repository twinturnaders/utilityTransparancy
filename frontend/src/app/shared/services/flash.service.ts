import { Injectable } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { filter, Subject } from 'rxjs';

export type FlashLevel = 'success' | 'error' | 'info' | 'warn';
export interface FlashMsg { level: FlashLevel; text: string; timeout?: number; }

@Injectable({ providedIn: 'root' })
export class FlashService {
  private subject = new Subject<FlashMsg | null>();
  private keepAfterRouteChange = false;

  messages$ = this.subject.asObservable();

  constructor(router: Router) {
    router.events.pipe(filter(e => e instanceof NavigationStart)).subscribe(() => {
      if (this.keepAfterRouteChange) {
        this.keepAfterRouteChange = false; // show once after route change
      } else {
        this.clear();
      }
    });
  }

  success(text: string, opts: { keepAfterRouteChange?: boolean; timeout?: number } = {}) {
    this.show('success', text, opts);
  }
  error(text: string, opts: { keepAfterRouteChange?: boolean; timeout?: number } = {}) {
    this.show('error', text, opts);
  }
  info(text: string, opts: { keepAfterRouteChange?: boolean; timeout?: number } = {}) {
    this.show('info', text, opts);
  }
  warn(text: string, opts: { keepAfterRouteChange?: boolean; timeout?: number } = {}) {
    this.show('warn', text, opts);
  }

  clear() { this.subject.next(null); }

  private show(level: FlashLevel, text: string,
               { keepAfterRouteChange = false, timeout = 4000 } = {}) {
    this.keepAfterRouteChange = keepAfterRouteChange;
    const msg: FlashMsg = { level, text, timeout };
    this.subject.next(msg);
    if (timeout > 0) setTimeout(() => this.clear(), timeout);
  }
}
