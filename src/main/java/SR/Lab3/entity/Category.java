package SR.Lab3.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name can only contain letters, numbers and spaces")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Новые поля
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon_url")
    private String iconUrl;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference("developer-category")
    private Set<Developer> developers;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Геттеры и сеттеры для новых полей
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Set<Developer> getDevelopers() { return developers; }
    public void setDevelopers(Set<Developer> developers) { this.developers = developers; }

    public void addDeveloper(Developer developer) {
        developers.add(developer);
        developer.setCategory(this);
    }

    public void removeDeveloper(Developer developer) {
        developers.remove(developer);
        developer.setCategory(null);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", developersCount=" + (developers != null ? developers.size() : 0) +
                '}';
    }
}