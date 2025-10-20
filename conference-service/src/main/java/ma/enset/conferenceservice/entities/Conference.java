package ma.enset.conferenceservice.entities;

import lombok.*;
import jakarta.persistence.*;
import ma.enset.conferenceservice.model.Keynote;

import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Conference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String type;
    private Date date;
    private double duree;
    private Integer nombreInscrits;
    private Double score;

    private Long keynoteId;

    @Transient
    private Keynote keynote;

    @OneToMany(mappedBy = "conference")
    private Collection<Review> reviews;
}