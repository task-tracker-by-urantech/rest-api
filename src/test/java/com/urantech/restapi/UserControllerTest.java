package com.urantech.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urantech.restapi.entity.user.UserAuthority;
import com.urantech.restapi.rest.user.RegistrationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.urantech.restapi.entity.user.UserAuthority.Authority.USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final UserAuthority authority = new UserAuthority();
    private final RegistrationRequest req = new RegistrationRequest("test@email.com", "testPass");

    @BeforeEach
    void setUp() {
        authority.setAuthority(USER);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("delete from user_authority where user_id in " +
                "(select id from users where id = 12345 or email = 'test@email.com')");
        jdbcTemplate.execute("delete from users where id = 12345 or email = 'test@email.com'");
    }

    @Test
    void shouldRegisterUser() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders
                .post("/api/users/register")
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
        jdbcTemplate.execute("insert into users (id, email, password, enabled) " +
                "values (12345, 'test@email.com', 'testPass', true)");
        var requestBuilder = MockMvcRequestBuilders
                .post("/api/users/register")
                .with(user("j.dewar").authorities(authority))
                .content(mapper.writeValueAsString(req))
                .contentType(MediaType.APPLICATION_JSON);

        // when
        mockMvc.perform(requestBuilder)
                // then
                .andExpect(status().isConflict());
    }
}
