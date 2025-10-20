package ma.enset.conferenceservice.mappers;

import ma.enset.conferenceservice.dto.ConferenceDTO;
import ma.enset.conferenceservice.entities.Conference;
import org.springframework.stereotype.Component;

@Component
public class ConferenceMapper {
    public ConferenceDTO toDto(Conference e) {
        if (e == null) return null;
        return ConferenceDTO.builder()
                .id(e.getId())
                .titre(e.getTitre())
                .type(e.getType())
                .date(e.getDate())
                .duree(e.getDuree())
                .nombreInscrits(e.getNombreInscrits())
                .score(e.getScore())
                .keynoteId(e.getKeynoteId())
                // keynote filled by service if needed
                .build();
    }

    public Conference toEntity(ConferenceDTO d) {
        if (d == null) return null;
        return Conference.builder()
                .id(d.getId())
                .titre(d.getTitre())
                .type(d.getType())
                .date(d.getDate())
                .duree(d.getDuree())
                .nombreInscrits(d.getNombreInscrits())
                .score(d.getScore())
                .keynoteId(d.getKeynoteId())
                .build();
    }

    public void updateEntityFromDto(ConferenceDTO d, Conference e) {
        if (d == null || e == null) return;
        if (d.getTitre() != null) e.setTitre(d.getTitre());
        if (d.getType() != null) e.setType(d.getType());
        if (d.getDate() != null) e.setDate(d.getDate());
        if (d.getDuree() > 0) e.setDuree(d.getDuree());
        if (d.getNombreInscrits() != null) e.setNombreInscrits(d.getNombreInscrits());
        if (d.getScore() != null) e.setScore(d.getScore());
        if (d.getKeynoteId() != null) e.setKeynoteId(d.getKeynoteId());
    }
}
