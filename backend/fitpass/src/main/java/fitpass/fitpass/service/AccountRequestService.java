package fitpass.fitpass.service;

import fitpass.fitpass.model.entity.AccountRequest;

import java.util.List;

public interface AccountRequestService {
    void acceptRequest(Long id);

    void rejectRequest(Long id);

    AccountRequest findById(Long id);

    List<AccountRequest> findAll();

    AccountRequest createAccountRequest(AccountRequest accountRequest);
}
