import { Injectable, signal } from '@angular/core';

export interface ToastMessage {
  id: number;
  title: string;
  message: string;
  type: 'success' | 'error' | 'warning';
}

@Injectable({
  providedIn: 'root',
})
export class CustomToastService {
  // A reactive list holding all visible notifications
  toasts = signal<ToastMessage[]>([]);
  private counter = 0;

  showSuccess(title: string, message: string) {
    this.addToast(title, message, 'success');
  }

  showError(title: string, message: string) {
    this.addToast(title, message, 'error');
  }

  showWarning(title: string, message: string) {
    this.addToast(title, message, 'warning');
  }

  private addToast(title: string, message: string, type: 'success' | 'error' | 'warning') {
    const id = this.counter++;
    const newToast: ToastMessage = { id, title, message, type };

    // Update the signal array cleanly
    this.toasts.update((current) => [...current, newToast]);

    // Automatically remove the toast card after exactly 3 seconds (3000ms)
    setTimeout(() => {
      this.remove(id);
    }, 3000);
  }

  remove(id: number) {
    this.toasts.update((current) => current.filter((t) => t.id !== id));
  }
}