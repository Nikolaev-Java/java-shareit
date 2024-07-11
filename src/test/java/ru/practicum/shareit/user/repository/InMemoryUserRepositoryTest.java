package ru.practicum.shareit.user.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.utils.DataUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class InMemoryUserRepositoryTest {
    private InMemoryUserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    @DisplayName("Test create user functionality")
    public void givenUser_whenCreateUser_thenUserIsCreated() {
        //given
        User userToCreate = DataUtils.getUserTestTransient(1);
        //when
        User userCreated = userRepository.create(userToCreate);
        //then
        assertThat(userCreated).isNotNull();
        assertThat(userCreated.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUser_whenUpdateUser_thenUserIsUpdated() {
        //given
        User userUpdateName = DataUtils.getUserTestTransient(1);
        userRepository.create(userUpdateName);
        String updateName = "updated Name";
        //when
        User userToUpdate = userRepository.getById(userUpdateName.getId()).orElse(null);
        userToUpdate.setName(updateName);
        User userUpdated = userRepository.update(userToUpdate.getId(), userToUpdate);
        //then
        assertThat(userUpdated).isNotNull();
        assertThat(userUpdated.getName()).isEqualTo(updateName);
    }

    @Test
    @DisplayName("Test delete user functionality")
    public void givenUser_whenDeleteUser_thenUserIsDeleted() {
        //given
        User user = DataUtils.getUserTestTransient(1);
        userRepository.create(user);
        //when
        userRepository.delete(user.getId());
        User userDeleted = userRepository.getById(user.getId()).orElse(null);
        //then
        assertThat(userDeleted).isNull();
    }

    @Test
    @DisplayName("Test get user by id functionality")
    public void givenUser_whenGetUserById_thenUserIsReturned() {
        //given
        User user = DataUtils.getUserTestTransient(1);
        userRepository.create(user);
        //when
        User userReturned = userRepository.getById(user.getId()).orElse(null);
        //then
        assertThat(userReturned).isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(user);
    }

    @Test
    @DisplayName("Test get user incorrect id functionality")
    public void givenUser_whenGetUserByInvalidId_thenUserIsNotReturned() {
        //given

        //when
        User userReturned = userRepository.getById(999).orElse(null);
        //then
        assertThat(userReturned).isNull();
    }

    @Test
    @DisplayName("Test get all user functionality")
    public void givenUser_whenGetAllUsers_thenUsersIsReturned() {
        //given
        User user1 = DataUtils.getUserTestTransient(1);
        User user2 = DataUtils.getUserTestTransient(2);
        User user3 = DataUtils.getUserTestTransient(3);
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);
        //when
        List<User> users = userRepository.getAll();
        //then
        assertThat(users).hasSize(3);
    }
}