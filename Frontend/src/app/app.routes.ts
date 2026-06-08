import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () => import('./features/auth/signin/signin').then(m => m.Signin)
    },
    {
        path:'signup',
        loadComponent: () => import('./features/auth/signup/signup').then(m => m.Signup)
    },
    {
        path:'forgot-password',
        loadComponent: () => import('./features/auth/forgot-password/forgot-password').then(m => m.ForgotPassword)
    },

//     {
//     path: 'app',
//     component: MainLayoutComponent, // This component wraps your NexaBank sidebar layout
//     children: [
//       {
//         path: '',
//         redirectTo: 'dashboard',
//         pathMatch: 'full'
//       },
//       {
//         path: 'dashboard',
//         loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent)
//       },
//       {
//         path: 'banking',
//         children: [
//           {
//             path: 'accounts',
//             loadComponent: () => import('./features/banking/accounts/accounts.component').then(m => m.AccountsComponent)
//           },
//           {
//             path: 'transactions',
//             loadComponent: () => import('./features/banking/transactions/transactions.component').then(m => m.TransactionsComponent)
//           }
//         ]
//       },
//       {
//         path: 'reports',
//         loadComponent: () => import('./features/reports/analytics/analytics.component').then(m => m.AnalyticsComponent)
//       },
//       {
//         path: 'settings',
//         loadComponent: () => import('./features/settings/settings.component').then(m => m.SettingsComponent)
//       }
//     ]
//   },

//   // Wildcard redirect for 404s
//   {
//     path: '**',
//     redirectTo: ''
//   }
];
