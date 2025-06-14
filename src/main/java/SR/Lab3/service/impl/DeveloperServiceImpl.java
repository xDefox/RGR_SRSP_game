package SR.Lab3.service.impl;

import java.util.List;

import SR.Lab3.entity.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SR.Lab3.repository.DeveloperRepository;
import SR.Lab3.service.DeveloperService;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository repository;

    @Override
    public Developer read(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public List<Developer> read() {
        return repository.findAll();
    }

    @Override
    public void save(Developer entity) {
        if (repository.existsByDeveloperName(entity.getDeveloperName())) {
            throw new IllegalArgumentException("Developer with name '" + entity.getDeveloperName() + "' already exists");
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Developer readByName(String name) {
        return repository.findByDeveloperName(name);
    }

    @Override
    public void edit(Developer entity) {
        Developer developer = repository.findById(entity.getId()).orElseThrow(IllegalArgumentException::new);
        developer.setDeveloperName(entity.getDeveloperName());
        repository.save(developer);
    }
}

