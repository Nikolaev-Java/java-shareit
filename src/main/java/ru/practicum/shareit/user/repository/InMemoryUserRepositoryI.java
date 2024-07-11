package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class InMemoryUserRepositoryI implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long id = 0;

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        checkExistsByEmail(user);
        user.setId(generatesId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void update(long id, User user) {
        log.info("Updating user with id {} to {}", id, user);
        checkExistsByEmail(user);
        users.put(user.getId(), user);
    }

    @Override
    public void delete(long id) {
        users.remove(id);
    }

    private void checkExistsByEmail(User user) {
        boolean existEmail = users.values().stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()) && !u.getId().equals(user.getId()));
        if (existEmail) {
            log.error("User with email {} already exists", user.getEmail());
            throw new DuplicatedDataException("Email address already in use");
        }
    }

    private long generatesId() {
        return ++id;
    }
}
