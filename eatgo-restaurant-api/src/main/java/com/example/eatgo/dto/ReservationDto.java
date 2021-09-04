package com.example.eatgo.dto;

import com.example.eatgo.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long id;

    private Long userId;

    private String userName;

    @NotEmpty
    private String date;

    @NotEmpty
    private String time;

    @NotNull
    private Integer partySize;

    public static ReservationDto EntityToDto(Reservation reservation){
        return ReservationDto.builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .userName(reservation.getUserName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .partySize(reservation.getPartySize())
                .build();
    }
}
