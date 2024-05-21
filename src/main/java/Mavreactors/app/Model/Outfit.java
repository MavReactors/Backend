package Mavreactors.app.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "outfit")
public class Outfit {
    @Id
    @Column(name = "outfit_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID outfitId;

    @JoinColumn(name = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false, targetEntity = User.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @Column(name = "email")
    @NonNull
    private String userId;


    @ManyToMany
    @JoinTable(
            name = "outfit_prendas",
            joinColumns = @JoinColumn(name = "outfit_id"),
            inverseJoinColumns = @JoinColumn(name = "clothing_id")
    )
    @JsonIgnore
    private List<Clothing> clothingList;

    @Column(name = "clothing_id")
    @NonNull
    private List<UUID> clothingIds;

    @Column(name = "is_public")
    @NonNull
    private Boolean isPublic;

    @Column(name = "special_date")
    private List<Date> specialDate;

    @Column(name = "vote_count")
    @NonNull
    private int voteCount;
}
