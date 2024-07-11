package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mappers.UserMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getById(long id) {
        User user = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        log.info("User with id {} found", id);
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto create(UserDto dto) {
        if (userRepository.getEmails().contains(dto.getEmail())) {
            throw new DuplicatedDataException("Email address already exists");
        }
        User newUser = userRepository.create(userMapper.toNewUser(dto));
        log.info("User with id {} created. {}", newUser.getId(), newUser);
        return userMapper.toUserDto(newUser);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.getAll();
        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto update(long id, UserDto dto) {
        log.info("Request to update user with id {} to {}", id, dto);
        User oldUser = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setName(oldUser.getName());
        updateUser.setEmail(oldUser.getEmail());
        if (dto.getEmail() != null && !dto.getEmail().equals(updateUser.getEmail())) {
            if (userRepository.getEmails().contains(dto.getEmail())) {
                throw new DuplicatedDataException("Email address already exists");
            }
            updateUser.setEmail(dto.getEmail());
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            updateUser.setName(dto.getName());
        }
        log.info("User with id {} updated {}", id, updateUser);
        userRepository.update(id, updateUser);
        return userMapper.toUserDto(updateUser);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
        log.info("User with id {} deleted", id);
    }
}
