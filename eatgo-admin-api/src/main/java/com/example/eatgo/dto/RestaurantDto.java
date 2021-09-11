package com.example.eatgo.dto;

import com.example.eatgo.domain.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
public class RestaurantDto {

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        @NotNull
        private Long categoryId;

        @NotBlank
        private String name;

        @NotBlank
        private String address;
    }

    public static Restaurant requestToEntity(Request request){
        return Restaurant.builder()
                .categoryId(request.categoryId)
                .name(request.name)
                .address(request.address)
                .build();
    }
}
