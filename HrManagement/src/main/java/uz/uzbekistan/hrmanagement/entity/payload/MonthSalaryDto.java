package uz.uzbekistan.hrmanagement.entity.payload;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public class MonthSalaryDto {

    @NotNull
    private UUID ownerId;
    @NotNull
    private Double amount;
    @NotNull
    private Long MonthId;
}
