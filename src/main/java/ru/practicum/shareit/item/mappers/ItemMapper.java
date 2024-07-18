package ru.practicum.shareit.item.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

@Component
public class ItemMapper {
    public ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.isAvailable());
        return dto;
    }

    public Item fromDto(ItemDto dto, User user) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setOwner(user);
        item.setAvailable(dto.getAvailable());
        return item;
    }


}
