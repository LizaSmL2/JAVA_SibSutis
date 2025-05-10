package com.example.petstore.service;

import com.example.petstore.model.Pet;
import com.example.petstore.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {

    @Mock
    private PetRepository petRepository; // Мок репозитория

    @InjectMocks
    private PetService petService; // Сервис, который тестируем

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализация моков
    }

    @Test
    void testSavePet() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        // Мокируем вызов метода save в репозитории
        when(petRepository.save(pet)).thenReturn(pet);

        // Вызываем метод savePet в сервисе
        Pet savedPet = petService.savePet(pet);

        // Проверяем, что метод save был вызван ровно один раз
        verify(petRepository, times(1)).save(pet);

        // Проверяем, что возвращенный объект не равен null
        assertNotNull(savedPet);

        // Проверяем, что имя сохраненного питомца совпадает с ожидаемым
        assertEquals("Buddy", savedPet.getName());
    }

    @Test
    void testGetPetById() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Max");

        // Мокируем вызов метода findById в репозитории
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // Вызываем метод getPetById в сервисе
        Optional<Pet> foundPet = petService.getPetById(1L);

        // Проверяем, что метод findById был вызван ровно один раз
        verify(petRepository, times(1)).findById(1L);

        // Проверяем, что питомец найден
        assertTrue(foundPet.isPresent());

        // Проверяем, что имя найденного питомца совпадает с ожидаемым
        assertEquals("Max", foundPet.get().getName());
    }

    @Test
    void testGetPetByIdNotFound() {
        // Мокируем вызов метода findById в репозитории (питомец не найден)
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        // Вызываем метод getPetById в сервисе
        Optional<Pet> foundPet = petService.getPetById(1L);

        // Проверяем, что метод findById был вызван ровно один раз
        verify(petRepository, times(1)).findById(1L);

        // Проверяем, что питомец не найден
        assertFalse(foundPet.isPresent());
    }

    @Test
    void testDeletePet() {
        // Мокируем вызов метода deleteById в репозитории
        doNothing().when(petRepository).deleteById(1L);

        // Вызываем метод deletePet в сервисе
        petService.deletePet(1L);

        // Проверяем, что метод deleteById был вызван ровно один раз
        verify(petRepository, times(1)).deleteById(1L);
    }
}