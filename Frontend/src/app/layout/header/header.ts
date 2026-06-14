import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header implements OnInit{

  pageTitle = 'Dashboard';
 
  constructor(
    private router: Router,
    private route : ActivatedRoute
  ) {}
 
  ngOnInit(): void {
    this.pageTitle = this.route.snapshot.data['title']
  }
}
