package SR.Lab3.repository;

import SR.Lab3.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Developer findByDeveloperName(String name);
    boolean existsByDeveloperName(String name);
}
