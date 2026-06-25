import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AccountsAPI } from '../service/accounts-api.service';
import { CustomToastService } from '../../../shared/service/customToast.service';
import { BankAccountDTO } from '../model/bank-account-dto.model';

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
  selectedAccount: BankAccountDTO = new BankAccountDTO();

  pageNo: number = 1;
  pageSize = 10;
  accountLoader: boolean = false;
  totalRecords: number = 0;
  noAccountsFound: boolean = false;
  accountsList: BankAccountDTO[] = [];
  searchAccount: string = '';

  constructor(
    private fb: FormBuilder,
    private accountAPI: AccountsAPI,
    private toastrService: CustomToastService
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

  fetchAccounts(event: any) {
    this.accountLoader = true;

    this.accountAPI.fetchAllAccounts().subscribe((res:any) => {
      if (res.success === 1) {
        if (event === '') {
          this.accountsList = []
          this.accountLoader = false
        }
        this.totalRecords = res.totalRecords;
        const accountRecords: BankAccountDTO[] = res.serviceResult;
        
        if (accountRecords.length > 0) {
          // Clean fix for handling unique array loading safely:
          accountRecords.forEach(newAccount => {
            const exists = this.accountsList.some(acc => acc.id === newAccount.id);
            if (!exists) {
              this.accountsList.push(newAccount);
            }
          });
        }

        this.noAccountsFound = this.accountsList.length === 0;
        // this.cdr.detectChanges();  i dont know why i do need this casue in my current company projet i dont need to say the angular to look for change detection as the api gives reposne it changes 

      }
    })
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

      this.accountAPI.updateAccount(accountDetails).subscribe((res:any) => {
        if (res.success === 1) {
          this.selectedAccount = new BankAccountDTO();
          this.onCloseAccount();
          this.fetchAccounts('');
          this.accountForm.reset();
          this.toastrService.showSuccess("Success Message", res.message);
        }
        else {
          this.toastrService.showError("Error Message", res.message);
        }
      })

    } else {

      const accountDetails: BankAccountDTO = { ...formValues };
      this.accountAPI.createAccount(accountDetails).subscribe((res:any) => {
        if (res.success === 1) {
          this.onCloseAccount();
          this.fetchAccounts('');
          this.accountForm.reset()
        }
        else {
          this.toastrService.showError("Error Message", res.message);
        }
      })
    }

  }

  editAccount(bankAccount: BankAccountDTO) {
    this.isEditMode = true
    this.selectedAccount = bankAccount
    console.log("for edit --> ", bankAccount)
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
    this.accountAPI.deleteAccountByID(accountId).subscribe((res:any) => {
      if (res.success === 1){
        this.accountsList = this.accountsList.filter((account: BankAccountDTO) => {
          return account.id !== accountId;
        })
        this.toastrService.showSuccess("Success Message", res.message);
      }
      else{
        this.toastrService.showError("Error Message", res.message);
      }
    })
  }

  openCreateAccount() {
    this.isModalOpen = true;
  }

  onCloseAccount() {
    this.isModalOpen = false;
  }
}
