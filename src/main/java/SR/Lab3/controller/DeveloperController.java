package SR.Lab3.controller;

import SR.Lab3.entity.Developer;
import SR.Lab3.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/developer", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeveloperController extends AbstractController<Developer> {
    @Autowired
    private DeveloperService service;

    @Override
    public DeveloperService getService() {
        return service;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Developer> getStudentsBySurname(@PathVariable String name) {
        Developer developer = service.readByName(name);
        if (developer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(developer, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getById(@PathVariable long id) {
        Developer entity = service.read(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> put(@RequestBody Developer entity) {
        service.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> post(@RequestBody Developer entity) {
        service.save(entity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/top")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public ResponseEntity<List<Developer>> getTopDevelopers() {
        return ResponseEntity.ok(service.read().subList(0, 5));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}