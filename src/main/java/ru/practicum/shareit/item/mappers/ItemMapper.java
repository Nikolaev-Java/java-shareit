package ru.practicum.shareit.item.mappers;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.isAvailable());
        return dto;
    }

    public static Item fromDto(ItemDto dto, long userID) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setOwnerId(userID);
        item.setAvailable(dto.getAvailable());
        return item;
    }

    public static Item toUpdateItem(ItemDto dto, Item item) {
        Item updateItem = new Item();
        updateItem.setId(item.getId());
        updateItem.setName(item.getName());
        updateItem.setDescription(item.getDescription());
        updateItem.setOwnerId(item.getOwnerId());
        updateItem.setAvailable(item.isAvailable());
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            updateItem.setDescription(dto.getDescription());
        }
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            updateItem.setName(dto.getName());
        }
        if (dto.getAvailable() != null) {
            updateItem.setAvailable(dto.getAvailable());
        }
        return updateItem;
    }
}
