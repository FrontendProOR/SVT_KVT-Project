package fitpass.fitpass.model.entity;

import fitpass.fitpass.model.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String surname;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private LocalDate birthday;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String city;

    @Column(nullable = true)
    private String zipCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

//    @Column(name = "is_deleted",nullable = false)
//    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.MERGE)
    private Set<Manages> manages = new HashSet<Manages>();

    @OneToMany(mappedBy = "wentBy" , cascade = CascadeType.MERGE)
    private Set<Exercise> exercises = new HashSet<Exercise>();

    @OneToMany(mappedBy = "madeBy" , cascade = CascadeType.MERGE)
    private Set<Review> reviews = new HashSet<Review>();

    public String getRole() {
        DiscriminatorValue discriminator = this.getClass().getAnnotation(DiscriminatorValue.class);
        return discriminator != null ? discriminator.value() : "UNKNOWN";
    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.phoneNumber = userDTO.getPhoneNumber();
        this.birthday = userDTO.getBirthday();
        this.address = userDTO.getAddress();
        this.city = userDTO.getCity();
        this.zipCode = userDTO.getZipCode();
//        this.isDeleted = userDTO.getIsDeleted();
    }
}
