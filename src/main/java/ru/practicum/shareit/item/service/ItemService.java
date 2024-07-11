package ru.practicum.shareit.item.service;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.validationMarker.Marker;

import java.util.List;

@Validated
public interface ItemService {
    @Validated(Marker.onCreate.class)
    ItemDto create(@Valid ItemDto itemDto, Long userId);

    @Validated(Marker.onUpdate.class)
    ItemDto update(Long id, @Valid ItemDto itemDto, Long userId);

    ItemDto getById(Long id);

    List<ItemDto> getAllOfOwner(Long userId);

    List<ItemDto> findByNameByDescription(String text);
}
