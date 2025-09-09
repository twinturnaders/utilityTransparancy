import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { UserService } from '../../../shared/services/user.service';
import {NgIf} from '@angular/common';

export interface UserDTO {
  id: number;
  email: string;
  displayName?: string;
  roles?: string[];
  createdAt?: string;
  enabled?: boolean;
}

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
  selector: 'app-account-edit',
  templateUrl: './account-edit.component.html',
  styleUrls: ['./account-edit.component.css'],
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AccountEditComponent implements OnInit {
  user?: UserDTO;
  saving = false;
  message = '';
  error = '';

  form!: AccountForm;

  constructor(private users: UserService, private fb: FormBuilder) {
    this.form = this.fb.group({
      displayName: this.fb.nonNullable.control('', []),
      email: this.fb.nonNullable.control('', [Validators.required, Validators.email]),
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
    if (this.form.invalid) return;

    const payload = this.buildUpdatePayload();
    if (!payload) { this.message = 'No changes to save.'; return; }

    this.saving = true; this.message = ''; this.error = '';
    this.users.update(payload).subscribe({
      next: () => { this.saving = false; this.message = 'Saved!'; this.form.get('password')!.reset(''); },
      error: () => { this.saving = false; this.error = 'Save failed.'; }
    });
  }

  private buildUpdatePayload(): UpdateAccountRequest | null {
    if (!this.user) return null;
    const v = this.form.getRawValue();

    const changes: UpdateAccountRequest = {};
    if ((v.displayName ?? '') !== (this.user.displayName ?? '')) changes.displayName = v.displayName?.trim() || '';
    if ((v.email ?? '') !== (this.user.email ?? '')) changes.email = v.email?.trim() || '';
    if (v.password?.trim()) changes.password = v.password.trim();

    // return null if nothing changed
    return Object.keys(changes).length ? changes : null;
  }
}
