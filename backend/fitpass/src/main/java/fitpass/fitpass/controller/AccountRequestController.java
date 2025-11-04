package fitpass.fitpass.controller;

import fitpass.fitpass.model.dto.UserDTO;
import fitpass.fitpass.model.entity.AccountRequest;
import fitpass.fitpass.service.AccountRequestService;
import fitpass.fitpass.service.EmailService;
import fitpass.fitpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accountRequests")
public class AccountRequestController {
    @Autowired
    private UserService userService;

    @Autowired
    private AccountRequestService accountRequestService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/create")
    public ResponseEntity<AccountRequest> createAccountRequest(@RequestBody AccountRequest accountRequest) {
        AccountRequest request = accountRequestService.createAccountRequest(accountRequest);
        if (request != null) {
            return ResponseEntity.ok(request);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AccountRequest> loadAll() {
        return this.accountRequestService.findAll();
    }

    @PostMapping("/{id}/accept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long id) {
        accountRequestService.acceptRequest(id);
        AccountRequest accRequest = accountRequestService.findById(id);
        if (accRequest != null) {
            UserDTO newUserDTO = new UserDTO();
            newUserDTO.setEmail(accRequest.getEmail());
            newUserDTO.setPassword(accRequest.getPassword());
            newUserDTO.setAddress(accRequest.getAddress());
            userService.createUser(newUserDTO);
            String to = accRequest.getEmail();
            String subject = "Your Account Request has been Accepted";
            String content = "Now you can login with your credentials.<br> Sad je dozvoljeno da se prijavite sa kredencijalima.";
            emailService.sendEmail(to, subject, content);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long id) {
        accountRequestService.rejectRequest(id);
        AccountRequest accRequest = accountRequestService.findById(id);
        if (accRequest != null) {
            String to = accRequest.getEmail();
            String subject = "Your Account Request has been Rejected";
            String content = "Send request again. <br> You failed. <br> Rejected request.";
            emailService.sendEmail(to, subject, content);
        }
        return ResponseEntity.ok().build();
    }
}
