package ma.enset.conferenceservice.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.enset.conferenceservice.clients.KeynoteClient;
import ma.enset.conferenceservice.dto.ConferenceDTO;
import ma.enset.conferenceservice.dto.ReviewDTO;
import ma.enset.conferenceservice.entities.Conference;
import ma.enset.conferenceservice.entities.Review;
import ma.enset.conferenceservice.mappers.ConferenceMapper;
import ma.enset.conferenceservice.mappers.ReviewMapper;
import ma.enset.conferenceservice.model.Keynote;
import ma.enset.conferenceservice.repositories.ConferenceRepository;
import ma.enset.conferenceservice.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final ReviewRepository reviewRepository;
    private final ConferenceMapper conferenceMapper;
    private final ReviewMapper reviewMapper;
    private final KeynoteClient keynoteClient;

    @Override
    public ConferenceDTO create(ConferenceDTO dto) {
        validateKeynote(dto.getKeynoteId());
        Conference saved = conferenceRepository.save(conferenceMapper.toEntity(dto));
        return conferenceMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ConferenceDTO get(Long id, boolean enrichKeynote) {
        Conference conf = conferenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conférence introuvable"));
        ConferenceDTO dto = conferenceMapper.toDto(conf);
        if (enrichKeynote) {
            dto.setKeynote(fetchKeynoteSafe(conf.getKeynoteId()));
        }
        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDTO> list(boolean enrichKeynote) {
        List<ConferenceDTO> list = conferenceRepository.findAll().stream()
                .map(conferenceMapper::toDto)
                .toList();
        if (!enrichKeynote) return list;
        return list.stream().peek(d -> d.setKeynote(fetchKeynoteSafe(d.getKeynoteId()))).toList();
    }

    @Override
    public ConferenceDTO update(Long id, ConferenceDTO dto) {
        Conference conf = conferenceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Conférence introuvable"));
        if (dto.getKeynoteId() != null) validateKeynote(dto.getKeynoteId());
        conferenceMapper.updateEntityFromDto(dto, conf);
        return conferenceMapper.toDto(conferenceRepository.save(conf));
    }

    @Override
    public void delete(Long id) {
        if (!conferenceRepository.existsById(id)) {
            throw new EntityNotFoundException("Conférence introuvable");
        }
        conferenceRepository.deleteById(id);
    }

    @Override
    public ReviewDTO addReview(Long conferenceId, ReviewDTO dto) {
        Conference conf = conferenceRepository.findById(conferenceId)
                .orElseThrow(() -> new EntityNotFoundException("Conférence introuvable"));
        if (dto.getDate() == null) dto.setDate(new Date());
        Review saved = reviewRepository.save(reviewMapper.toEntity(dto, conf));
        // Recalculate average score
        List<Review> reviews = reviewRepository.findByConferenceId(conferenceId);
        double avg = reviews.stream()
                .filter(r -> r.getNote() != null)
                .mapToInt(Review::getNote)
                .average().orElse(0.0);
        conf.setScore(avg);
        conferenceRepository.save(conf);
        return reviewMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> listReviews(Long conferenceId) {
        if (!conferenceRepository.existsById(conferenceId)) {
            throw new EntityNotFoundException("Conférence introuvable");
        }
        return reviewRepository.findByConferenceId(conferenceId).stream()
                .map(reviewMapper::toDto)
                .toList();
    }

    private void validateKeynote(Long keynoteId) {
        if (keynoteId == null) throw new IllegalArgumentException("keynoteId est obligatoire");
        // Throws if not found
        fetchKeynoteSafe(keynoteId);
    }

    private Keynote fetchKeynoteSafe(Long keynoteId) {
        try {
            return keynoteClient.getKeynote(keynoteId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Keynote introuvable pour id=" + keynoteId);
        }
    }
}
