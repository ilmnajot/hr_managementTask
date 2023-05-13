package uz.uzbekistan.hrmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uzbekistan.hrmanagement.entity.enums.MonthName;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MonthName monthName;
}
