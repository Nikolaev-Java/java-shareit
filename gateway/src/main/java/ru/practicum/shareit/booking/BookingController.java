package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.NewBookingDtoRequest;
import ru.practicum.shareit.validationMarker.Marker;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Validated
public class BookingController {
    private final BookingClient bookingClient;
    private static final String USER_ID = "X-Sharer-User-Id";

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public ResponseEntity<Object> createBooking(@RequestBody @Valid NewBookingDtoRequest dto,
                                                @RequestHeader(USER_ID) long userId) {
        dto.setBookerId(userId);
        return bookingClient.createBooking(dto, userId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> decideRent(@PathVariable long bookingId,
                                             @RequestHeader(USER_ID) long userId,
                                             @RequestParam Boolean approved) {
        return bookingClient.decideRent(bookingId, userId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingByIdOfBookerOrOwner(@PathVariable long bookingId,
                                                                @RequestHeader(USER_ID) long userId) {
        return bookingClient.getBookingByIdOfBookerOrOwner(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingsByBooker(@RequestHeader(USER_ID) long userId,
                                                         @RequestParam(name = "state", defaultValue = "ALL") String stateParam) {
        BookingState state = BookingState.parseString(stateParam);
        return bookingClient.getAllBookingsByBooker(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsByOwner(@RequestHeader(USER_ID) long userId,
                                                        @RequestParam(name = "state", defaultValue = "ALL") String stateParam) {
        BookingState state = BookingState.parseString(stateParam);
        return bookingClient.getAllBookingsByOwner(userId, state);
    }
}
