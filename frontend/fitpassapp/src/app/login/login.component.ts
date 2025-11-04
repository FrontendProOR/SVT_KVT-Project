import { Component } from '@angular/core';
import { AuthenticationService } from '../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  providers: [AuthenticationService]
})
export class LoginComponent{

  email: string = '';
  password: string = '';

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  onLogin(): void {
    this.authenticationService.loginUser(this.email, this.password).subscribe({
      next: (response) => {
        console.log('Login successful, setting token:', response.accessToken);
        localStorage.setItem('token', response.accessToken);

        // Fetch user profile to get the address
        this.authenticationService.fetchUserProfile().subscribe({
          next: (profile) => {
            const addressParts = profile.address.split(', ');
            const city = addressParts[addressParts.length - 1];
            localStorage.setItem('userCity', city);

            this.router.navigate(['homepage']);  // Redirect to a protected route after successful login
          },
          error: (error) => {
            console.error('Error fetching user profile', error);
          }
        });
      },
      error: (error) => {
        console.error('Login failed', error);
      }
    });
  }
  
}