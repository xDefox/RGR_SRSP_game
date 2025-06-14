package SR.Lab3.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import SR.Lab3.entity.Developer;
import SR.Lab3.entity.Game;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SR.Lab3.repository.DeveloperRepository;
import SR.Lab3.repository.GameRepository;
import SR.Lab3.service.GameService;

@Service
@Transactional
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final DeveloperRepository developerRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           DeveloperRepository developerRepository) {
        this.gameRepository = gameRepository;
        this.developerRepository = developerRepository;
    }

    @Override
    public Game read(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("GameController not found with id: " + id));
    }

    @Override
    public List<Game> read() {
        return gameRepository.findAll();
    }

    @Override
    public void save(Game game) {
        if (game.getDeveloper() == null || game.getDeveloper().getId() == null) {
            throw new IllegalArgumentException("GameController must be associated with a Developer");
        }

        Developer developer = developerRepository.findById(game.getDeveloper().getId())
                .orElseThrow(() -> new NoSuchElementException("Developer not found with id: " + game.getDeveloper().getId()));

        game.setDeveloper(developer);
        gameRepository.save(game);

        if (developer.getGames() != null) {
            developer.getGames().add(game);
        }
    }

    @Override
    public void delete(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("GameController not found with id: " + id));

        Developer developer = game.getDeveloper();
        if (developer != null && developer.getGames() != null) {
            developer.getGames().remove(game);
            developerRepository.save(developer);
        }

        gameRepository.delete(game);
    }

    @Override
    public List<Game> readByDeveloper(Developer developerId) {
        return gameRepository.findByDeveloper(developerId);
    }

    @Override
    public List<Game> readByType(String type) {
        return gameRepository.findByType(type);
    }

    @Override
    public void edit(Game game) {
        if (game.getId() == null) {
            throw new IllegalArgumentException("GameController id cannot be null for edit operation");
        }

        Game existingGame = gameRepository.findById(game.getId())
                .orElseThrow(() -> new NoSuchElementException("GameController not found with id: " + game.getId()));

        // Обновляем только изменяемые поля
        existingGame.setName(game.getName());
        existingGame.setType(game.getType());
        existingGame.setSize(game.getSize());

        // Если нужно изменить группу
        if (game.getDeveloper() != null && game.getDeveloper().getId() != null) {
            Developer newDeveloper = developerRepository.findById(game.getDeveloper().getId())
                    .orElseThrow(() -> new NoSuchElementException("Developer not found with id: " + game.getDeveloper().getId()));

            // Удаляем из старой группы
            Developer oldDeveloper = existingGame.getDeveloper();
            if (oldDeveloper != null && oldDeveloper.getGames() != null) {
                oldDeveloper.getGames().remove(existingGame);
                developerRepository.save(oldDeveloper);
            }

            // Добавляем в новую группу
            existingGame.setDeveloper(newDeveloper);
            if (newDeveloper.getGames() != null) {
                newDeveloper.getGames().add(existingGame);
            }
        }

        gameRepository.save(existingGame);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Game> readAllWithDeveloperAndCategoryInfo() {
        List<Game> games = gameRepository.findAll();
        games.forEach(s -> {
            Hibernate.initialize(s.getDeveloper());
            Hibernate.initialize(s.getDeveloper().getCategory());
        });
        return games;
    }

    @Override
    public List<Game> readByDeveloper(Long DeveloperId) {
        return List.of();
    }
}