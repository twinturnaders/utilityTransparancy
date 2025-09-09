
import { Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import {FlashMsg, FlashService} from '../services/flash.service';

@Component({
  selector: 'app-flash-banner',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="msg" class="flash" [class.success]="msg.level==='success'"
                                [class.error]="msg.level==='error'"
                                [class.info]="msg.level==='info'"
                                [class.warn]="msg.level==='warn'">
      <span class="icon">{{ icon }}</span>
      <span>{{ msg.text }}</span>
      <button type="button" class="close" (click)="close()">×</button>
    </div>
  `,
  styleUrls: ['./flash-banner.component.css']
})
export class FlashBannerComponent implements OnInit, OnDestroy {
  msg: FlashMsg | null = null;
  sub?: Subscription;

  get icon() {
    return this.msg?.level === 'success' ? '✓' :
      this.msg?.level === 'error'   ? '!' :
        this.msg?.level === 'warn'    ? '!' : 'i';
  }

  constructor(private flash: FlashService) {}
  ngOnInit() { this.sub = this.flash.messages$.subscribe(m => this.msg = m); }
  ngOnDestroy() { this.sub?.unsubscribe(); }
  close() { this.flash.clear(); }
}
