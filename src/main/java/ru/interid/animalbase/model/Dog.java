package ru.interid.animalbase.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dogs")
public class Dog extends Animal {

    @Column(name = "run_speed")
    private String runSpeed;
}
