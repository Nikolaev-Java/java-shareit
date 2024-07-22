package ru.practicum.shareit.item.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.util.List;

@Component
public class ItemMapper {
    public ItemDto toDto(Item item) {
        if (item == null) return null;
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.isAvailable());
        return dto;
    }

    public Item fromDto(ItemDto dto, User user) {
        if (dto == null) return null;
        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setOwner(user);
        item.setAvailable(dto.getAvailable());
        return item;
    }

    public List<ItemDto> toDtoList(List<Item> items) {
        if (items == null) return null;
        return items.stream().map(this::toDto).toList();
    }
}
