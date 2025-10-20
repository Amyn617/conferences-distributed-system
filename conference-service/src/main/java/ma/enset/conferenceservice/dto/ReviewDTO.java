package ma.enset.conferenceservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;

    private Date date;

    @NotBlank
    private String texte;

    @NotNull
    @Min(1) @Max(5)
    private Integer note;

    @NotNull
    private Long conferenceId;
}
