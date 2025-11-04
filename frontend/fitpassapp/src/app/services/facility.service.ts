import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Facility } from '../models/Facility';

@Injectable({
  providedIn: 'root'
})
export class FacilityService {
  // Base URL for API endpoints related to facilities
  private apiBase = 'http://localhost:8080/api';
  // Endpoint URL for facilities operations
  private facilitiesEndpoint = `${this.apiBase}/facilities`;

  constructor(private httpClient: HttpClient) {}

  /**
   * Retrieves a list of all facilities.
   * @returns An observable containing an array of facility objects.
   */
  fetchAllFacilities(): Observable<Facility[]> {
    return this.httpClient.get<Facility[]>(this.facilitiesEndpoint, { headers: this.constructHeaders() });
  }

  /**
   * Fetches details of a specific facility by its ID.
   * @param facilityId - The ID of the facility to be fetched.
   * @returns An observable containing the facility object with the specified ID.
   */
  fetchFacilityById(facilityId: number): Observable<Facility> {
    return this.httpClient.get<Facility>(`${this.facilitiesEndpoint}/${facilityId}`, { headers: this.constructHeaders() });
  }

  /**
   * Adds a new facility.
   * @param newFacility - The facility object to be added.
   * @returns An observable containing the added facility object.
   */
  createFacility(newFacility: Facility): Observable<Facility> {
    return this.httpClient.post<Facility>(`${this.facilitiesEndpoint}/add`, newFacility, { headers: this.constructHeaders() });
  }

/**
   * Deletes a facility by its ID.
   * @param facilityId - The ID of the facility to be deleted.
   * @returns An observable indicating the result of the deletion operation.
   */
deleteFacility(facilityId: number): Observable<void> {
  return this.httpClient.delete<void>(`${this.facilitiesEndpoint}/${facilityId}`, { headers: this.constructHeaders() });
}

  getFacilityName(facility: Facility): string {
    return facility.facilityName; // Assuming 'name' is the property that holds the facility name
  }

  /**
   * Updates an existing facility.
   * @param updatedFacility - The facility object with updated details.
   * @returns An observable containing the updated facility object.
   */
  modifyFacility(updatedFacility: Facility): Observable<Facility> {
    const facilityUrl = `${this.facilitiesEndpoint}/${updatedFacility.id}`;
    return this.httpClient.put<Facility>(facilityUrl, updatedFacility, { headers: this.constructHeaders() });
  }

  /**
   * Constructs HTTP headers including the authorization token.
   * @returns An HttpHeaders object with Content-Type and Authorization headers.
   */
  private constructHeaders(): HttpHeaders {
    const token = this.retrieveAuthToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  /**
   * Retrieves the authentication token from local storage.
   * @returns The token string if it exists, or an empty string if not found.
   */
  private retrieveAuthToken(): string {
    const token = localStorage.getItem('token');
    return token || '';
  }
}
