package com.example.petstore.repository;

import com.example.petstore.model.Pet;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PetRepository {
    private final Map<Long, Pet> pets = new HashMap<>();

    public List<Pet> getAllPets() {
        return new ArrayList<>(pets.values());
    }

    public Optional<Pet> getPetById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    public Pet save(Pet pet) {
        pets.put(pet.getId(), pet);
        return pet;
    }

    public void deleteById(Long id) {
        pets.remove(id);
    }
}
