export class TransactionDTO {

  transactionId: number;
  transactionType: string;
  transactionAmount: number;
  date: string;
  transactionStatus: string;
  accountOwner: string;
  bankName: string;
  accountNumber: number;

  constructor() {
    this.transactionId = null as any;
    this.transactionType = '';
    this.transactionAmount = 0;
    this.date = '';
    this.transactionStatus = '';
    this.accountOwner = '';
    this.bankName = '';
    this.accountNumber = null as any;
  }

}