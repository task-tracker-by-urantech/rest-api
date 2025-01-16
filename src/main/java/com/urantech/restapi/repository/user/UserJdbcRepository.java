package com.urantech.restapi.repository.user;

import com.urantech.restapi.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserJdbcRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        jdbcTemplate.update("insert into users (email, password) values (?, ?) ",
                user.getEmail(), user.getPassword());
    }
}
