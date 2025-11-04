import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from '../models/Review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  // The base URL for API endpoints related to reviews
  private apiBaseUrl = 'http://localhost:8080/api';
  // Endpoint URL for review operations
  private reviewEndpoint = `${this.apiBaseUrl}/reviews`;

  constructor(private httpClient: HttpClient) {}

  /**
   * Submits a new review to the server.
   * @param review - The review object to be sent to the server.
   * @returns An observable containing the submitted review.
   */
  submitReview(review: Review): Observable<Review> {
    return this.httpClient.post<Review>(this.reviewEndpoint, review, { headers: this.createHeaders() });
  }

  /**
   * Creates HTTP headers for API requests, including authentication.
   * @returns HttpHeaders object with Content-Type and Authorization headers.
   */
  private createHeaders(): HttpHeaders {
    const authToken = this.fetchAuthToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${authToken}`
    });
  }

  /**
   * Retrieves the authentication token from local storage.
   * @returns The token if available, or an empty string if not found.
   */
  private fetchAuthToken(): string {
    const authToken = localStorage.getItem('token');
    return authToken || '';
  }
}
