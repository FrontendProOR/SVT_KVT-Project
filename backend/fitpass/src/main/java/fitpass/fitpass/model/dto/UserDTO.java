package fitpass.fitpass.model.dto;

import fitpass.fitpass.model.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    private String city;

    private String name;

    private String surname;

    private String phoneNumber;

    private LocalDate createdAt;

    private String zipCode;

    private LocalDate birthday;

//    private Boolean isDeleted = false;

    public UserDTO(User createdUser) {
        this.id = createdUser.getId();
        this.email = createdUser.getEmail();
        this.password = createdUser.getPassword();
        this.address = createdUser.getAddress();
        this.birthday = createdUser.getBirthday();
        this.city = createdUser.getCity();
        this.createdAt = createdUser.getCreatedAt();
        this.name = createdUser.getName();
        this.phoneNumber = createdUser.getPhoneNumber();
        this.surname = createdUser.getSurname();
        this.zipCode = createdUser.getZipCode();
//        this.isDeleted = Boolean.FALSE;
    }
}