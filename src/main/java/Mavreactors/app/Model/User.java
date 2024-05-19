package Mavreactors.app.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "user")
public class User {

    @Id
    @Column(name = "email", nullable=false, unique=true)
    private String email;

    @Column(name = "password", nullable=false)
    private String password;

    @Column(name = "userName", nullable=false, unique=true)
    private String userName;

    @Column(name = "profilePhoto", nullable=false)
    private String profilePhoto;

    @Column(name = "roles", nullable=false)
    private List<UserRole> userRoles;

    private boolean isEnabled;
}
