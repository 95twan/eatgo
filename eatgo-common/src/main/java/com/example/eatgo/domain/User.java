package com.example.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @NotEmpty
    private String name;

    @Setter
    private String password;

    private Long restaurantId;

    @Setter
    @NotEmpty
    @Column(unique = true)
    private String email;

    @Setter
    private Long level;

    public void deactivate(){
        this.level = 0L;
    }

    @JsonIgnore
    public boolean isAdmin() {
        return level == 3L;
    }

    @JsonIgnore
    public boolean isActive() {
        return level != 0L;
    }

    public void setRestaurantId(Long restaurantId){
        this.level = 2L;
        this.restaurantId = restaurantId;
    }

    @JsonIgnore
    public boolean isRestaurantOwner() {
        return this.level == 2L;
    }
}
