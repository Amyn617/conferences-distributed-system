package ma.enset.keynoteservice.mappers;

import ma.enset.keynoteservice.dto.KeynoteDTO;
import ma.enset.keynoteservice.entities.Keynote;
import org.springframework.stereotype.Component;

@Component
public class KeynoteMapper {
    public KeynoteDTO toDto(Keynote e) {
        if (e == null) return null;
        return KeynoteDTO.builder()
                .id(e.getId())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .email(e.getEmail())
                .fonction(e.getFonction())
                .build();
    }

    public Keynote toEntity(KeynoteDTO d) {
        if (d == null) return null;
        return Keynote.builder()
                .id(d.getId())
                .nom(d.getNom())
                .prenom(d.getPrenom())
                .email(d.getEmail())
                .fonction(d.getFonction())
                .build();
    }

    // Partial update: only non-null fields are copied
    public void updateEntityFromDto(KeynoteDTO d, Keynote e) {
        if (d == null || e == null) return;
        if (d.getNom() != null) e.setNom(d.getNom());
        if (d.getPrenom() != null) e.setPrenom(d.getPrenom());
        if (d.getEmail() != null) e.setEmail(d.getEmail());
        if (d.getFonction() != null) e.setFonction(d.getFonction());
    }
}
