import { Component, OnInit } from '@angular/core';
import { FacilityService } from '../services/facility.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Facility } from '../models/Facility';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-facility-edit',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './edit-facility.component.html',
  styleUrl: './edit-facility.component.scss',
  providers: [FacilityService]
})

export class EditFacilityComponent implements OnInit {
  facility!: Facility;
  facilityId!: number;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private facilityService: FacilityService 
  ){}
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = +params['id']; 
      if (id) {
        this.loadFacilityDetails(id);
      }
    });
  }

  saveFacility(): void {
    this.facilityService.modifyFacility(this.facility).subscribe(
      () => {
        console.log('Facility updated successfully');
        this.router.navigate(['/facility', this.facility.id]);
      },
      (error) => {
        console.error('Error updating facility:', error);
      }
    );
  }

  loadFacilityDetails(id: number): void {
    this.facilityService.fetchFacilityById(id).subscribe(
      (facility: Facility) => {
        this.facility = facility;
      },
      (error) => {
        console.error('Error loading facility details:', error);
      }
    );
  }

}
