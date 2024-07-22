package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.validationMarker.Marker;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService service;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.getAll();
    }

    @PostMapping
    @Validated({Marker.OnCreate.class})
    public UserDto createUser(@RequestBody @Valid UserDto dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    @Validated({Marker.OnUpdate.class})
    public UserDto updateUser(@PathVariable long id, @RequestBody @Valid UserDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        service.delete(id);
    }
}
