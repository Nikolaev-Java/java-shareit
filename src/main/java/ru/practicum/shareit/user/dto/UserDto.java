package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.validationMarker.Marker;

@Data
@EqualsAndHashCode(of = "id")
public class UserDto {
    @Null(groups = Marker.onCreate.class)
    private Long id;
    @NotBlank(message = "name not be blank", groups = Marker.onCreate.class)
    private String name;
    @Email(message = "incorrect email", groups = {Marker.onCreate.class, Marker.onUpdate.class})
    @NotNull(message = "email not be null", groups = Marker.onCreate.class)
    private String email;
}
