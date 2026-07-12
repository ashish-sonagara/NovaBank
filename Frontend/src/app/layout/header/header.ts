import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.html',
  styleUrl: './header.scss',
})
export class Header implements OnInit {

  pageTitle = 'Dashboard';

  constructor(
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    )
      .subscribe(() => {
        let route = this.route

        while (route.firstChild) {
          route = route.firstChild
        }

        this.pageTitle = route.snapshot.data['title']
      })
  }
}
