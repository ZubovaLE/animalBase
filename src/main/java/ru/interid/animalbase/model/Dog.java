package ru.interid.animalbase.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dogs")
@NoArgsConstructor
public class Dog extends Animal {
    @Column(name = "run_speed")
    private String runSpeed;

    public Dog(Long id, String name, String runSpeed) {
        super(id, name);
        this.runSpeed = runSpeed;
    }
}