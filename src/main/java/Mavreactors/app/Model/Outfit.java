package Mavreactors.app.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "outfit")
public class Outfit {
    @Id
    @Column(name = "outfit_id")
    private UUID outfitId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "outfit_prendas",
            joinColumns = @JoinColumn(name = "OUTFIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRENDA_ID")
    )
    private List<Prendas> prendas;

    @Column(name = "is_public")
    private Boolean isPublic;
}
