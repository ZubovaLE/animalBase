package ru.interid.animalbase.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

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

        public static AnimalType getTypeByDescription(String desc) {
            return Arrays.stream(values())
                    .filter(t -> t.description.equals(desc))
                    .findFirst()
                    .orElse(null);
        }
    }
}