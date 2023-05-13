package uz.uzbekistan.hrmanagement.entity.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LoginDto {

    @Email(message = "email is not correct, please double check")
    @NotNull(message = "email cannot be blank, please enter your email or double check")
    private String email;
    @NotNull(message = "password cannot be blank, please enter your password or double check")
    private String password;
}

