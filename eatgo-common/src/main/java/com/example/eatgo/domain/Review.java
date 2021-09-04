package com.example.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    private Long restaurantId;

    @Setter
    private String name;

    @NotNull
    @Min(0)
    @Max(5)
    private Integer score;

    @NotEmpty
    private String description;
}
