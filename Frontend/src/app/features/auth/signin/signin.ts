import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [],
  templateUrl: './signin.html',
  styleUrl: './signin.scss',
})
export class Signin implements OnInit{

  constructor(
    private route: Router
  ){}

  ngOnInit(): void {
    
  }

  routeToApp(){
    console.log("happy to listen")
    this.route.navigateByUrl('/app/dashboard')
  }
}
