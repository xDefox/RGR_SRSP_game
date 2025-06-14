package SR.Lab3.service;

import SR.Lab3.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> readAll();
    Category read(long id);
    Category save(Category category);
    void delete(long id);
    Category readByName(String name);
    Category update(Category category);
    List<Category> searchByDescription(String keyword);
}