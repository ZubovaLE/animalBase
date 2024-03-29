package ru.interid.animalbase.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
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
        BIRD("Птица"),
        DOG("Собака");

        private final String description;
    }
}