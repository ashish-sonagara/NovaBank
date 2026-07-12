import { Component, signal, WritableSignal } from '@angular/core';
import { ATMServiceAPI } from '../service/atm-service-api.service';
import { CustomToastService } from '../../../shared/service/customToast.service';
import { ApiResponseStatus } from '../../../core/enums/ApiResponse';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { TransactionDTO } from '../model/TransactionDTO.model';

@Component({
  selector: 'app-atm-services',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './atm-services.html',
  styleUrl: './atm-services.scss',
})
export class ATMServices {
  depositForm!: FormGroup;
  withdrawForm!: FormGroup;
  transferForm!: FormGroup;

  transactionsList: WritableSignal<TransactionDTO[]> = signal([]);
  transactionLoader = false;
  noTransactionsFound = false;

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
    this.fetchTransactions('');
  }

  depositMoney() {
    if (this.depositForm.invalid) {
      this.depositForm.markAllAsTouched();
      return;
    }

    const { accountNumber, amount } = this.depositForm.value;

    this.atmAPI.deposit(accountNumber, amount).subscribe((res: any) => {
      if (res.success === ApiResponseStatus.SUCCESS) {
        this.toastr.showSuccess('Success', res.message);
        this.depositForm.reset();
        this.fetchTransactions('');
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

    this.atmAPI.withdraw(accountNumber, amount).subscribe((res: any) => {
      if (res.success === ApiResponseStatus.SUCCESS) {
        this.toastr.showSuccess('Success', res.message);
        this.withdrawForm.reset();
        this.fetchTransactions('');
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

    const { fromAccountNumber, toAccountNumber, amount } = this.transferForm.value;

    this.atmAPI.transfer(fromAccountNumber, toAccountNumber, amount).subscribe((res: any) => {
      if (res.success === ApiResponseStatus.SUCCESS) {
        this.toastr.showSuccess('Success', res.message);
        this.transferForm.reset();
        this.fetchTransactions('');
      } else {
        this.toastr.showError('Error', res.message);
      }
    });
  }


  fetchTransactions(event: any) {
    this.transactionLoader = true;

    this.atmAPI.fetchLastNTransaction(5).subscribe((res: any) => {
      if (res.success === ApiResponseStatus.SUCCESS) {
        if (event === '') {
          this.transactionsList.set([]);
          this.transactionLoader = false;
        }

        const allTransaction: TransactionDTO[] = res.serviceResult;

        if (allTransaction.length !== 0) {
          allTransaction.forEach((newTransaction) => {
            const existing = this.transactionsList().find(
              (tx) => tx.transactionId === newTransaction.transactionId,
            );

            if (!existing) {
              this.transactionsList.update((transactions) => [...transactions, newTransaction]);
            }
          });
        }

        this.noTransactionsFound = this.transactionsList().length === 0;
      }
    });
  }

}
