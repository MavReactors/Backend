package Mavreactors.app.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "vote")
public class Vote {
    @Id
    @Column(name = "VOTE_ID")
    private UUID voteId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_EMAIL", referencedColumnName = "email", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OUTFIT", referencedColumnName = "OUTFIT_ID", nullable = false)
    private Outfit outfit;
}
