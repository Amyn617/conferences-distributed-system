package ma.enset.keynoteservice.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import ma.enset.keynoteservice.dto.KeynoteDTO;
import ma.enset.keynoteservice.entities.Keynote;
import ma.enset.keynoteservice.mappers.KeynoteMapper;
import ma.enset.keynoteservice.repositories.KeynoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KeynoteServiceImpl implements KeynoteService {
    private final KeynoteRepository repository;
    private final KeynoteMapper mapper;

    @Override
    public KeynoteDTO create(KeynoteDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
        Keynote saved = repository.save(mapper.toEntity(dto));
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public KeynoteDTO get(Long id) {
        Keynote e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Keynote introuvable"));
        return mapper.toDto(e);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KeynoteDTO> list() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public KeynoteDTO update(Long id, KeynoteDTO dto) {
        Keynote e = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Keynote introuvable"));

        if (dto.getEmail() != null) {
            repository.findByEmail(dto.getEmail())
                    .filter(k -> !k.getId().equals(id))
                    .ifPresent(k -> { throw new IllegalArgumentException("Email déjà utilisé"); });
        }

        mapper.updateEntityFromDto(dto, e);
        return mapper.toDto(repository.save(e));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Keynote introuvable");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }
}
