package com.urantech.restapi.rest.task;

import com.urantech.restapi.entity.task.Task;

public record TaskDto(Long id, String description, Boolean done) {

    public static TaskDto fromEntity(Task task) {
        return new TaskDto(task.getId(), task.getDescription(), task.isDone());
    }
}
