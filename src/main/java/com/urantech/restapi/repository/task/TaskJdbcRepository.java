package com.urantech.restapi.repository.task;

import com.urantech.restapi.model.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskJdbcRepository implements TaskRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Task> findById(long id) {
        List<Task> tasks = jdbcTemplate.query("select * from task where id = ?", mapRows(), id);
        return tasks.stream().findFirst();
    }

    @Override
    public Long save(Task task) {
        return jdbcTemplate.queryForObject("insert into task (user_id, description, done) values (?, ?, ?) returning id", Long.class,
                task.getUserId(), task.getDescription(), task.isDone());
    }

    @Override
    public List<Task> findAllByUserId(long userId) {
        return jdbcTemplate.query("select * from task where user_id = ?", mapRows(), userId);
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update("delete from task where id = ?", id);
    }

    private static RowMapper<Task> mapRows() {
        return (rs, rowNum) -> {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setDescription(rs.getString("description"));
            task.setUserId(rs.getLong("user_id"));
            task.setDone(rs.getBoolean("done"));
            return task;
        };
    }
}
