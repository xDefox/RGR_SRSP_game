package SR.Lab3.controller;

import SR.Lab3.entity.Game;
import SR.Lab3.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("api/game")
public class GameController extends AbstractController<Game> {
    @Autowired
    private GameService service;

    @Override
    public GameService getService() {
        return service;
    }

    @GetMapping("/developer/{id}")
    public ResponseEntity<List<Game>> getGamesByDeveloper(@PathVariable long id) {
        List<Game> games = service.readByDeveloper(id);
        return games.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(games, headers, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Game>> getGamesByType(@PathVariable String type) {
        List<Game> games = service.readByType(type);
        return games.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(games, headers, HttpStatus.OK);
    }

    @GetMapping("/latest")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public ResponseEntity<List<Game>> getLatestGames() {
        List<Game> allGames = service.read();
        int endIndex = Math.min(5, allGames.size());
        return ResponseEntity.ok(allGames.subList(0, endIndex));
    }

    @GetMapping("/by-size/{size}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public ResponseEntity<List<Game>> getGamesBySize(@PathVariable String size) {
        List<Game> games = service.read().stream()
                .filter(g -> g.getSize() != null && g.getSize().equalsIgnoreCase(size))
                .toList();
        return games.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(games);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public ResponseEntity<List<Game>> searchGames(@RequestParam String term) {
        List<Game> results = service.read().stream()
                .filter(g -> g.getName().toLowerCase().contains(term.toLowerCase()))
                .toList();
        return ResponseEntity.ok(results);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> put(@RequestBody Game entity) {
        service.save(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> post(@RequestBody Game game) {
        if (game.getDeveloper() == null || game.getDeveloper().getId() == null) {
            throw new IllegalArgumentException("Developer ID must be specified");
        }
        service.save(game);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}