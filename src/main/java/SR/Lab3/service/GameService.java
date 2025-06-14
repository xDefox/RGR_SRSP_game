package SR.Lab3.service;

import java.util.List;

import SR.Lab3.entity.Game;
import SR.Lab3.entity.Developer;

public interface GameService extends Service<Game> {

    List<Game> readAllWithDeveloperAndCategoryInfo();

    List<Game> readByDeveloper(Long developerId);

    List<Game> readByDeveloper(Developer developerId);

    List<Game> readByType(String type);

}