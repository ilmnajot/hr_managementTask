package uz.uzbekistan.hrmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.format.annotation.DateTimeFormat;
import uz.uzbekistan.hrmanagement.entity.enums.TaskStatus;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AutoCloseable.class)
public class Task {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private boolean acceptedByOwner;
    private boolean completedTime;

    @ManyToOne
    private User owner;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreatedBy
    @Column(updatable = false)
    private UUID createdBy;

    @LastModifiedBy
    private UUID updatedBy;

    public Task(String name, String description, Date endTime, User owner){
        this.name = name;
        this.description = description;
        this.endTime = endTime;
        this.owner = owner;
    }

}


