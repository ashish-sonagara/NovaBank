import { HttpClient } from '@angular/common/http';
import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BankAccountDTO } from '../model/bank-account-dto.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-accounts',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './accounts.html',
  styleUrl: './accounts.scss',
})
export class Accounts implements OnInit {
  isModalOpen = false;
  accountForm: FormGroup;
  isEditMode: boolean = false;
  selectedAccount: BankAccountDTO = new BankAccountDTO()
  accountsList: BankAccountDTO[] = [];

  private http = inject(HttpClient);

  constructor(
    private fb: FormBuilder,
    private cdr: ChangeDetectorRef
  ) {
    this.accountForm = this.fb.group({
      accountOwner: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      phoneNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      bankName: ['', Validators.required],
      accountType: ['', Validators.required],
      currentBalance: ['', [Validators.required, Validators.min(1000)]],
      accountStatus: ['ACTIVE']
    });
  }

  ngOnInit(): void {
    this.fetchAccounts('')
  }

  onSubmit() {
    if (this.accountForm.invalid) {
      this.accountForm.markAllAsTouched();
      return;
    }

    const formValues = this.accountForm.value;

    if (this.isEditMode) {
      const accountDetails: BankAccountDTO = {
        ...formValues,
        id: this.selectedAccount.id,
        accountNumber: this.selectedAccount.accountNumber
      };

      this.http.put('http://localhost:8080/manage-accounts/updateAccount', accountDetails, { responseType: 'text' as 'json' })
        .subscribe({
          next: (data) => {
            console.log("Successful update", data);
            this.selectedAccount = new BankAccountDTO();
            this.onCloseAccount();
            this.fetchAccounts('');
            this.accountForm.reset()
          },
          error: (error) => console.error('Oops, something went wrong!', error)
        });

    } else {
      const accountDetails: BankAccountDTO = { ...formValues };
      console.log(accountDetails)
      this.http.post('http://localhost:8080/manage-accounts/create-account', accountDetails, { responseType: 'text' as 'json' })
        .subscribe({
          next: (data) => {
            console.log("Successful create", data);
            this.onCloseAccount();
            this.fetchAccounts('');
            this.accountForm.reset()
          },
          error: (error) => console.error('Oops, something went wrong!', error)
        });
    }
  }

  fetchAccounts(event: any) {
    this.http.get('http://localhost:8080/manage-accounts/get-accounts').subscribe({
      next: (data) => {
        this.accountsList = data as any
        console.log(this.accountsList)
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Oops, something went wrong!', error); // Handle errors
      },
    })
  }

  editAccount(bankAccount: BankAccountDTO) {
    this.isEditMode = true
    this.selectedAccount = bankAccount
    console.log("for edit --> " , bankAccount)
    this.accountForm.patchValue({
      accountOwner: bankAccount.accountOwner,
      phoneNumber: bankAccount.phoneNumber,
      email: bankAccount.email,
      bankName: bankAccount.bankName,
      accountType: bankAccount.accountType,
      currentBalance: bankAccount.currentBalance,
      accountStatus: bankAccount.accountStatus || 'ACTIVE'
    });

    this.openCreateAccount()
  }

  deleteAccount(accountId: number) {
    this.http.delete(`http://localhost:8080/manage-accounts/delete-account/${accountId}`, { responseType: 'text' }).subscribe({
      next: (data) => {
        this.accountsList = this.accountsList.filter((account) => {
          return account.id != accountId
        })
        console.log("Account Deleted Succesfully!")
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Oops, something went wrong!', error); // Handle errors
      },
    })
  }

  openCreateAccount() {
    this.isModalOpen = true;
  }

  onCloseAccount() {
    this.isModalOpen = false;
  }
}
