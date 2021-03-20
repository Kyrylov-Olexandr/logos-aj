package impl;

import model.Adress;
import model.Animal;
import model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {
    private UserService userService;
    private User user;
    private Adress adress;
    private Animal animal;
    private String newName;


    @BeforeEach
    void init () {
        userService = new UserServiceImpl();
        user = new User();
        user.setAnimals(new ArrayList<>());
        adress = Adress
                .builder()
                .counry("Ukraine")
                .city("Lviv")
                .street("Naukova")
                .house("74")
                .apart("2")
                .build();
        animal = new Animal("Rick", "Dog", 5);
        newName = "Bob Marley";
    }
    @Test
    public void isAdultTest () {
        user.setAge(15);
        assertFalse(userService.isAdult(user));
    }
    @Test
    public void shouldAddAdressToUser() {
        userService.addAdress(user, adress);
        assertEquals(adress, user.getAdress());
    }
    @Test
    public void shoudDeleteAdressFromUser() {
        userService.addAdress(user, adress);
        userService.deleteAdress(user);
        assertNull(user.getAdress());
    }
    @Test
    public void shouldAddAnimalToUser() {
        userService.addAnimal(user, animal);
        boolean isAdded = user.getAnimals().contains(animal);
        assertTrue(isAdded);
    }
    @Test
    public void shouldDeleteAnimalFromUser() {
        userService.addAnimal(user, animal);
        boolean isDeleted = userService.deleteAnimal(user,animal);
        assertTrue(isDeleted);
    }
    @Test
    public void shouldRenameUser() {
        boolean isRenamed = userService.renameUser(user, newName);
        assertTrue(isRenamed);
    }
}
