package ru.practicum.shareit.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingState;
import ru.practicum.shareit.booking.dto.NewBookingDtoRequest;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";
    private static final Logger log = LoggerFactory.getLogger(BookingClient.class);

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createBooking(NewBookingDtoRequest dto, long userId) {
        return post("", userId, dto);
    }

    public ResponseEntity<Object> decideRent(long bookingId, long userId, Boolean approved) {
        log.warn("Deciding Rent for booking with id {} - {}", bookingId, approved);
        return patch("/" + bookingId + "?approved={approved}", userId, Map.of("approved", approved));
    }

    public ResponseEntity<Object> getBookingByIdOfBookerOrOwner(long bookingId, long userId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> getAllBookingsByBooker(long userId, BookingState state) {
        return get("?state={state}", userId, Map.of("state", state));
    }

    public ResponseEntity<Object> getAllBookingsByOwner(long userId, BookingState state) {
        return get("/owner?state={state}", userId, Map.of("state", state));
    }
}
