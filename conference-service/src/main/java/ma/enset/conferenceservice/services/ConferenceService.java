package ma.enset.conferenceservice.services;

import ma.enset.conferenceservice.dto.ConferenceDTO;
import ma.enset.conferenceservice.dto.ReviewDTO;

import java.util.List;

public interface ConferenceService {
    ConferenceDTO create(ConferenceDTO dto);
    ConferenceDTO get(Long id, boolean enrichKeynote);
    List<ConferenceDTO> list(boolean enrichKeynote);
    ConferenceDTO update(Long id, ConferenceDTO dto);
    void delete(Long id);

    ReviewDTO addReview(Long conferenceId, ReviewDTO dto);
    List<ReviewDTO> listReviews(Long conferenceId);
}
