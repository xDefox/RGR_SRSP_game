package SR.Lab3.service.impl;

import SR.Lab3.entity.Category;
import SR.Lab3.repository.CategoryRepository;
import SR.Lab3.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> readAll() {
        return repository.findAll();
    }

    @Override
    public Category read(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
    }

    @Override
    public Category save(Category category) {
        validateCategory(category);
        return repository.save(category);
    }

    @Override
    public void delete(long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
        repository.delete(category);
    }

    @Override
    public Category readByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with name: " + name));
    }

    @Override
    public Category update(Category category) {
        Category existing = repository.findById(category.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (!existing.getName().equals(category.getName()) &&
                repository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category name must be unique");
        }

        // Обновляем все поля
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        existing.setIconUrl(category.getIconUrl());

        return repository.save(existing);
    }

    @Override
    public List<Category> searchByDescription(String keyword) {
        return repository.findByDescriptionContainingIgnoreCase(keyword);
    }

    private void validateCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        if (repository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category with name '" + category.getName() + "' already exists");
        }
    }
}