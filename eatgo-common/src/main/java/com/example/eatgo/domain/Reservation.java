package com.example.eatgo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    private Long userId;

    private String userName;

    @NotEmpty
    private String date;

    @NotEmpty
    private String time;

    @NotNull
    private Integer partySize;

}
