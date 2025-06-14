package SR.Lab3.repository;

import java.util.List;

import SR.Lab3.entity.Developer;
import SR.Lab3.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByDeveloper(Developer developer_Name);

    List<Game> findByType(String type);
}
