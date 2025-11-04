package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.entity.AccountRequest;
import fitpass.fitpass.model.entity.User;
import fitpass.fitpass.model.entity.enums.RequestStatus;
import fitpass.fitpass.repository.AccountRequestRepository;
import fitpass.fitpass.service.AccountRequestService;
import fitpass.fitpass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountRequestServiceImpl implements AccountRequestService {
    @Autowired
    private AccountRequestRepository arRepo;

    @Autowired
    private UserService uService;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Override
    public List<AccountRequest> findAll() {
        return arRepo.findAll();
    }

    @Override
    public AccountRequest findById(Long id) {
        return arRepo.findById(id).orElse(null);
    }

    @Override
    public AccountRequest createAccountRequest(AccountRequest req) {
        User user = uService.findByEmail(req.getEmail());
        if (user != null) {
            return null;
        }

        Optional<AccountRequest> pendingReq = arRepo.findByEmailAndStatus(req.getEmail(), RequestStatus.PENDING);
        if (pendingReq.isPresent()) {
            return null;
        }

        req.setEmail(req.getEmail());
        req.setPassword(pwdEncoder.encode(req.getPassword()));
        req.setAddress(req.getAddress());
        req.setCreatedAt(LocalDate.now());
        req.setStatus(RequestStatus.PENDING);

        return arRepo.save(req);
    }

    @Override
    public void acceptRequest(Long id) {
        AccountRequest req = arRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found: " + id));

        req.setStatus(RequestStatus.ACCEPTED);
        arRepo.save(req);
    }

    @Override
    public void rejectRequest(Long id) {
        AccountRequest req = arRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found: " + id));

        req.setStatus(RequestStatus.REJECTED);
        arRepo.save(req);
    }
}
