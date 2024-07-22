package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryItemRepository implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private final Map<Long, List<Item>> itemsByUser = new HashMap<>();
    private long id = 0;

    @Override
    public Item create(Item item) {
        item.setId(generateId());
        items.put(item.getId(), item);
        itemsByUser.computeIfAbsent(item.getOwner().getId(), k -> new ArrayList<>()).add(item);
        return item;
    }


    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        itemsByUser.get(item.getOwner().getId()).remove(item);
        itemsByUser.get(item.getOwner().getId()).add(item);
        return item;
    }

    @Override
    public Optional<Item> getById(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public List<Item> getAllOfOwner(Long userId) {
        return new ArrayList<>(itemsByUser.get(userId));
    }

    @Override
    public List<Item> findByNameByDescription(String text) {
        return items.values().stream()
                .filter(item -> (item.getDescription().toLowerCase().contains(text.toLowerCase())
                        || item.getName().toLowerCase().contains(text.toLowerCase())) && item.isAvailable())
                .toList();
    }

    private Long generateId() {
        return ++id;
    }
}
