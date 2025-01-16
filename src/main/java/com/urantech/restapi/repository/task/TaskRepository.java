package com.urantech.restapi.repository.task;

import com.urantech.restapi.model.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Optional<Task> findById(long id);

    Long save(Task task);

    List<Task> findAllByUserId(long userId);

    void deleteById(long id);
}
