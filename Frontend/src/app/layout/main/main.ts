import { Component } from '@angular/core';
import { Sidebar } from "../sidebar/sidebar";
import { Header } from "../header/header";
import { RouterOutlet } from '@angular/router';
import { Toastr } from '../../shared/components/toastr/toastr';

@Component({
  selector: 'app-main',
  imports: [Sidebar, Header, RouterOutlet, Toastr],
  templateUrl: './main.html',
  styleUrl: './main.scss',
})
export class Main {}
