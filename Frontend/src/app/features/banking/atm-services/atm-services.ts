import { Component, signal } from '@angular/core';
import { ATMServiceAPI } from '../service/atm-service-api.service';
import { CustomToastService } from '../../../shared/service/customToast.service';
import { ApiResponseStatus } from '../../../core/enums/ApiResponse';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-atm-services',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './atm-services.html',
  styleUrl: './atm-services.scss',
})
export class ATMServices {
  depositForm!: FormGroup;
  withdrawForm!: FormGroup;
  transferForm!: FormGroup;

  recentTransactions = signal<any[]>([]);

  constructor(
    private fb: FormBuilder,
    private atmAPI: ATMServiceAPI,
    private toastr: CustomToastService,
  ) {
    this.depositForm = this.fb.group({
      accountNumber: ['', [Validators.required, Validators.pattern(/^\d{8,18}$/)]],
      amount: ['', [Validators.required, Validators.min(1)]],
    });

    this.withdrawForm = this.fb.group({
      accountNumber: ['', [Validators.required, Validators.pattern(/^\d{8,18}$/)]],
      amount: ['', [Validators.required, Validators.min(1)]],
    });

    this.transferForm = this.fb.group({
      fromAccountNumber: ['', [Validators.required, Validators.pattern(/^\d{8,18}$/)]],

      toAccountNumber: ['', [Validators.required, Validators.pattern(/^\d{8,18}$/)]],

      amount: ['', [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit() {
    this.loadRecentTransactions();
  }

  depositMoney() {

  if (this.depositForm.invalid) {
    this.depositForm.markAllAsTouched();
    return;
  }

  const { accountNumber, amount } = this.depositForm.value;

  this.atmAPI.deposit(accountNumber, amount)
    .subscribe((res: any) => {

      if (res.success === ApiResponseStatus.SUCCESS) {

        this.toastr.showSuccess('Success', res.message);

        this.depositForm.reset();

        this.loadRecentTransactions();

      } else {

        this.toastr.showError('Error', res.message);

      }

    });

}

 withdrawMoney() {

  if (this.withdrawForm.invalid) {
    this.withdrawForm.markAllAsTouched();
    return;
  }

  const { accountNumber, amount } = this.withdrawForm.value;

  this.atmAPI.withdraw(accountNumber, amount)
    .subscribe((res: any) => {

      if (res.success === ApiResponseStatus.SUCCESS) {

        this.toastr.showSuccess('Success', res.message);

        this.withdrawForm.reset();

        this.loadRecentTransactions();

      } else {

        this.toastr.showError('Error', res.message);

      }

    });

}

  transferMoney() {

  if (this.transferForm.invalid) {
    this.transferForm.markAllAsTouched();
    return;
  }

  const {
    fromAccountNumber,
    toAccountNumber,
    amount
  } = this.transferForm.value;

  this.atmAPI
    .transfer(
      fromAccountNumber,
      toAccountNumber,
      amount
    )
    .subscribe((res: any) => {

      if (res.success === ApiResponseStatus.SUCCESS) {

        this.toastr.showSuccess('Success', res.message);

        this.transferForm.reset();

        this.loadRecentTransactions();

      } else {

        this.toastr.showError('Error', res.message);

      }

    });

}

  loadRecentTransactions() {
    this.atmAPI.fetchRecentTransactions().subscribe((res: any) => {
      if (res.success === ApiResponseStatus.SUCCESS) {
        this.recentTransactions.set(res.serviceResult);
      }
    });
  }
}