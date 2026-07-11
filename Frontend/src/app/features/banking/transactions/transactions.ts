import { Component, OnInit, signal, computed, WritableSignal, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ATMServiceAPI } from '../service/atm-service-api.service';
import { TransactionDTO } from '../model/TransactionDTO.model';
import { ApiResponseStatus } from '../../../core/enums/ApiResponse';

@Component({
  selector: 'app-transactions',
  imports: [CommonModule, FormsModule],
  templateUrl: './transactions.html',
  styleUrl: './transactions.scss',
})
export class Transactions implements OnInit {

  transactionsList: WritableSignal<TransactionDTO[]> = signal([]);
  transactionLoader = false;
  noTransactionsFound = false;

  constructor(private atmServiceAPI: ATMServiceAPI) { }

  ngOnInit(): void {
    this.fetchTransactions('');
  }

  fetchTransactions(event: any) {
    this.transactionLoader = true;

    this.atmServiceAPI.fetchAllTransaction().subscribe((res: any) => {
      if (res.success === ApiResponseStatus.SUCCESS) {

        if (event === '') {
          this.transactionsList.set([]);
          this.transactionLoader = false;
        }

        const allTransaction: TransactionDTO[] = res.serviceResult;

        if (allTransaction.length !== 0) {
          allTransaction.forEach((newTransaction) => {

            const existing = this.transactionsList()
              .find(tx => tx.transactionId === newTransaction.transactionId);

            if (!existing) {
              this.transactionsList.update(transactions => [...transactions, newTransaction]);
            }

          });
        }

        this.noTransactionsFound = this.transactionsList().length === 0;
      }
    });
  }

}