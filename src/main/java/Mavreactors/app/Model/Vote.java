package Mavreactors.app.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@ToString
@Table(name = "vote")
public class Vote {
    @Id
    @Column(name = "VOTE_ID")
    @NonNull
    private UUID voteId;

    @JoinColumn(name = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false, targetEntity = User.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @Column(name = "email")
    @NonNull
    private String userId;

    @JoinColumn(name = "outfit_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, targetEntity = Outfit.class, fetch = FetchType.EAGER)
    private Outfit outfit;

    @Column(name = "outfit_id")
    @NonNull
    private UUID outfitId;
}
