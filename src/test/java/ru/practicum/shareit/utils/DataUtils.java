package ru.practicum.shareit.utils;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public class DataUtils {
    public static User getUserTestTransient(int nameVariable) {
        User user = new User();
        user.setName("Test" + nameVariable);
        user.setEmail("Test" + nameVariable + "@test.com");
        return user;
    }

    public static User getUserTestPersistence(int nameVariable) {
        User user = new User();
        user.setId((long) nameVariable);
        user.setName("Test" + nameVariable);
        user.setEmail("Test" + nameVariable + "@test.com");
        return user;
    }

    public static UserDto getUserDtoTestTransient(int nameVariable) {
        UserDto userDto = new UserDto();
        userDto.setName("Test" + nameVariable);
        userDto.setEmail("Test" + nameVariable + "@test.com");
        return userDto;
    }

    public static UserDto getUserDtoTestPersistence(int nameVariable) {
        UserDto userDto = new UserDto();
        userDto.setId((long) nameVariable);
        userDto.setName("Test" + nameVariable);
        userDto.setEmail("Test" + nameVariable + "@test.com");
        return userDto;
    }


    public static Item getItemTestTransient(int nameVariable) {
        Item item = new Item();
        item.setName("Test" + nameVariable);
        item.setDescription("Test" + nameVariable);
        item.setAvailable(true);
        return item;
    }

    public static Item getItemTestPersistence(int nameVariable) {
        Item item = new Item();
        item.setId((long) nameVariable);
        item.setName("Test" + nameVariable);
        item.setDescription("Test" + nameVariable);
        return item;
    }

    public static ItemDto getItemDtoTestTransient(int nameVariable) {
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Test" + nameVariable);
        itemDto.setDescription("Test" + nameVariable);
        itemDto.setAvailable(true);
        return itemDto;
    }

    public static ItemDto getItemDtoTestPersistence(int nameVariable) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId((long) nameVariable);
        itemDto.setName("Test" + nameVariable);
        itemDto.setDescription("Test" + nameVariable);
        itemDto.setAvailable(true);
        return itemDto;
    }
}
