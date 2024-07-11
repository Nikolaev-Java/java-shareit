package ru.practicum.shareit.user.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.validationMarker.Marker;

import java.util.List;

@Validated
public interface UserService {
    UserDto getById(long id);

    @Validated({Marker.OnCreate.class})
    UserDto create(@Valid UserDto dto);

    List<UserDto> getAll();

    @Validated({Marker.OnUpdate.class})
    UserDto update(@Positive long id, @Valid UserDto dto);

    void delete(long id);
}
