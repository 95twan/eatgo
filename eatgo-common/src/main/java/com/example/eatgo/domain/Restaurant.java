package com.example.eatgo.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    private Long categoryId;

    private String name;

    private String address;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIncludeProperties(value = {"name"})
    private List<MenuItem> menuItems = new ArrayList<>();

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(value = {"id", "restaurantId"})
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Restaurant(Long id, Long categoryId, String name, String address) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.address = address;
    }

    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAddress(String address){
        this.address = address;
    }
}
