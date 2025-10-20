package ma.enset.conferenceservice.entities;

import lombok.*;
import jakarta.persistence.*;
import ma.enset.keynoteservice.entities.Keynote;

import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Conference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String type; // académique ou commerciale
    private Date date;
    private double durée;
    private Integer nombreInscrits;
    private Double score;

    private Long keynoteId; // Clé étrangère vers le Keynote Service

    @Transient // Indique à JPA d'ignorer ce champ dans la persistance locale [31]
    private Keynote keynote;

    @OneToMany(mappedBy = "conference")
    private Collection<Review> reviews;
}