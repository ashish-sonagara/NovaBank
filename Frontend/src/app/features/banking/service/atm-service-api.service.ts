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
    return this.http.post(`${this.baseUrl}/withdraw?accountNumber=${accountNumber}&amount=${amount}`,{});
  }

  transfer(fromAccount: number, toAccount: number, amount: number) {
    return this.http.post(`${this.baseUrl}/transfer?accountNumber1=${fromAccount}&accountNumber2=${toAccount}&amount=${amount}`,{});
  }

  fetchAllTransaction() {
    return this.http.get(`${this.baseUrl}/transactions`);
  }

  fetchLastNTransaction(n: number){
    return this.http.get(`${this.baseUrl}/last-n-transactions?n=${n}`)
  }
}