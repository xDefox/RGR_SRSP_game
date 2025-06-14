package SR.Lab3.service;

import SR.Lab3.entity.Developer;


public interface DeveloperService extends Service<Developer> {

    Developer readByName(String name);

}
