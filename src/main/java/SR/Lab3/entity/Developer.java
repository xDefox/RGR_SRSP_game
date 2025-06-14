package SR.Lab3.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "developer")
@AttributeOverride(name = "id", column = @Column(name = "`developer_id`"))
public class Developer extends AbstractEntity {

        @NotBlank(message = "Developer name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        @Column(unique = true)
        @JsonProperty("developer_name")
        private String developerName;

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("developer-games")
    private Set<Game> games;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference("developer-category")
    private Category category;

    @Transient
    @JsonProperty(value = "category_id", access = JsonProperty.Access.WRITE_ONLY)
    private Long genreIdInput;


    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Developer - " + (id != null ? id : "null_id") + ": [name=" + developerName + // Проверка на null для id, если он из AbstractEntity
                ", countofGames=" + (games != null ? games.size() : 0) +
                ", category=" + (category != null && category.getId() != null ? category.getId() : "null") + "]"; // Добавил facultyId в toString
    }
}
