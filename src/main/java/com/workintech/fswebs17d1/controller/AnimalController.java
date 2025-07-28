package com.workintech.fswebs17d1.controller;

import com.workintech.fswebs17d1.entity.Animal;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//http://localhost:8585/workintech/animal
@RestController
@RequestMapping(path = "/workintech/animal")
//Bu, sınıfın tüm URL'lerinin /workintech/animal ile başlamasını sağlar. Yani, bu kontrolcüye yapılan tüm istekler bu URL ile başlar.
public class AnimalController {
    private Map<Integer, Animal> animals;


    @PostConstruct
    public void loadAll() {
        // Başlatma işlemleri burada yapılır
        System.out.println("postconstruct çalıştı.... Bean is created and dependencies are injected. Initialization logic here.");
        this.animals = new HashMap<>();
        this.animals.put(1, new Animal(1,"maymun"));
    }

    //Tüm Hayvanları Getir:
    @GetMapping
    public List<Animal> getAnimals(){
        System.out.println("... animals get all triggered!!");

        return new ArrayList<>(animals.values());
    }

    // ID'ye Göre Hayvan Getir:Örneğin: /workintech/animal/1.
    @GetMapping("/{id}")
    public Animal getAnimalById(@PathVariable("id") int id) {
        if(id<0){
            return null;
        }
        return animals.get(id);
    }

    //Yeni Hayvan Ekle:
    @PostMapping
    public String addAnimal(@RequestBody Animal animal){
        System.out.println("add animal is triggered");
        if (animals.containsKey(animal.getId())) {
            return "Animal with this ID already exists!";
        } else{
            this.animals.put(animal.getId(), animal);
        }

        return "Animal added successfully!";
    }

    // Mevcut Hayvanı Güncelle:
    @PutMapping("/{id}")
    public Animal updateAnimal(@PathVariable int id, @RequestBody Animal updatedAnimal) {
        this.animals.replace(id,updatedAnimal);
        return animals.get(id);
    }

    // Hayvan Sil:
    @DeleteMapping("/{id}")
    public void deleteAnimal(@PathVariable("id") int id) {
        this.animals.remove(id);
    }
}