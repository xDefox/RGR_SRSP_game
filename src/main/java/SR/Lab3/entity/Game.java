package SR.Lab3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "games")
public class Game extends AbstractEntity {

    @NotBlank(message = "Game name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Game type is required")
    @Pattern(regexp = "^(RPG|FPS|Strategy|Adventure|Simulation)$",
            message = "Invalid game type. Allowed: RPG, FPS, Strategy, Adventure, Simulation, Tower defence")
    @Column(name = "type", nullable = false)
    private String type;

    @Pattern(regexp = "^\\d+\\s?(GB|MB)$", message = "Size must be like '10 GB' or '500MB'")
    @Column(name = "size")
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id")
    @JsonBackReference("developer-games")
    private Developer developer;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    @Override
    public String toString() {
        return "Game - " + (id != null ? id : "null_id") + ": [name=" + name + ", type=" + type + ", size=" + size +
                ", developerID=" + (developer != null && developer.getId() != null ? developer.getId() : "null") + "]"; // Добавил groupId в toString
    }
}
