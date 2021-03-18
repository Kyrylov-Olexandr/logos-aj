package service;

import model.Adress;
import model.Animal;
import model.User;

public interface UserService {
    boolean isAdult(User user);

    void addAdress(User user, Adress adress);

    boolean deleteAdress(User user);

    boolean addAnimal(User user, Animal animal);

    boolean deleteAnimal(User user, Animal animal);

    boolean renameUser(User user, String name);

}
