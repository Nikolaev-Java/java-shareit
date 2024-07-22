package ru.practicum.shareit.item.service;

import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@Validated
public interface ItemService {

    ItemDto create(ItemDto itemDto, Long userId);

    ItemDto update(ItemDto itemDto, Long userId);

    ItemDto getById(Long id);

    List<ItemDto> getAllOfOwner(Long userId);

    List<ItemDto> findByNameByDescription(String text);
}
