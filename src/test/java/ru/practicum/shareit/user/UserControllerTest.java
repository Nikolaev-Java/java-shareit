package ru.practicum.shareit.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.practicum.shareit.matcher.ResponseBodyMatcher.responseBody;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final static String URL = "/users";

    @Test
    void getUserById_whenUserFound_thenReturn200AndResult() throws Exception {
        UserDto userDto = getUserTestUser(1);
        addUser();
        userDto.setId(1L);
        mvc.perform(get(URL + "/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(userDto, UserDto.class));
    }

    @Test
    void getUserById_whenUserNotFound_thenReturn404AndResult() throws Exception {
        mvc.perform(get(URL + "/1")
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().containsError("User with id 1 not found"));
    }

    @Test
    void getAllUsers_thenReturn200AndResult() throws Exception {
        List<UserDto> expectedUsers = getUserListTestUser();
        long id = 0;
        for (UserDto expectedUser : expectedUsers) {
            expectedUser.setId(++id);
        }
        addUsers();
        mvc.perform(get(URL)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsListAsJson(expectedUsers, new TypeReference<List<UserDto>>() {
                }));
    }

    @Test
    void createUser_whenValidInput_thenReturn200AndResult() throws Exception {
        UserDto userTestUser = getUserTestUser(1);
        String query = objectMapper.writeValueAsString(userTestUser);
        userTestUser.setId(1L);
        mvc.perform(post(URL)
                        .contentType("application/json")
                        .content(query))
                .andExpect(status().isOk())
                .andExpect(responseBody().containsObjectAsJson(userTestUser, UserDto.class));
    }

    @Test
    void createUser_whenNoEmailInput_thenReturn400AndResult() throws Exception {
        UserDto userTestUser = incorrectUserDtoNoEmail();
        String query = objectMapper.writeValueAsString(userTestUser);
        mvc.perform(post(URL)
                        .contentType("application/json")
                        .content(query))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsErrorValid(
                        "Invalid value of the create.dto.email parameter: email not be null"));
    }

    @Test
    void createUser_whenBlackNameInput_thenReturn400AndResult() throws Exception {
        UserDto userTestUser = incorrectUserDtoBlankName();
        String query = objectMapper.writeValueAsString(userTestUser);
        mvc.perform(post(URL)
                        .contentType("application/json")
                        .content(query))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsErrorValid(
                        "Invalid value of the create.dto.name parameter: name not be blank"));
    }

    @Test
    void createUser_whenIncorrectEmailInput_thenReturn400AndResult() throws Exception {
        UserDto userTestUser = incorrectUserDtoEmail();
        String query = objectMapper.writeValueAsString(userTestUser);
        mvc.perform(post(URL)
                        .contentType("application/json")
                        .content(query))
                .andExpect(status().isBadRequest())
                .andExpect(responseBody().containsErrorValid(
                        "Invalid value of the create.dto.email parameter: incorrect email"));
    }

    @Test
    void createUser_whenDuplicateEmailInput_thenReturn400AndResult() throws Exception {
        addUser();
        UserDto userTestUser = getUserTestUser(1);
        String query = objectMapper.writeValueAsString(userTestUser);
        mvc.perform(post(URL)
                        .contentType("application/json")
                        .content(query))
                .andExpect(status().isConflict())
                .andExpect(responseBody().containsError(
                        "Email address already in use"));
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser_thenReturn200() throws Exception {
        addUsers();
        mvc.perform(delete(URL + "/1"))
                .andExpect(status().isOk());
        mvc.perform(get(URL + "/1")
                        .contentType("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(responseBody().containsError("User with id 1 not found"));
    }

    private UserDto getUserTestUser(int nameVariable) {
        UserDto userDto = new UserDto();
        userDto.setName("Test" + nameVariable);
        userDto.setEmail("Test" + nameVariable + "@test.com");
        return userDto;
    }

    private List<UserDto> getUserListTestUser() {
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(getUserTestUser(1));
        userDtoList.add(getUserTestUser(2));
        userDtoList.add(getUserTestUser(3));
        return userDtoList;
    }

    private UserDto incorrectUserDtoNoEmail() {
        UserDto userDto = new UserDto();
        userDto.setName("Test");
        return userDto;
    }

    private UserDto incorrectUserDtoBlankName() {
        UserDto userDto = new UserDto();
        userDto.setName("");
        userDto.setEmail("test@test.com");
        return userDto;
    }

    private UserDto incorrectUserDtoEmail() {
        UserDto userDto = new UserDto();
        userDto.setName("Test");
        userDto.setEmail("test");
        return userDto;
    }

    private void addUsers() throws Exception {
        List<UserDto> userListTestUser = getUserListTestUser();
        for (UserDto userDto : userListTestUser) {
            String body = objectMapper.writeValueAsString(userDto);
            mvc.perform(post(URL)
                    .contentType("application/json")
                    .content(body));
        }
    }

    private void addUser() throws Exception {
        UserDto userDto = getUserTestUser(1);
        String body = objectMapper.writeValueAsString(userDto);
        mvc.perform(post(URL)
                .contentType("application/json")
                .content(body));
    }
}