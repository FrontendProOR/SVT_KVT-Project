package fitpass.fitpass.model.dto;

import fitpass.fitpass.model.entity.enums.RequestStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountRequestDTO {
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
