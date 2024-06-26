package ru.interid.animalbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.interid.animalbase.model.Bird;

@Repository
public interface BirdRepository extends JpaRepository<Bird, Long> {
}