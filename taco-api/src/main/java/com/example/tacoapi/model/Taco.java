package com.example.tacoapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "tacos")
@RestResource(rel = "tacos", path = "tacos")
@AllArgsConstructor
@NoArgsConstructor
public class Taco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Taco(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    @NotNull
    @Size(min = 3, message = "Name should be at least 3 characters")
    private String name;

    @ToString.Exclude
    @ManyToMany(targetEntity = Ingredient.class)
    private List<Ingredient> ingredients;

    private Date createdAt;

    @PrePersist
    void createdAt() {
        createdAt = new Date();
    }

    public double totalPrice() {
        return Double.parseDouble(
                String.format("%.2f",
                        ingredients
                                .stream()
                                .mapToDouble(Ingredient::getPrice)
                                .sum()));
    }
}
