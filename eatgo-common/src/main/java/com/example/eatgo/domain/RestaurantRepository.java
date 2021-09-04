package com.example.eatgo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByAddressContaining(String region);

    List<Restaurant> findAllByCategoryId(Long categoryId);

    List<Restaurant> findAllByAddressContainingAndCategoryId(String region, Long categoryId);
}
