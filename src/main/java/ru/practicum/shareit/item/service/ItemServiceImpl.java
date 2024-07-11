package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mappers.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static ru.practicum.shareit.item.mappers.ItemMapper.fromDto;
import static ru.practicum.shareit.item.mappers.ItemMapper.toDto;
import static ru.practicum.shareit.item.mappers.ItemMapper.toUpdateItem;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {
        if (userRepository.getById(userId).isEmpty()) {
            throw new NotFoundException("User with id - " + userId + " not found");
        }
        Item item = itemRepository.create(fromDto(itemDto, userId));
        return toDto(item);
    }

    @Override
    public ItemDto update(Long id, ItemDto itemDto, Long userId) {
        if (userRepository.getById(userId).isEmpty()) {
            throw new NotFoundException("User with id - " + userId + " not found");
        }
        Item item = itemRepository.getById(id).orElseThrow(
                () -> new NotFoundException("Item with id - " + id + " not found"));
        if (!item.getOwnerId().equals(userId)) {
            throw new AccessException("The user with the id  - " + userId + " is not the owner");
        }
        Item update = itemRepository.update(toUpdateItem(itemDto, item));
        return toDto(update);
    }

    @Override
    public ItemDto getById(Long id) {
        Item item = itemRepository.getById(id).orElseThrow(
                () -> new NotFoundException("Item with id - " + id + " not found"));
        return toDto(item);
    }

    @Override
    public List<ItemDto> getAllOfOwner(Long userId) {
        if (userRepository.getById(userId).isEmpty()) {
            throw new NotFoundException("User with id - " + userId + " not found");
        }
        List<Item> items = itemRepository.getAllOfOwner(userId);
        return items.stream().map(ItemMapper::toDto).toList();
    }

    @Override
    public List<ItemDto> findByNameByDescription(String text) {
        List<Item> items = itemRepository.findByNameByDescription(text);
        return items.stream().map(ItemMapper::toDto).toList();
    }
}
