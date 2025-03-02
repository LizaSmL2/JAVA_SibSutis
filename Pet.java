package com.example.petstore.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class Pet {
    private Long id;
    private String name;
    private Category category;
    private List<Tag> tags;
    private String status; // "available", "pending", "sold"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

@Data
@NoArgsConstructor
class Category {
    private Long id;
    private String name;
}

@Data
@NoArgsConstructor
class Tag {
    private Long id;
    private String name;
}


