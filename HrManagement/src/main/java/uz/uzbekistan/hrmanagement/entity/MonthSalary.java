package uz.uzbekistan.hrmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AutoCloseable.class)
public class MonthSalary {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Double amount;
    @ManyToOne
    private Month month;

    @ManyToOne
    private User owner;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private UUID createdBy;
    @LastModifiedBy
    private UUID updatedBy;

    public MonthSalary(Double amount, Month month, User owner){
        this.amount = amount;
        this.month = month;
        this.owner = owner;
    }

}
