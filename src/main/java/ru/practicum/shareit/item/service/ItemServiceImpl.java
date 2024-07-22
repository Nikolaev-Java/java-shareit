package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mappers.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("User with id - " + userId + " not found"));
        Item item = itemRepository.create(itemMapper.fromDto(itemDto, user));
        return itemMapper.toDto(item);
    }

    @Override
    public ItemDto update(ItemDto itemDto, Long userId) {
        checkIfExistsUser(userId);
        Item item = itemRepository.getById(itemDto.getId()).orElseThrow(
                () -> new NotFoundException("Item with id - " + itemDto.getId() + " not found"));
        if (!item.getOwner().getId().equals(userId)) {
            throw new AccessException("The user with the id  - " + userId + " is not the owner");
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isEmpty()) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getName() != null && !itemDto.getName().isEmpty()) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        Item update = itemRepository.update(item);
        return itemMapper.toDto(update);
    }

    @Override
    public ItemDto getById(Long id) {
        Item item = itemRepository.getById(id).orElseThrow(
                () -> new NotFoundException("Item with id - " + id + " not found"));
        return itemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAllOfOwner(Long userId) {
        checkIfExistsUser(userId);
        List<Item> items = itemRepository.getAllOfOwner(userId);
        return itemMapper.toDtoList(items);
    }

    @Override
    public List<ItemDto> findByNameByDescription(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        List<Item> items = itemRepository.findByNameByDescription(text);
        return itemMapper.toDtoList(items);
    }

    private void checkIfExistsUser(Long userId) {
        if (userRepository.getById(userId).isEmpty()) {
            throw new NotFoundException("User with id - " + userId + " not found");
        }
    }
}
