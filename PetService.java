package com.example.petstore.service;

import com.example.petstore.model.Pet;
import com.example.petstore.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> getAllPets() {
        return repository.getAllPets();
    }

    public Optional<Pet> getPetById(Long id) {
        return repository.getPetById(id);
    }

    public Pet addOrUpdatePet(Pet pet) {
        return repository.save(pet);
    }

    public void deletePet(Long id) {
        repository.deleteById(id);
    }
}
