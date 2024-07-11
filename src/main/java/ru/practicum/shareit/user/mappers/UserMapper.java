package ru.practicum.shareit.user.mappers;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static User toNewUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static User toUpdateUser(User user, UserDto userDto) {
        User userUpdate = new User();
        userUpdate.setId(user.getId());
        userUpdate.setName(user.getName());
        userUpdate.setEmail(user.getEmail());
        if (userDto.getEmail() != null) {
            userUpdate.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null && !userDto.getName().isEmpty()) {
            userUpdate.setName(userDto.getName());
        }
        return userUpdate;
    }
}
