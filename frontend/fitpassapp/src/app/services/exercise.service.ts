import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Exercise } from '../models/Exercise';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {
  // The endpoint URL for exercise-related API operations
  private endpointUrl = 'http://localhost:8080/api/exercises';

  constructor(private httpClient: HttpClient) {}

  /**
   * Adds a new exercise to the server.
   * @param exercise - The exercise object to be sent to the server.
   * @returns An observable containing the added exercise data.
   */
  createExercise(exercise: Exercise): Observable<Exercise> {
    return this.httpClient.post<Exercise>(this.endpointUrl, exercise, { headers: this.composeHeaders() });
  }

  /**
   * Constructs HTTP headers for making requests to the server.
   * @returns The HttpHeaders object with Content-Type and Authorization headers.
   */
  private composeHeaders(): HttpHeaders {
    const authToken = this.retrieveAuthToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${authToken}`
    });
  }

  /**
   * Retrieves the authentication token from local storage.
   * @returns The token if present, or an empty string if not found.
   */
  private retrieveAuthToken(): string {
    const authToken = localStorage.getItem('token');
    return authToken || '';
  }
}
