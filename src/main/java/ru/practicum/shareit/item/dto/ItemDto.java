package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.validationMarker.Marker;

/**
 * TODO Sprint add-controllers.
 */
@Data
@EqualsAndHashCode(of = "id")
public class ItemDto {
    @Null(message = "The ID must be null", groups = Marker.onCreate.class)
    private Long id;
    @NotBlank(message = "The name should not be blank", groups = Marker.onCreate.class)
    private String name;
    @NotNull(message = "The description should not be null", groups = Marker.onCreate.class)
    private String description;
    @NotNull(message = "The available should not be null", groups = Marker.onCreate.class)
    private Boolean available;
}
