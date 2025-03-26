package com.example.petstore.controller;

import com.example.petstore.model.Pet;
import com.example.petstore.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PetControllerTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализация моков
    }

    @Test
    void testAddPet() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Buddy");

        // Мокируем вызов метода savePet в сервисе
        when(petService.savePet(pet)).thenReturn(pet);

        // Выполняем POST-запрос через контроллер
        ResponseEntity<Pet> response = petController.addPet(pet);

        // Проверяем статус ответа и тело
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Buddy", response.getBody().getName());

        // Проверяем, что метод savePet был вызван ровно один раз
        verify(petService, times(1)).savePet(pet);
    }

    @Test
    void testGetPetById() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Max");

        // Мокируем вызов метода getPetById в сервисе
        when(petService.getPetById(1L)).thenReturn(Optional.of(pet));

        // Выполняем GET-запрос через контроллер
        ResponseEntity<Pet> response = petController.getPetById(1L);

        // Проверяем статус ответа и тело
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Max", response.getBody().getName());

        // Проверяем, что метод getPetById был вызван ровно один раз
        verify(petService, times(1)).getPetById(1L);
    }

    @Test
    void testGetPetByIdNotFound() {
        // Мокируем вызов метода getPetById в сервисе (питомец не найден)
        when(petService.getPetById(1L)).thenReturn(Optional.empty());

        // Выполняем GET-запрос через контроллер
        ResponseEntity<Pet> response = petController.getPetById(1L);

        // Проверяем статус ответа (404 Not Found)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Проверяем, что метод getPetById был вызван ровно один раз
        verify(petService, times(1)).getPetById(1L);
    }

    @Test
    void testUpdatePet() {
        // Подготовка тестовых данных
        Pet pet = new Pet();
        pet.setId(1L);
        pet.setName("Rex");

        // Мокируем вызов метода savePet в сервисе
        when(petService.savePet(pet)).thenReturn(pet);

        // Выполняем PUT-запрос через контроллер
        ResponseEntity<Pet> response = petController.updatePet(pet);

        // Проверяем статус ответа и тело
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rex", response.getBody().getName());

        // Проверяем, что метод savePet был вызван ровно один раз
        verify(petService, times(1)).savePet(pet);
    }

    @Test
    void testDeletePet() {
        // Мокируем вызов метода deletePet в сервисе
        doNothing().when(petService).deletePet(1L);

        // Выполняем DELETE-запрос через контроллер
        ResponseEntity<Void> response = petController.deletePet(1L);

        // Проверяем статус ответа (204 No Content)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Проверяем, что метод deletePet был вызван ровно один раз
        verify(petService, times(1)).deletePet(1L);
    }
}