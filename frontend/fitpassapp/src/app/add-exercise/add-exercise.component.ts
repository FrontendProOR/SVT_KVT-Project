import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ExerciseService } from '../services/exercise.service';
import { Exercise } from '../models/Exercise';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-exercise',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './add-exercise.component.html',
  styleUrl: './add-exercise.component.scss',
  providers: [ExerciseService, AuthenticationService]
})

export class AddExerciseComponent {
  facilityId: number = 0;
  userId: number = 0;
  timeError: boolean = false;
  dateError: boolean = false;
  taskForm: FormGroup;
  exerciseError = false;

  constructor(
    private route: ActivatedRoute,
    private exerciseService: ExerciseService,
    private authenticationService: AuthenticationService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.taskForm = this.formBuilder.group({
      fromDate: ['', Validators.required],
      fromTime: ['', Validators.required],
      untilDate: ['', Validators.required],
      untilTime: ['', Validators.required],
      id: ['']
    });
  }

  ngOnInit(): void {
    this.facilityId = +(this.route.snapshot.paramMap.get('id') ?? 0);
  }

  onSubmit() {
    let f = this.taskForm.value;
    let fromDateTime: Date = new Date(`${f.fromDate}T${f.fromTime}`);
    let untilDateTime: Date = new Date(`${f.untilDate}T${f.untilTime}`);
  
    this.authenticationService.fetchUserProfile().subscribe({
      next: (resp) => {
        this.userId = resp.id;
        let exercise: Exercise = new Exercise(fromDateTime, untilDateTime, this.facilityId, this.userId);
        this.exerciseService.createExercise(exercise).subscribe({
          next: (response) => {
            console.log("Successfully created exercise!");
            this.router.navigate(['facility/', this.facilityId]);
          },
          error: (err) => {
            console.error("Failed to create exercise:" + err);
            this.exerciseError = true;
          },
          complete: () => { }
        });
      },
      error: (err) => {
        console.error("Failed to get user profile:" + err);
      }
    });
  }

  validateDates(): void {
    const fromDate = this.taskForm.get('fromDate')?.value;
    const untilDate = this.taskForm.get('untilDate')?.value;
    this.dateError = fromDate !== untilDate;
  }

  validateTimes(): void {
    const fromTime = this.taskForm.get('fromTime')?.value;
    const untilTime = this.taskForm.get('untilTime')?.value;
    this.timeError = fromTime >= untilTime;
  }
}
