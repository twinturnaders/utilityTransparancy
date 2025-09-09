import {Component, inject, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AuthService} from '../../../shared/services/auth.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html'
})
export class LogoutComponent implements OnInit {
  private router = inject(Router);
  constructor(
    private auth: AuthService,

  ) {}

  ngOnInit(): void {

    this.auth.logout();
    this.router.navigateByUrl('');
  }
}
