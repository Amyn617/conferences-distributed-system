package ma.enset.conferenceservice.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.enset.conferenceservice.dto.ConferenceDTO;
import ma.enset.conferenceservice.dto.ReviewDTO;
import ma.enset.conferenceservice.services.ConferenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/conferences")
@RequiredArgsConstructor
@CrossOrigin
public class ConferenceController {
    private final ConferenceService service;

    @GetMapping
    public List<ConferenceDTO> list(@RequestParam(defaultValue = "true") boolean enrichKeynote) {
        return service.list(enrichKeynote);
    }

    @GetMapping("/{id}")
    public ConferenceDTO get(@PathVariable Long id, @RequestParam(defaultValue = "true") boolean enrichKeynote) {
        return service.get(id, enrichKeynote);
    }

    @PostMapping
    public ResponseEntity<ConferenceDTO> create(@Valid @RequestBody ConferenceDTO dto) {
        ConferenceDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/conferences/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ConferenceDTO update(@PathVariable Long id, @Valid @RequestBody ConferenceDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long id, @Valid @RequestBody ReviewDTO dto) {
        dto.setConferenceId(id);
        ReviewDTO created = service.addReview(id, dto);
        return ResponseEntity.created(URI.create("/api/conferences/" + id + "/reviews/" + created.getId())).body(created);
    }

    @GetMapping("/{id}/reviews")
    public List<ReviewDTO> listReviews(@PathVariable Long id) {
        return service.listReviews(id);
    }
}
