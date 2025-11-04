package fitpass.fitpass.repository;

import fitpass.fitpass.model.entity.AccountRequest;
import fitpass.fitpass.model.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRequestRepository extends JpaRepository<AccountRequest, Long> {
    @Query(value = "SELECT * FROM account_requests WHERE status = 'PENDING'", nativeQuery = true)
    List<AccountRequest> findAll();
    Optional<AccountRequest> findByEmailAndStatus(String email, RequestStatus status);
}
