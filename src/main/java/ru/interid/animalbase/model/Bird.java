package ru.interid.animalbase.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "birds")
public class Bird extends Animal {
    @Column(name = "flight_speed")
    private BigDecimal flightSpeed;

    public Bird() {
    }

    public Bird(Long id, String name, BigDecimal flightSpeed) {
        super(id, name);
        this.flightSpeed = flightSpeed;
    }
}