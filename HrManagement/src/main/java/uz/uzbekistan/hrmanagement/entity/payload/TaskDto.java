package uz.uzbekistan.hrmanagement.entity.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    @NotBlank
    private String name;
    private String description;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @NotNull
    private UUID ownerId;

    @NotNull
    private Long statusId;

}
