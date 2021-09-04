package com.example.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    public void creation(){
        User user = User.builder().name("test").email("test@example.com").level(1L).build();

        assertThat(user.getName()).isEqualTo("test");
        assertThat(user.isAdmin()).isFalse();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    public void isRestaurantOwner(){
        User user = User.builder().level(1L).build();

        assertThat(user.isRestaurantOwner()).isFalse();

        user.setRestaurantId(1004L);

        assertThat(user.isRestaurantOwner()).isTrue();
        assertThat(user.getRestaurantId()).isEqualTo(1004L);

    }
}
