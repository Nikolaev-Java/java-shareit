package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mappers.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static ru.practicum.shareit.user.mappers.UserMapper.toNewUser;
import static ru.practicum.shareit.user.mappers.UserMapper.toUpdateUser;
import static ru.practicum.shareit.user.mappers.UserMapper.toUserDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto getById(long id) {
        User user = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        log.info("User with id {} found", id);
        return toUserDto(user);
    }

    @Override
    public UserDto create(UserDto dto) {
        User newUser = userRepository.create(toNewUser(dto));
        log.info("User with id {} created. {}", newUser.getId(), newUser);
        return toUserDto(newUser);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.getAll();
        return users.stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto update(long id, UserDto dto) {
        log.info("Request to update user with id {} to {}", id, dto);
        User updateUser = userRepository.getById(id)
                .map(user -> toUpdateUser(user, dto))
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        log.info("User with id {} updated {}", id, updateUser);
        userRepository.update(id, updateUser);
        return toUserDto(updateUser);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
        log.info("User with id {} deleted", id);
    }
}
