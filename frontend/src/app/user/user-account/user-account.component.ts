import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { UserService } from '../../shared/services/user.service';
import { UserDTO } from '../user.dto';
import {CommonModule, NgIf} from '@angular/common';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {UserBillsComponent} from './user-bills/user-bills.component';
import {AccountEditComponent} from './account-edit/account-edit.component';

export interface UpdateAccountRequest {
  displayName?: string;
  email?: string;
  password?: string;
}

type AccountForm = FormGroup<{
  displayName: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
}>;

@Component({
  selector: 'app-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css'],
  imports: [
    ReactiveFormsModule,
    CommonModule,
    RouterLinkActive,
    RouterLink,
    RouterOutlet
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserAccountComponent implements OnInit {
  user?: UserDTO;
  saving = false;
  message: string | null = null;
  error: string | null = null;


  form!: AccountForm;

  constructor(private users: UserService, private fb: FormBuilder) {

    this.form = this.fb.group({
      displayName: this.fb.nonNullable.control('', []),
      email: this.fb.nonNullable.control('', [Validators.email]),
      password: this.fb.nonNullable.control('', []),



    });
  }

  ngOnInit(): void {
    this.users.me().subscribe({
      next: (u) => {
        this.user = u;
        this.form.patchValue({
          displayName: u.displayName ?? '',
          email: u.email ?? ''
        });
      },
      error: () => this.error = 'Could not load account.'
    });
  }

  save(): void {
    if (!this.user) return;

    const v = this.form.getRawValue();


    const payload: UpdateAccountRequest = {};
    if (v.displayName.trim() && v.displayName !== (this.user.displayName ?? '')) {
      payload.displayName = v.displayName.trim();
    }
    if (v.email.trim() && v.email !== (this.user.email ?? '')) {
      payload.email = v.email.trim();
    }
    if (v.password.trim()) {
      payload.password = v.password.trim();
    }

    if (Object.keys(payload).length === 0) {
      this.message = 'No changes to save.';
      return;
    }

    this.saving = true; this.message = null; this.error = null;
    this.users.update(payload).subscribe({
      next: () => {
        this.saving = false;
        this.message = 'Saved!';
        this.form.get('password')!.reset('');
      },
      error: () => {
        this.saving = false;
        this.error = 'Save failed';
      }
    });
  }
}

