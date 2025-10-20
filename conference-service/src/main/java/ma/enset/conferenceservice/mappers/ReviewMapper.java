package ma.enset.conferenceservice.mappers;

import ma.enset.conferenceservice.dto.ReviewDTO;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.entities.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewDTO toDto(Review e) {
        if (e == null) return null;
        return ReviewDTO.builder()
                .id(e.getId())
                .date(e.getDate())
                .texte(e.getTexte())
                .note(e.getNote())
                .conferenceId(e.getConference() != null ? e.getConference().getId() : null)
                .build();
    }

    public Review toEntity(ReviewDTO d, Conference conf) {
        if (d == null) return null;
        return Review.builder()
                .id(d.getId())
                .date(d.getDate())
                .texte(d.getTexte())
                .note(d.getNote())
                .conference(conf)
                .build();
    }
}
