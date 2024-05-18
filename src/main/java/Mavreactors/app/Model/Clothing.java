package Mavreactors.app.Model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "clothing")
public class Clothing {

    @Id
    @Column(name = "clothing_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clothingId;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    @NonNull
    private String photo;

    @Column(name = "is_ironed ")
    @NonNull
    private Boolean isIroned;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NonNull
    private Type type;

    @Column(name = "last_wear")
    @NonNull
    private Date lastWear;

    @JoinColumn(name = "email", insertable = false, updatable = false)
    @ManyToOne(optional = false, targetEntity = User.class, fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;

    @Column(name = "email")
    @NonNull
    private String userId;
}