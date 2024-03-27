package ru.interid.animalbase.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public abstract class Animal {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "name")
    private String name;

    @Getter
    @RequiredArgsConstructor
    public enum AnimalType {
        BIRD("птица"),
        DOG("собака");
        private final String desc;
    }
}