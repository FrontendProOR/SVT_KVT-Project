import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { Facility } from './../models/Facility';

// Interface defining the structure of the authentication response
interface AuthResponse {
  accessToken: string;
  expiresIn: number;
}

// Interface for user sign-up data
interface SignUpData {
  email: string;
  address: string;
  password: string;
}

// Interface representing details of an account request
interface AccountDetail {
  id: number;
  email: string;
  address: string;
  status: string;
  createdAt: Date;
}

// Interface defining the user profile structure
interface UserInfo {
  id: number;
  email: string;
  password: string;
  name: string | null;
  surname: string | null;
  createdAt: Date;
  phoneNumber: string | null;
  birthday: Date | null;
  address: string;
  city: string | null;
  zipCode: string | null;
  image: string | null;
  manages: any[];
  exercises: any[];
  reviews: any[];
  role: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  // Base URL for the API
  private apiUrl = 'http://localhost:8080/api';

  constructor(private httpClient: HttpClient) {}

  /**
   * Logs in a user with provided email and password.
   * @param email - The user's email address.
   * @param password - The user's password.
   * @returns An observable of the authentication response containing the access token and expiration time.
   */
  loginUser(email: string, password: string): Observable<AuthResponse> {
    const endpoint = `${this.apiUrl}/users/login`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<AuthResponse>(endpoint, { email, password }, { headers });
  }

  /**
   * Registers a new account with provided sign-up data.
   * @param signUpData - Data required for account registration including email, address, and password.
   * @returns An observable of the sign-up data.
   */
  registerAccount(signUpData: SignUpData): Observable<SignUpData> {
    const endpoint = `${this.apiUrl}/accountRequests/create`;
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.httpClient.post<SignUpData>(endpoint, signUpData, { headers });
  }

  /**
   * Retrieves a list of pending account requests.
   * @returns An observable of account details array.
   */
  fetchPendingAccountRequests(): Observable<AccountDetail[]> {
    const endpoint = `${this.apiUrl}/accountRequests/pending`;
    const token = this.retrieveToken();

    if (!token) {
      console.error('Token is missing from storage');
      return of([]); 
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<AccountDetail[]>(endpoint, { headers });
  }

  /**
   * Approves a specific account request by its ID.
   * @param requestId - The ID of the account request to be approved.
   * @returns An observable of void, indicating success or failure.
   */
  approveAccountRequest(requestId: number): Observable<void> {
    const endpoint = `${this.apiUrl}/accountRequests/${requestId}/accept`;
    const token = this.retrieveToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.post<void>(endpoint, {}, { headers }).pipe(
      tap(() => console.log(`Approved account request ID ${requestId}`))
    );
  }

  /**
   * Denies a specific account request by its ID.
   * @param requestId - The ID of the account request to be denied.
   * @returns An observable of void, indicating success or failure.
   */
  denyAccountRequest(requestId: number): Observable<void> {
    const endpoint = `${this.apiUrl}/accountRequests/${requestId}/reject`;
    const token = this.retrieveToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.post<void>(endpoint, {}, { headers }).pipe(
      tap(() => console.log(`Denied account request ID ${requestId}`))
    );
  }

  /**
   * Fetches the current user's profile information.
   * @returns An observable of the user's profile data.
   */
  fetchUserProfile(): Observable<UserInfo> {
    const endpoint = `${this.apiUrl}/users/profile`;
    const token = this.retrieveToken();
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.get<UserInfo>(endpoint, { headers });
  }

  /**
   * Updates the user's profile with new data.
   * @param userProfile - The updated profile information.
   * @returns An observable of the response.
   */
  modifyUserProfile(userProfile: any): Observable<any> {
    const endpoint = `${this.apiUrl}/users/profile/edit`;
    const token = this.retrieveToken();

    if (!token) {
      console.error('Token is missing from storage');
      return of(null); 
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this.httpClient.put<any>(endpoint, userProfile, { headers });
  }

  /**
   * Changes the user's password.
   * @param oldPwd - The current password.
   * @param newPwd - The new password to be set.
   * @returns An observable of the response.
   */
  updatePassword(oldPwd: string, newPwd: string): Observable<any> {
    const endpoint = `${this.apiUrl}/users/change-password`;
    const token = this.retrieveToken();

    if (!token) {
      console.error('Token is missing from storage');
      return of(null); 
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    const payload = { oldPassword: oldPwd, newPassword: newPwd };

    return this.httpClient.put<any>(endpoint, payload, { headers });
  }

  fetchUserExercises(): Observable<any> {
    const endpoint = `${this.apiUrl}/exercises/user-exercises`;
    const token = this.retrieveToken();
  
    if (!token) {
      console.error('Token is missing from storage');
      return of(null);
    }
  
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  
    return this.httpClient.get<any>(endpoint, { headers });
  }
  
  getFacilityById(facilityId: number): Observable<Facility> {
    return this.httpClient.get<Facility>(`${this.apiUrl}/facilities/${facilityId}`);
}



  /**
   * Retrieves the authentication token from local storage.
   * @returns The token if present, or null if not found.
   */
  private retrieveToken(): string | null {
    if (typeof window !== 'undefined' && localStorage) {
      const token = localStorage.getItem('token');
      console.log('Token retrieved from storage:', token); 
      return token;
    }
    return null;
  }
}
