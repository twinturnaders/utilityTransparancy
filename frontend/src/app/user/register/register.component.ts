
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { CommonModule } from '@angular/common';
import { finalize } from 'rxjs/operators';

import { AuthService, RegisterRequest } from '../../shared/services/auth.service';
import { FlashService } from '../../shared/services/flash.service';
import { MunicipalityService, MunicipalityOption } from '../../shared/municipality.service';
import {HomeButtonComponent} from '../../shared/home-button/home-button.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, RouterLink, HomeButtonComponent],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  loading = false;
  error: string | null = null;

  form!: FormGroup;
  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private flash: FlashService,
    private munis: MunicipalityService
  ) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      zipCode: ['', [Validators.required, Validators.pattern(/^\d{5}$/)]],
      municipalityId: [null],
      displayName: ['']
    });
  }
  nearby: MunicipalityOption[] = [];
  autoPicked = false;



  submit() {
    if (this.form.invalid || this.loading) return;

    this.loading = true;
    this.error = null;

    const payload = this.form.getRawValue() as RegisterRequest;

    this.auth.register(payload).pipe(
      finalize(() => (this.loading = false))
    ).subscribe({
      next: () => {

        this.flash.success('Account created! Please sign in.', { keepAfterRouteChange: true });
        void this.router.navigate(['/login'], { replaceUrl: true, queryParams: { email: payload.email } });
        this.form.reset();
      },
      error: (e) => {
        this.error = e?.error?.message || 'Registration failed';
      }
    });
  }

  onZipBlur() {
    const zip = this.form.controls['zipCode'].value as string;
    if (!/^\d{5}$/.test(zip)) return;

    this.munis.near(zip, 16093).subscribe((opts) => {
      this.nearby = opts;
      if (opts.length === 1) {
        this.form.patchValue({ municipalityId: opts[0].id });
        this.autoPicked = true;
      } else {
        this.form.patchValue({ municipalityId: null });
        this.autoPicked = false;
      }
    });
  }
}
