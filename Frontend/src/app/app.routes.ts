import { Routes } from '@angular/router';
import { Main } from './layout/main/main';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./features/auth/signin/signin').then((m) => m.Signin),
  },
  {
    path: 'signup',
    loadComponent: () => import('./features/auth/signup/signup').then((m) => m.Signup),
  },
  {
    path: 'forgot-password',
    loadComponent: () =>
      import('./features/auth/forgot-password/forgot-password').then((m) => m.ForgotPassword),
  },
  {
    path: 'app',
    component: Main,
    children: [
      {
        path: 'dashboard',
        loadComponent: () => import('./features/overview/dashboard/dashboard').then((m) => m.Dashboard),
        data: { title: 'DASHBOARD' }
      },
      {
        path: 'accounts',
        loadComponent: () => import('./features/banking/accounts/accounts').then((m) => m.Accounts),
        data: { title: 'ACCOUNTS' }
      },
      {
        path: 'transactions',
        loadComponent: () => import('./features/banking/transactions/transactions').then((m) => m.Transactions),
        data: { title: 'TRANSACTIONS' }
      },
      {
        path: 'fund-transfer',
        loadComponent: () => import('./features/banking/fund-transfer/fund-transfer').then((m) => m.FundTransfer),
        data: { title: 'FUND TRANSFER' }
      }
      ,
      {
        path: 'atm-services',
        loadComponent: () => import('./features/banking/atm-services/atm-services').then((m) => m.AtmServices),
        data: { title: 'ATM SERVICE' }
      },
      {
        path: 'analytics',
        loadComponent: () => import('./features/reports/analytics/analytics').then((m) => m.Analytics),
        data: { title: 'ANALYTICS' }
      },
      {
        path: 'settings',
        loadComponent: () => import('./features/settings/settings/settings').then((m) => m.Settings),
        data: { title: 'SETTINGS' }
      }
    ]
  },
  {
    path: '**',
    redirectTo: '',
  },
];
