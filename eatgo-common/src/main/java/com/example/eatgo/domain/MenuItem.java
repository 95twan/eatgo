package com.example.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MenuItem {
    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Long restaurantId;

    private String name;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean destroy;

}
