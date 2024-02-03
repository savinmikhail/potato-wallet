package ru.cft.template.core.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;

    @Size(min = 2, max = 50, message = "Name should contain between 5 and 50 characters")
    @NotBlank(message = "Name cannot be empty")
    private String firstName;

    @Size(min = 2, max = 50, message = "Surname should contain between 5 and 50 characters")
    @NotBlank(message = "Surname cannot be empty")
    private String lastName;

    @Column(unique = true, nullable = false, length = 30)
    @Size(min = 5, max = 50, message = "Email should contain between 5 and 30 characters")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
//    @UniqueElements
    private String email;

    @NotNull(message = "Phone number cannot be null")
    private Long phone;

    @Column(nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date registrationDate = new Date();

    @Column(nullable = false, columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date lastUpdateDate = new Date();

    @Column(nullable = false)
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 100, message = "Age must not exceed 100")
    @NotNull
    private int age;

    @Size(min = 5, max = 50, message = "Password should contain between 5 and 50 characters")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    public static UserBuilder builder() {
        return new UserBuilder();
    }
}
