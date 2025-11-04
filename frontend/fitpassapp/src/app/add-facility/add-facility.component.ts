import { Component } from '@angular/core';
import { FormGroup, FormsModule } from '@angular/forms';
import { FacilityService } from '../services/facility.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Facility } from '../models/Facility';
import { Discipline } from '../models/Discipline';
import { WorkDay } from '../models/Workday';

@Component({
  selector: 'app-add-facility',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-facility.component.html',
  styleUrl: './add-facility.component.scss',
  providers: [FacilityService]
})

export class AddFacilityComponent {
  facility: Facility = new Facility();
  daysOfWeek: string[] = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];

  constructor(private facilityService: FacilityService, private router: Router) {}

  addFacility() {
    this.facilityService.createFacility(this.facility).subscribe(
      (response: Facility) => {
        console.log('Facility added successfully', response);
        this.router.navigate(['homepage']);
        // You can add a redirection or a success message here
      },
      (error: any) => {
        console.error('Failed to add facility', error);
      }
    );
  }

  addDiscipline() {
    this.facility.disciplines.push(new Discipline(''));
  }

  removeDiscipline(index: number) {
    this.facility.disciplines.splice(index, 1);
  }

  addWorkDay() {
    this.facility.workDays.push(new WorkDay(new Date(), '', '', ''));
  }

  removeWorkDay(index: number) {
    this.facility.workDays.splice(index, 1);
  }

}