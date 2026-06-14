import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink , RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss',
})
export class Sidebar implements OnInit{

  constructor(
    private route: Router,
  ){}

  ngOnInit(): void {
    
  }

  // onChangeRoute(routeName: string){
  //   this.route.navigate(['/app',routeName])
  // }

  onLogOut(){

  }

}
