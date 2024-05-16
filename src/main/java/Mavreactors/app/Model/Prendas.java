package Mavreactors.app.Model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(value = { "email" })
@Table(name = "PRENDAS")
public class Prendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRENDA_ID")
    private Long prendaId;

    @Lob
    @Column(name = "FOTO", columnDefinition = "LONGBLOB")
    private String foto;

    @Column(name = "SE_PLANCHA")
    private Boolean sePlancha;

    @Column(name = "ULTIMO_LAVADO")
    private Date ultimoLavado;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO")
    private Type tipo;

    @Column(name = "ULTIMO_USO")
    private Date ultimoUso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;
}