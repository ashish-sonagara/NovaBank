import { Component } from '@angular/core';

@Component({
  selector: 'app-accounts',
  imports: [],
  templateUrl: './accounts.html',
  styleUrl: './accounts.scss',
})
export class Accounts {

isModalOpen = false;

  openCreateAccount() {
    this.isModalOpen = true;
  }

  onCloseAccount(){
    this.isModalOpen = false
  }
}
