import { HttpClientModule } from '@angular/common/http';
import { AuthenticationService } from '../services/auth.service';
import { FormsModule } from '@angular/forms';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [HttpClientModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
  providers: [AuthenticationService]
})

export class RegisterComponent {
  email: string = '';
  password: string = '';
  address: string = '';

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  onRegister(): void {
    const signUpAccountRequest = {
      email: this.email,
      password: this.password,
      address: this.address
    };

    this.authenticationService.registerAccount(signUpAccountRequest).subscribe({
      next: (response) => {
        console.log('Account request created successfully', response);
        this.router.navigate(['']);  // Redirect to a protected route after successful login
      },
      error: (error) => {
        console.error('Failed to create account request', error);
      }
    });
  }

}
