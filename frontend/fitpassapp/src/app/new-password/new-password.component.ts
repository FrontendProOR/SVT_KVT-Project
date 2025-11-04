import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './new-password.component.html',
  styleUrl: './new-password.component.scss',
  providers: [AuthenticationService]
})
export class NewPasswordComponent {

  oldPassword: string = '';
  newPassword: string = '';
  confirmNewPassword: string = '';
  errorMessage: string = '';

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  submitChangePassword(): void {
    if (this.newPassword !== this.confirmNewPassword) {
      this.errorMessage = 'New passwords do not match';
      return;
    }

    this.authenticationService.updatePassword(this.oldPassword, this.newPassword).subscribe({
      next: () => {
        console.log('Password changed successfully');
        this.errorMessage = '';
        
        // this.router.navigate(['/']);
        this.logout();
      },
      error: (error) => {
        console.error('Error changing password:', error);
        this.errorMessage = 'Failed to change password';
      }
    });
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('userCity');
    this.router.navigate(['/login']);
  }
}
