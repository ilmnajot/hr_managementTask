package uz.uzbekistan.hrmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Enumerated
    private uz.uzbekistan.hrmanagement.entity.enums.TaskStatus status;
}
