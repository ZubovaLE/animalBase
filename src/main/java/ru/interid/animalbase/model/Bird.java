package ru.interid.animalbase.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "birds")
@NoArgsConstructor
public class Bird extends Animal {
    @Column(name = "flight_speed")
    private BigDecimal flightSpeed;

    public Bird(Long id, String name, BigDecimal flightSpeed) {
        super(id, name);
        this.flightSpeed = flightSpeed;
    }
}