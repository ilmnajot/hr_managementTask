package uz.uzbekistan.hrmanagement.entity.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.uzbekistan.hrmanagement.entity.Role;
import uz.uzbekistan.hrmanagement.entity.User;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
        private UUID id;
        private String firstName;
        private String lastName;
        private String email;
        private Set<Role> roles;
        private Timestamp createdAt;
        private Timestamp updatedAt;


        public UserResponse (User user){
            this.id = user.getId();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.email = user.getEmail();
            this.roles = user.getRoles();
            this.createdAt = user.getCreatedAt();
            this.updatedAt = user.getUpdatedAt();
        }

}
