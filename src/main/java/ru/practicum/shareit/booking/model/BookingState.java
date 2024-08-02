package ru.practicum.shareit.booking.model;

public enum BookingState {
    ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED;

    public static BookingState parseString(String state) {
        return switch (state.toUpperCase()) {
            case "ALL" -> ALL;
            case "CURRENT" -> CURRENT;
            case "PAST" -> PAST;
            case "FUTURE" -> FUTURE;
            case "WAITING" -> WAITING;
            case "REJECTED" -> REJECTED;
            default -> throw new IllegalArgumentException("Unknown state: " + state);
        };
    }
}
