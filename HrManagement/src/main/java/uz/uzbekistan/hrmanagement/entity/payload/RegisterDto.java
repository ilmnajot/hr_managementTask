package uz.uzbekistan.hrmanagement.entity.payload;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uzbekistan.hrmanagement.entity.Role;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class RegisterDto {

    @NotNull(message = "please enter your name here, it cannot be blank")
    private String firstName;
    @NotNull(message = "please enter your lastname here, it cannot be blank")
    private String lastName;
    @Email
    @NotNull(message = "please enter your gmail here, it cannot be blank")
    private String email;
    @NotNull(message="please enter your password, it cannot be blank")
    private String password;
    private Set<Role> roles;
}
