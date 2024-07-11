package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> getById(long id);

    List<User> getAll();

    User create(User user);

    User update(long id, User user);

    void delete(long id);

    List<String> getEmails();
}
