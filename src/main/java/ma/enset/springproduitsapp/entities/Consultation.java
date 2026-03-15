package ma.enset.springproduitsapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateConsultation;

    @Column(length = 1000)
    private String rapport;

    @OneToOne
    @JoinColumn(name = "rdv_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RendezVous rendezVous;
}
