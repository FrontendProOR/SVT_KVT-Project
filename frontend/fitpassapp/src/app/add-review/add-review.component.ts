import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReviewService } from '../services/review.service';
import { AuthenticationService } from '../services/auth.service';
import { RateService } from '../services/rate.service';
import { CommentService } from '../services/comment.service';
import { Rate } from '../models/Rate';
import { Comment } from '../models/Comment';
import { Review } from '../models/Review';

@Component({
  selector: 'app-review-add',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.scss'],
  providers: [ReviewService, AuthenticationService, RateService, CommentService]
})

export class AddReviewComponent {
  reviewForm: FormGroup;
  facilityId!: number;
  currentUser!: any;
  exerciseCount: number = 0;

  constructor(
    private fb: FormBuilder,
    private reviewService: ReviewService,
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private rateService: RateService,
    private commentService: CommentService,
    private router: Router
  ) {
    // Define the form with validators
    this.reviewForm = this.fb.group({
      rateEquipment: [null, [Validators.required, Validators.min(1), Validators.max(10)]],
      rateHygiene: [null, [Validators.required, Validators.min(1), Validators.max(10)]],
      rateSpace: [null, [Validators.required, Validators.min(1), Validators.max(10)]],
      rateStaff: [null, [Validators.required, Validators.min(1), Validators.max(10)]],
      commentText: ['']
    });
  }

  ngOnInit(): void {
    // Extract facilityId from the URL
    this.facilityId = Number(this.route.snapshot.paramMap.get('id'));
  }

  onSubmit() {
    if (this.reviewForm.valid) {
      const value = this.reviewForm.value;

      // Create a new Rate object with the form values
      const rate: Rate = new Rate(
        value.rateEquipment,
        value.rateStaff,
        value.rateHygiene,
        value.rateSpace
      );

      // Fetch the current user profile from the authentication service
      this.authService.fetchUserProfile().subscribe((resp) => {
        this.currentUser = resp;

        // Submit the rate to the rate service with the facilityId
        this.rateService.submitRate(rate, this.facilityId).subscribe((resp) => {
          const submittedRate = resp;

          // If a comment is provided, submit the comment
          if (value.commentText !== '') {
            const comment: Comment = new Comment(value.commentText, this.currentUser);

            this.commentService.submitComment(comment).subscribe((resp) => {
              const submittedComment = resp;

              if (this.facilityId !== undefined) {
                // Create a new review with the submitted rate and comment
                const review: Review = new Review(
                  this.exerciseCount,
                  false,
                  this.currentUser,
                  submittedRate,
                  this.facilityId,
                  submittedComment
                );

                // Submit the review to the review service
                this.reviewService.submitReview(review).subscribe(() => {
                  console.log('Successfully created review!');
                  // Navigate to the facility page
                  this.router.navigate(['facility/', this.facilityId]);
                });
              }
            });
          } else {
            // If no comment, submit only the review
            if (this.facilityId !== undefined) {
              const review: Review = new Review(
                this.exerciseCount,
                false,
                this.currentUser,
                submittedRate,
                this.facilityId
              );

              this.reviewService.submitReview(review).subscribe(() => {
                console.log('Successfully created review!');
                this.router.navigate(['facility/', this.facilityId]);
              });
            }
          }
        });
      });
    }
  }
}
