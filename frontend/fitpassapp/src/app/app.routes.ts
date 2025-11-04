import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomepageComponent } from './homepage/homepage.component';
import { ProfileComponent } from './profile/profile.component';
import { NewPasswordComponent } from './new-password/new-password.component';
import { AccountRequestsComponent } from './acc-req/acc-req.component';
import { AddExerciseComponent } from './add-exercise/add-exercise.component';
import { AddFacilityComponent } from './add-facility/add-facility.component';
import { AddReviewComponent } from './add-review/add-review.component';
import { EditFacilityComponent } from './edit-facility/edit-facility.component';
import { FacilityDetailsComponent } from './facility-details/facility-details.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'homepage', component: HomepageComponent },
    { path: 'profile', component: ProfileComponent },
    { path: 'exercises/user-exercises', component: ProfileComponent },
    { path: 'profile/new-password', component: NewPasswordComponent },
    { path: 'acc-req', component: AccountRequestsComponent },
    { path: 'facility/:id/exercise-add', component: AddExerciseComponent },
    { path: 'facility-add', component: AddFacilityComponent },
    { path: 'facility/:id/review', component: AddReviewComponent },
    { path: 'facility/:id/edit', component: EditFacilityComponent },
    { path: 'facility/:id', component: FacilityDetailsComponent },
];
