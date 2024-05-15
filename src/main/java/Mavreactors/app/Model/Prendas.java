package Mavreactors.app.Model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(value = { "email"})
@Table(name = "PRENDAS")
public class Prendas {

    //You need to modify the column with this SQL line:ALTER TABLE employee MODIFY COLUMN employee_id BIGINT AUTO_INCREMENT;
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

    @Column(name = "TIPO")
    private Character tipo;

    @Column(name = "ULTIMO_USO")
    private Date ultimoUso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName = "email")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;
}