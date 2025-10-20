package ma.enset.conferenceservice.clients;

import ma.enset.conferenceservice.model.Keynote;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "KEYNOTE-SERVICE", path = "/api/keynotes")
public interface KeynoteClient {
    @GetMapping("/{id}")
    Keynote getKeynote(@PathVariable("id") Long id);
}
