import { Component, OnInit } from '@angular/core';
import { FacilityService } from '../services/facility.service';
import { Facility } from '../models/Facility';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [HttpClientModule, CommonModule, RouterLink, FormsModule],
  templateUrl: './homepage.component.html',
  styleUrl: './homepage.component.scss',
  providers: [FacilityService]
})
export class HomepageComponent implements OnInit {
  facilities: Facility[] = [];
  filteredFacilities: Facility[] = [];
  searchParams: any = {
    city: '',
    discipline: '',
    minRating: '',
    maxRating: '',
    fromTime: '',
    untilTime: ''
  };

  constructor(private facilityService: FacilityService) {}

  ngOnInit(): void {
    this.loadActiveFacilities();
  }

  loadActiveFacilities(): void {
    this.facilityService.fetchAllFacilities().subscribe(
      (data: Facility[]) => {
        this.facilities = data;
        this.applyFilter(); 
      },
      error => {
        console.error('Error loading active facilities', error);
      }
    );
  }

  applyFilter(): void {
    this.filteredFacilities = this.facilities.filter(facility =>
      this.matchesSearchCriteria(facility)
    );
  }

  matchesSearchCriteria(facility: Facility): boolean {
    const { city, discipline, minRating, maxRating, fromTime, untilTime } = this.searchParams;

    if (city && !facility.city.toLowerCase().includes(city.toLowerCase())) {
      return false;
    }

    if (discipline && !this.facilityHasDiscipline(facility, discipline)) {
      return false;
    }

    if (minRating && facility.totalRating < parseFloat(minRating)) {
      return false;
    }
    if (maxRating && facility.totalRating > parseFloat(maxRating)) {
      return false;
    }

    if (!this.workDaysMatchCriteria(facility)) {
      return false;
    }

    return true;
  }

  timeRangeOverlaps(workdayStart: string, workdayEnd: string, fromTime: string, untilTime: string): boolean {
    return true; 
  }

  workDaysMatchCriteria(facility: Facility): boolean {
    const { fromTime, untilTime } = this.searchParams;

    if (!fromTime && !untilTime) {
      return true;
    }

    return facility.workDays.some(workday =>
      this.timeRangeOverlaps(workday.fromTime, workday.untilTime, fromTime, untilTime)
    );
  }
  
  private facilityHasDiscipline(facility: Facility, discipline: string): boolean {
    return facility.disciplines.some(d => d.name.toLowerCase().includes(discipline.toLowerCase()));
  }

  clearFilters(): void {
    this.searchParams = {
      city: '',
      discipline: '',
      minRating: '',
      maxRating: '',
      fromTime: '',
      untilTime: ''
    };
    this.applyFilter();
  }

}