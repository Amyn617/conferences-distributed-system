package ma.enset.keynoteservice.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.enset.keynoteservice.dto.KeynoteDTO;
import ma.enset.keynoteservice.services.KeynoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/keynotes")
@RequiredArgsConstructor
@Validated
@CrossOrigin
public class KeynoteController {
    private final KeynoteService service;

    @GetMapping
    public List<KeynoteDTO> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public KeynoteDTO get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<KeynoteDTO> create(@Valid @RequestBody KeynoteDTO dto) {
        KeynoteDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/keynotes/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public KeynoteDTO update(@PathVariable Long id, @Valid @RequestBody KeynoteDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists")
    public boolean emailExists(@RequestParam String email) {
        return service.emailExists(email);
    }
}
