package com.urantech.restapi.controller;

import static com.urantech.restapi.entity.user.UserAuthority.Authority.USER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urantech.restapi.entity.user.UserAuthority;
import com.urantech.restapi.rest.user.RegistrationRequest;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    private final UserAuthority authority = new UserAuthority();
    private final RegistrationRequest req = new RegistrationRequest("test@email.com", "testPass");

    @BeforeEach
    void setUp() {
        authority.setAuthority(USER);

        jdbcTemplate.execute("truncate table users, user_authority, task restart identity cascade");

        when(kafkaTemplate.send(anyString(), anyString())).thenReturn(new CompletableFuture<>());
    }

    @Test
    void shouldRegisterUser() throws Exception {
        // given
        var requestBuilder =
                MockMvcRequestBuilders.post("/api/users/register")
                        .with(user("j.dewar").authorities(authority))
                        .content(mapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isOk());
    }

    @Test
    void givenDuplicateUser_whenRegisterUser_thenReturnConflict() throws Exception {
        // given
        jdbcTemplate.execute(
                "insert into users (id, email, password, enabled) "
                        + "values (12345, 'test@email.com', 'testPass', true)");
        var requestBuilder =
                MockMvcRequestBuilders.post("/api/users/register")
                        .with(user("j.dewar").authorities(authority))
                        .content(mapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isConflict());
    }
}
