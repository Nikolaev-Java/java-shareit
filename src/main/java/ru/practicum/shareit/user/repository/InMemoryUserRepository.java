package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@Slf4j
public class InMemoryUserRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
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
        user.setId(generatesId());
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }

    @Override
    public User update(long id, User user) {
        log.info("Updating user with id {} to {}", id, user);
        String email = users.get(id).getEmail();
        if (!email.equals(user.getEmail())) {
            emails.remove(email);
        }
        users.put(user.getId(), user);
        emails.add(user.getEmail());
        return user;
    }

    @Override
    public void delete(long id) {
        emails.remove(users.get(id).getEmail());
        users.remove(id);
    }

    @Override
    public List<String> getEmails() {
        return emails.stream().toList();
    }

    private long generatesId() {
        return ++id;
    }
}
