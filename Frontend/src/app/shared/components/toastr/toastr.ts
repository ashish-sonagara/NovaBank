import { Component } from '@angular/core';
import { CustomToastService } from '../../service/customToast.service';

@Component({
  selector: 'app-toastr',
  imports: [],
  templateUrl: './toastr.html',
  styleUrl: './toastr.scss',
})
export class Toastr {
 constructor(public toastService: CustomToastService) {} 
}
