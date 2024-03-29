package ru.interid.animalbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.interid.animalbase.model.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}