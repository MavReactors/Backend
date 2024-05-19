package Mavreactors.app.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@ToString
@Table(name = "confirmationToken")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private Long tokenId;

    @Column(name="confirmation_token")
    @NonNull
    private String confirmationToken;

    @Column(name="email")
    @NonNull
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
