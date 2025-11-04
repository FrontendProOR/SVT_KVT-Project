import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../models/Comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  // The base URL for the API endpoints
  private apiBaseUrl = 'http://localhost:8080/api';
  // URL for the comments endpoint
  private commentsEndpoint = `${this.apiBaseUrl}/comments`;

  constructor(private httpClient: HttpClient) {}

  /**
   * Adds a new comment to the server.
   * @param comment - The comment object to be sent to the server.
   * @returns An observable containing the created comment.
   */
  submitComment(comment: Comment): Observable<Comment> {
    const headers = this.buildHeaders();
    return this.httpClient.post<Comment>(this.commentsEndpoint, comment, { headers });
  }

  /**
   * Constructs HTTP headers for requests.
   * @returns The HttpHeaders object including Content-Type and Authorization headers.
   */
  private buildHeaders(): HttpHeaders {
    const token = this.retrieveToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  /**
   * Retrieves the authentication token from local storage.
   * @returns The token string if it exists, or an empty string if not found.
   */
  private retrieveToken(): string {
    const token = localStorage.getItem('token');
    return token || '';
  }
}
