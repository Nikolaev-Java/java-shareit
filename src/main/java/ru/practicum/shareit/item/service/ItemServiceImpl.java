package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mappers.ItemMapper;
import ru.practicum.shareit.item.Item;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id - " + userId + " not found"));
        Item item = itemRepository.save(itemMapper.fromDto(itemDto, user));
        return itemMapper.toDto(item);
    }

    @Override
    public ItemDto update(ItemDto itemDto, Long userId) {
        checkIfExistsUser(userId);
        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(
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
        Item update = itemRepository.save(item);
        return itemMapper.toDto(update);
    }

    @Override
    public ItemDto getById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Item with id - " + id + " not found"));
        return itemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAllOfOwner(Long userId) {
        checkIfExistsUser(userId);
        List<Item> items = itemRepository.findAllByOwnerId(userId);
        return itemMapper.toDtoList(items);
    }

    @Override
    public List<ItemDto> findByNameByDescription(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        List<Item> items = itemRepository.findAllByNameOrDescription(text);
        return itemMapper.toDtoList(items);
    }

    private void checkIfExistsUser(Long userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with id - " + userId + " not found");
        }
    }
}
