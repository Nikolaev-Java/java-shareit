package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.dto.NewBookingDtoRequest;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.validationMarker.Marker;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Validated
public class BookingController {
    private final BookingService bookingService;
    private static final String USER_ID = "X-Sharer-User-Id";

    @PostMapping
    @Validated(Marker.OnCreate.class)
    public BookingDtoResponse createBooking(@RequestBody @Valid NewBookingDtoRequest dto,
                                            @RequestHeader(USER_ID) long userId) {
        dto.setBookerId(userId);
        return bookingService.createBooking(dto);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse decideRent(@PathVariable long bookingId,
                                         @RequestHeader(USER_ID) long userId,
                                         @RequestParam boolean approved) {
        if (approved) {
            return bookingService.approveBooking(bookingId, userId);
        } else
            return bookingService.rejectBooking(bookingId, userId);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoResponse getBookingByIdOfBookerOrOwner(@PathVariable long bookingId,
                                                            @RequestHeader(USER_ID) long userId) {
        return bookingService.getBookingByIdOfBookerOrOwner(bookingId, userId);
    }

    @GetMapping
    public List<BookingDtoResponse> getAllBookingsByBooker(@RequestHeader(USER_ID) long userId,
                                                           @RequestParam(name = "state", defaultValue = "ALL") String stateParam) {
        BookingState state = BookingState.parseString(stateParam);
        return bookingService.getAllBookingsByBooker(userId, state);
    }

    @GetMapping("/owner")
    public List<BookingDtoResponse> getAllBookingsByOwner(@RequestHeader(USER_ID) long userId,
                                                          @RequestParam(name = "state", defaultValue = "ALL") String stateParam) {
        BookingState state = BookingState.parseString(stateParam);
        return bookingService.getAllBookingsByOwner(userId, state);
    }
}
