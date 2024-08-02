package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Getter
@Setter
@Builder
public class BookingDtoResponse {
    private long id;
    private ItemDtoResponse item;
    private UserDtoResponse booker;
    private LocalDateTime start;
    private LocalDateTime end;
    private String status;

    @Setter
    @Getter
    @Builder
    static class ItemDtoResponse {
        private long id;
        private String name;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    static class UserDtoResponse {
        private long id;
    }
}
