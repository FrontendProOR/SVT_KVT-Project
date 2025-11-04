import { Component } from '@angular/core';
import { FacilityService } from '../services/facility.service';
import { Facility } from '../models/Facility';
import { CommonModule } from '@angular/common';
// import { ActivatedRoute, RouterLink } from '@angular/router';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
@Component({
  selector: 'app-facility-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './facility-details.component.html',
  styleUrl: './facility-details.component.scss',
  providers: [FacilityService]
})

export class FacilityDetailsComponent {
  facility: Facility | null = null;

  constructor(
    private route: ActivatedRoute,
    private facilityService: FacilityService,
    private router: Router,
    private snackBar: MatSnackBar
  ){}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const facilityId = params.get('id');
      if (facilityId) {
        this.loadFacilityDetails(parseInt(facilityId, 10));
      }
    });
  }

  loadFacilityDetails(id: number) {
    this.facilityService.fetchFacilityById(id).subscribe({
      next: (facility) => {
        this.facility = facility;
      },
      error: (error) => {
        console.error('Error while fetching facility by ID', error);
      }
    });
  }
  
  deleteFacility() {
    if (this.facility && this.facility.id) {
      this.facilityService.deleteFacility(this.facility.id).subscribe({
        next: () => {
          this.snackBar.open('Facility deleted successfully!', 'Close', {
            duration: 3000,
          });
          this.router.navigate(['/facilities']); // Navigate to the list of facilities
        },
        error: (error: Error) => {
          console.error('Error while deleting facility', error);
          this.snackBar.open('Failed to delete facility.', 'Close', {
            duration: 3000,
          });
        }
      });
    }
  }
}
