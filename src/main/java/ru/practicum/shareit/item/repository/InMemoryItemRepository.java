package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Map<Long, Item>> items = new HashMap<>();
    private long id = 0;

    @Override
    public Item create(Item item) {
        item.setId(generateId());
        items.computeIfAbsent(item.getOwnerId(), k -> new HashMap<>()).put(item.getId(), item);
        return item;
    }


    @Override
    public Item update(Item item) {
        checkExist(item.getId(), item.getOwnerId());
        items.get(item.getOwnerId()).put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> getById(Long id) {
        Map<Long, Item> itemsAll = items.values().stream()
                .flatMap(itemsMap -> itemsMap.values().stream())
                .collect(Collectors.toMap(Item::getId, i -> i));
        return Optional.ofNullable(itemsAll.get(id));
    }

    @Override
    public List<Item> getAllOfOwner(Long userId) {
        return new ArrayList<>(items.get(userId).values());
    }

    @Override
    public List<Item> findByNameByDescription(String text) {
        return items.values().stream()
                .flatMap(m -> m.values().stream())
                .filter(item -> (item.getDescription().toLowerCase().contains(text.toLowerCase())
                        || item.getName().toLowerCase().contains(text.toLowerCase())) && item.isAvailable())
                .toList();
    }

    private Long generateId() {
        return ++id;
    }

    private void checkExist(long id, long userId) {
        if (!items.get(userId).containsKey(id)) {
            throw new NotFoundException("Item " + id + " does not exist");
        }
    }
}
