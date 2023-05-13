package uz.uzbekistan.hrmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false, name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false)
    private Timestamp updatedAt;

    private String emailCode;

    @CreatedBy
    @Column(updatable = false)
    private UUID createdBy;

    @LastModifiedBy
    @Column(updatable = false)
    private UUID updatedBy;

    @ManyToMany
    private Set<Role> roles;

    private boolean accountNonExpired=true;
    private boolean accountNonLocked=true;
    private boolean credentialsNonExpired=true;
    private boolean enabled = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
