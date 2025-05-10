package com.example.petstore.repository;

import com.example.petstore.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PetRepository {

    private final List<Pet> pets = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1); // Для генерации ID

    public PetRepository() {
        // Инициализация тестовыми данными (опционально)
        Pet pet1 = new Pet();
        pet1.setId(idCounter.getAndIncrement());
        pet1.setName("Buddy");
        pet1.setStatus("available");

        Pet pet2 = new Pet();
        pet2.setId(idCounter.getAndIncrement());
        pet2.setName("Max");
        pet2.setStatus("pending");

        pets.add(pet1);
        pets.add(pet2);
    }

    public Optional<Pet> findById(Long id) {
        return pets.stream()
                .filter(pet -> pet.getId().equals(id))
                .findFirst();
    }

    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            pet.setId(idCounter.getAndIncrement());
        }
        pets.add(pet);
        return pet;
    }

    // Переименованный метод delete в deleteById
    public void deleteById(Long id) {
        pets.removeIf(pet -> pet.getId().equals(id));
    }

    public List<Pet> findAll() {
        return new ArrayList<>(pets);
    }
}
