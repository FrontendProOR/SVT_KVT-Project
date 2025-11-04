import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rate } from '../models/Rate';

@Injectable({
  providedIn: 'root'
})
export class RateService {
  // API base URL for interacting with rate-related endpoints
  private apiBase = 'http://localhost:8080/api';
  // Endpoint URL specifically for rate operations
  private rateEndpoint = `${this.apiBase}/rates`;

  constructor(private httpClient: HttpClient) {}

  /**
   * Submits a new rate to the server, including facilityId.
   * @param rate - The rate object to be sent to the server.
   * @param facilityId - The ID of the facility being rated.
   * @returns An observable containing the newly created rate.
   */
  submitRate(rate: Rate, facilityId: number): Observable<Rate> {
    // Create a payload including the rate and facilityId
    const ratingDTO = {
      ...rate,         // Spread the rate properties
      facilityId: facilityId // Include facilityId in the payload
    };
    
    return this.httpClient.post<Rate>(this.rateEndpoint + '/newrating', ratingDTO, { headers: this.prepareHeaders() });
  }

  /**
   * Constructs HTTP headers for server requests, including authorization.
   * @returns HttpHeaders object with Content-Type and Authorization headers.
   */
  private prepareHeaders(): HttpHeaders {
    const authToken = this.retrieveAuthToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${authToken}`
    });
  }

  /**
   * Retrieves the authentication token from local storage.
   * @returns The token if available, or an empty string if not found.
   */
  private retrieveAuthToken(): string {
    const authToken = localStorage.getItem('token');
    return authToken || '';
  }
}
