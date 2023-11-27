package com.smarttek.chatgptbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarttek.chatgptbot.client.TelegramClient;
import com.smarttek.chatgptbot.dto.telegram.TelegramMessageDto;
import com.smarttek.chatgptbot.dto.telegram.TelegramUserDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
public class TelegramControllerTest {
    private static final String PATH_FOR_ADD_MESSAGES_SQL_FILE
            = "classpath:database/add-telegram-message.sql";
    private static final String PATH_REMOVE_MESSAGES_SQL_FILE
            = "classpath:database/delete-telegram-message.sql";

    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TelegramClient telegramClient;
    private TelegramMessageDto firstTelegramMessageDto;
    private TelegramMessageDto secondTelegramMessageDto;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        firstTelegramMessageDto = new TelegramMessageDto();
        firstTelegramMessageDto.setId(1L);
        firstTelegramMessageDto.setRequest("Test");
        firstTelegramMessageDto.setResponse("How can I help you");
        firstTelegramMessageDto.setDateTime(LocalDateTime.parse("2023-10-27T03:23:37"));

        secondTelegramMessageDto = new TelegramMessageDto();
        secondTelegramMessageDto.setId(2L);
        secondTelegramMessageDto.setRequest("Hey");
        secondTelegramMessageDto.setResponse("Hello, what's your question?");
        secondTelegramMessageDto.setDateTime(LocalDateTime.parse("2023-10-27T03:23:37"));
    }

    @WithUserDetails("admin@mail.com")
    @Test
    @DisplayName("Test get user messages by ID")
    @Sql(scripts = PATH_FOR_ADD_MESSAGES_SQL_FILE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_REMOVE_MESSAGES_SQL_FILE,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getUserMessagesById_validRequest_ReturnsList() throws Exception {
        MvcResult response = mockMvc.perform(get("/api/users/3/messages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn();
        ArrayList actualResponse =
                objectMapper.readValue(response.getResponse()
                        .getContentAsString(), ArrayList.class);
        List<TelegramMessageDto> actual = objectMapper
                .convertValue(actualResponse, new TypeReference<>() {});
        Assertions.assertEquals(List.of(firstTelegramMessageDto, secondTelegramMessageDto), actual);
    }

    @WithUserDetails("admin@mail.com")
    @Test
    @DisplayName("Test get all telegram users")
    @Sql(scripts = PATH_FOR_ADD_MESSAGES_SQL_FILE,
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = PATH_REMOVE_MESSAGES_SQL_FILE,
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllTelegramUsers_ReturnsList() throws Exception {
        TelegramUserDto userDto = new TelegramUserDto();
        userDto.setId(3L);
        userDto.setTelegramId(3L);
        List<TelegramUserDto> expected = new ArrayList<>();
        expected.add(userDto);

        MvcResult result = mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TelegramUserDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(), TelegramUserDto[].class);
        assertEquals(1, actual.length);
        assertEquals(expected, Arrays.stream(actual).toList());
    }
}
