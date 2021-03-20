package model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String name;
    private Adress adress;
    private int age;
    private List<Animal> animals;
}
