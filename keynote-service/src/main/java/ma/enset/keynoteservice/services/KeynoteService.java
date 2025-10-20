package ma.enset.keynoteservice.services;

import ma.enset.keynoteservice.dto.KeynoteDTO;

import java.util.List;

public interface KeynoteService {
    KeynoteDTO create(KeynoteDTO dto);
    KeynoteDTO get(Long id);
    List<KeynoteDTO> list();
    KeynoteDTO update(Long id, KeynoteDTO dto);
    void delete(Long id);
    boolean emailExists(String email);
}
