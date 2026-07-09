import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ATMServiceAPI {

  private readonly baseUrl = 'http://localhost:8080/bank-service';

  constructor(private http: HttpClient) {}

  deposit(accountNumber: number, amount: number) {
    return this.http.post(`${this.baseUrl}/deposit?accountNumber=${accountNumber}&amount=${amount}`,{});
  }

  withdraw(accountNumber: number, amount: number) {
    return this.http.post(`${this.baseUrl}/withdraw?accountID=${accountNumber}&amount=${amount}`,{});
  }

  transfer(fromAccount: number, toAccount: number, amount: number) {
    return this.http.post(`${this.baseUrl}/transfer?fromAccount=${fromAccount}&toAccount=${toAccount}&amount=${amount}`,{});
  }

  fetchRecentTransactions() {
    return this.http.get(`${this.baseUrl}/recent-transactions`);
  }

}