export class BankAccountDTO {

  id: number;
  accountOwner: string;
  phoneNumber: string;
  email: string;
  bankName: string;
  accountType: string;
  currentBalance: number;
  accountNumber: number;
  accountStatus: string;

  constructor() {
    this.id = null as any;
    this.accountNumber = null as any
    this.accountOwner = '';
    this.phoneNumber = '';
    this.email = '';
    this.bankName = '';
    this.accountType = '';
    this.currentBalance = 0;
    this.accountStatus = "ACTIVE";
  }
}