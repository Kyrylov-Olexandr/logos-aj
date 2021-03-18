package model;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Adress {
    String counry;
    String city;
    String street;
    String house;
    String apart;
}
