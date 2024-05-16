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
@Table(name = "Outfit")
public class Outfit {
    @Id
    @Column(name = "OUTFIT_ID")
    private UUID outfitId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_EMAIL", referencedColumnName = "email", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "OUTFIT_PRENDAS",
            joinColumns = @JoinColumn(name = "OUTFIT_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRENDA_ID")
    )
    private List<Prendas> prendas;

    @Column(name = "IS_PUBLIC")
    private Boolean isPublic;
}
