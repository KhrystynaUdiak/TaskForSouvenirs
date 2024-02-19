package model;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Producer implements Serializable {
    long id;
    String name;
    String country;
 //   Set<Souvenir> souvenirs;
}
