package impl;

import model.Adress;
import model.Animal;
import model.User;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService{

    public boolean isAdult(User user) {
        return user.getAge() >= 18;
    }

    public void addAdress(User user, Adress adress) {
        user.setAdress(adress);
    }

    public boolean deleteAdress(User user) {
        user.setAdress(null);
        return user.getAdress() == null;
    }

    public boolean addAnimal(User user, Animal animal) {
        user.getAnimals().add(animal);
        return user.getAnimals().contains(animal);
    }

    public boolean deleteAnimal(User user, Animal animal) {
        List<Animal> animals = user.getAnimals();
        animals.remove(animal);
        return !animals.contains(animal);
    }

    public boolean renameUser(User user, String name) {
        user.setName(name);
        return user.getName().equals(name);
    }
}

