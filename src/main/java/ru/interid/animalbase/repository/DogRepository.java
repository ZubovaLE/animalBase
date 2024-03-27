package ru.interid.animalbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.interid.animalbase.model.Dog;

public interface DogRepository extends JpaRepository<Dog, Long> {
}