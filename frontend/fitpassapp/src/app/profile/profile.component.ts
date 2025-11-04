import { Component } from '@angular/core';
import { AuthenticationService } from '../services/auth.service';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { FacilityService } from '../services/facility.service'; 
import { forkJoin } from 'rxjs';
import { map } from 'rxjs/operators'; // Import map
import { Observable } from 'rxjs';

// Define an interface for Exercise
interface Exercise {
  facilityId: number;
  startTime: Date;
  endTime: Date;
  facilityName: string;
  status: string;
}

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
  providers: [AuthenticationService, DatePipe]
})
export class ProfileComponent {
  userProfile: any;
  exercises: Exercise[] = []; // Use the Exercise interface
  isEditing = false;

  constructor(
    private authenticationService: AuthenticationService,
    private facilityService: FacilityService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    console.log('Fetching user profile');
    this.fetchUserProfile();
    this.fetchUserExercises();
  }

  fetchUserProfile(): void {
    this.authenticationService.fetchUserProfile().subscribe({
      next: (data: any) => {
        console.log('Received user profile:', data);
        this.userProfile = {
          ...data,
          birthday: this.formatDate(data.birthday),
          createdAt: this.formatDate(data.createdAt)
        };
      },
      error: (error) => {
        console.error('Error fetching user profile:', error);
      }
    });
  }

  fetchUserExercises(): void {
    this.authenticationService.fetchUserExercises().subscribe({
      next: (data: any) => {
        console.log('Received exercises:', data);

        // Create an array of observables for facility name fetching
        const facilityObservables: Observable<{ facilityId: number; facilityName: string; startTime: Date; endTime: Date; status: string }>[] = data.map((exercise: any) => 
          this.facilityService.fetchFacilityById(exercise.facilityId).pipe(
            // Combine facility data with exercise data
            map((facility: any) => ({
              facilityId: exercise.facilityId,
              startTime: new Date(
                exercise.fromDate[0],
                exercise.fromDate[1] - 1, // Months are 0-indexed
                exercise.fromDate[2],
                exercise.fromDate[3],
                exercise.fromDate[4]
              ),
              endTime: new Date(
                exercise.untilDate[0],
                exercise.untilDate[1] - 1, // Months are 0-indexed
                exercise.untilDate[2],
                exercise.untilDate[3],
                exercise.untilDate[4]
              ),
              facilityName: facility.facilityName, // Get facility name
              status: this.getExerciseStatus({
                startTime: new Date(
                  exercise.fromDate[0],
                  exercise.fromDate[1] - 1,
                  exercise.fromDate[2],
                  exercise.fromDate[3],
                  exercise.fromDate[4]
                ),
                endTime: new Date(
                  exercise.untilDate[0],
                  exercise.untilDate[1] - 1,
                  exercise.untilDate[2],
                  exercise.untilDate[3],
                  exercise.untilDate[4]
                )
              })
            }))
          )
        );

        // Use forkJoin to wait for all observables to complete
        forkJoin(facilityObservables).subscribe((mappedExercises: Exercise[]) => {
          this.exercises = mappedExercises;
          console.log('Mapped exercises:', this.exercises);
        });
      },
      error: (error) => {
        console.error('Error fetching exercises:', error);
      }
    });
  }
  
  getExerciseStatus(exercise: any): string {
    const now = new Date();
    if (exercise.endTime && new Date(exercise.endTime) < now) {
      return 'ended';
    } else if (exercise.startTime && new Date(exercise.startTime) > now) {
      return 'pending';
    } else {
      return 'in progress';
    }
  }

  formatDate(date: string | null): string | null {
    return date ? this.datePipe.transform(new Date(date), 'yyyy-MM-dd') : null;
  }

  toggleEdit(): void {
    this.isEditing = !this.isEditing;
    if (!this.isEditing) {
      this.fetchUserProfile();
    }
  }

  submitProfile(): void {
    if (this.isEditing) {
      this.authenticationService.modifyUserProfile(this.userProfile).subscribe({
        next: () => {
          console.log('Profile updated successfully');
          this.isEditing = false;
        },
        error: (error) => {
          console.error('Error updating profile:', error);
        }
      });
    }
  }
}
