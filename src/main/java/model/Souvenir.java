package model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class Souvenir implements Serializable {
    long id;
    String name;
    Producer producer;
    int date;
    double price;


}
