package ma.enset.conferenceservice.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String texte;
    private Integer note; // 1 à 5

    @ManyToOne
    private Conference conference;
}