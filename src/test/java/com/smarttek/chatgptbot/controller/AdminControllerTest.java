package com.smarttek.chatgptbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarttek.chatgptbot.client.TelegramClient;
import com.smarttek.chatgptbot.dto.user.UserDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminControllerTest {
    private static final String PATH_FOR_ADD_USER_SQL_FILE
            = "classpath:database/add-user.sql";
    private static final String PATH_REMOVE_ALL_USERS_SQL_FILE
            = "classpath:database/delete-users.sql";
    private static final String ENDPOINT_WITH_ID = "/api/admin/3";
    private static final String ENDPOINT_WITHOUT_ID = "/api/admin";
    private static final int EXPECTED_LENGTH = 1;

    private static MockMvc mockMvc;

    @MockBean
    private TelegramClient telegramClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @WithUserDetails("admin@mail.com")
    @Test
    @DisplayName("Test getting all users")
    @Sql(scripts = PATH_FOR_ADD_USER_SQL_FILE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_REMOVE_ALL_USERS_SQL_FILE,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllUsers_WithDefaultPage_ReturnsOk() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(3L);
        userDto.setEmail("user1@mail.com");
        userDto.setFirstName("TestUserName1");
        userDto.setLastName("TestUserLastName1");
        List<UserDto> expected = new ArrayList<>();
        expected.add(userDto);

        MvcResult result = mockMvc.perform(get(ENDPOINT_WITHOUT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), UserDto[].class);
        assertEquals(EXPECTED_LENGTH, actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @WithUserDetails("admin@mail.com")
    @Test
    @DisplayName("Test for deleting user by ID")
    @Sql(scripts = PATH_FOR_ADD_USER_SQL_FILE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteUserById_NoContentStatus() throws Exception {
        mockMvc.perform(delete(ENDPOINT_WITH_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
