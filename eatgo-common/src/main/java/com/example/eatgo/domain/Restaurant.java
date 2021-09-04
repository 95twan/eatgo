package com.example.eatgo.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private Long categoryId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIncludeProperties(value = {"name"})
    private List<MenuItem> menuItems;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(value = {"id", "restaurantId"})
    private List<Review> reviews;

    @JsonIgnore
    public String getInformation() {
        return name + " in " + address;
    }

    public void addMenuItem(MenuItem menuItem) {
        if(this.menuItems == null){
            this.menuItems = new ArrayList<>();
        }
        this.menuItems.add(menuItem);
    }

    public void addMenuItems(List<MenuItem> menuItems) {
        if(this.menuItems == null){
            this.menuItems = new ArrayList<>();
        }
        this.menuItems.addAll(menuItems);
    }

    public void addReview(Review review) {
        if(this.reviews == null){
            this.reviews = new ArrayList<>();
        }
        this.reviews.add(review);
    }

    public void addReviews(List<Review> reviews) {
        if(this.reviews == null){
            this.reviews = new ArrayList<>();
        }
        this.reviews.addAll(reviews);
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
