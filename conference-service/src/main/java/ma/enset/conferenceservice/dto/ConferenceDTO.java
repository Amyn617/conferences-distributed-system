package ma.enset.conferenceservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import ma.enset.conferenceservice.model.Keynote;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceDTO {
    private Long id;

    @NotBlank
    private String titre;

    @NotBlank
    private String type; // academique | commerciale

    @NotNull
    private Date date;

    @Positive
    private double duree;

    @Min(0)
    private Integer nombreInscrits;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double score;

    @NotNull
    private Long keynoteId;

    // Enriched data
    private Keynote keynote;
}
