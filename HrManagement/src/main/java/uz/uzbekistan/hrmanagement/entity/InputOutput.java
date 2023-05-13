package uz.uzbekistan.hrmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import uz.uzbekistan.hrmanagement.entity.enums.InOutStatus;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InputOutput {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private InOutStatus outStatus;

    @ManyToOne
    private User user;

    @CreatedDate
    private Timestamp createdAt;

    public InputOutput(InOutStatus outStatus, User user){
        this.outStatus = outStatus;
        this.user = user;
    }

}
