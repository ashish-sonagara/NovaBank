import { HttpClient } from "@angular/common/http";
import { BankAccountDTO } from "../model/bank-account-dto.model";

export class AccountsAPI {
    
    constructor(
        private http: HttpClient,
    ){}

    createAccount(body: BankAccountDTO){
      return this.http.post('http://localhost:8080/manage-accounts/create-account', body);
    }

    updateAccount(body: BankAccountDTO){
       return this.http.put('http://localhost:8080/manage-accounts/updateAccount', body);
    }

    fetchAllAccounts(){
        return this.http.get('http://localhost:8080/manage-accounts/get-accounts');
    }

    deleteAccountByID(accountId: number){
        return this.http.delete(`http://localhost:8080/manage-accounts/delete-account/${accountId}`)
    }
}