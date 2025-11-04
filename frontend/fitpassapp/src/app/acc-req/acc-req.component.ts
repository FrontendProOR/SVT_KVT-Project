import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../services/auth.service';
import { CommonModule } from '@angular/common';

interface AccountRequest {
  id: number;
  email: string;
  address: string;
  createdAt: Date;
  status: string;
}

@Component({
  selector: 'app-account-requests',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './acc-req.component.html',
  styleUrl: './acc-req.component.scss',
  providers: [AuthenticationService]
})

export class AccountRequestsComponent implements OnInit {
  accountRequests: AccountRequest[] = [];

  constructor(private authenticationService: AuthenticationService) { }

  acceptRequest(reqId: number): void {
    this.authenticationService.approveAccountRequest(reqId).subscribe({
      next: () => {
        console.log('Successfully accepted request');
        this.accountRequests = this.accountRequests.filter(req => req.id !== reqId);
      },
      error: (err) => {
        console.error('Failed at fetching account requests', err);
      }
    });
  }

  rejectRequest(reqId: number): void {
    this.authenticationService.denyAccountRequest(reqId).subscribe({
      next: () => {
        console.log('Successfully rejected request');
        this.accountRequests = this.accountRequests.filter(req => req.id !== reqId);
      },
      error: (err) => {
        console.error('Failed at fetching account requests', err);
      }
    });
  }

  ngOnInit(): void {
    console.log('Fetch all account requests');
    this.authenticationService.fetchPendingAccountRequests().subscribe({
      next: (requests: AccountRequest[]) => {
        console.log('Received account requests:', requests);
        this.accountRequests = requests;
      },
      error: (err) => {
        console.error('Failed at fetching account requests', err);
      }
    });
  }
  
}