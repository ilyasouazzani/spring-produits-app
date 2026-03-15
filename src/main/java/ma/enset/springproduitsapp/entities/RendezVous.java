package ma.enset.springproduitsapp.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "rendez_vous")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendezVous {

    @Id
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date date;

    @Enumerated(EnumType.STRING)
    private StatusRDV statut;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Medecin medecin;

    @OneToOne(mappedBy = "rendezVous", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Consultation consultation;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
        if (this.statut == null) this.statut = StatusRDV.EN_ATTENTE;
    }
}
