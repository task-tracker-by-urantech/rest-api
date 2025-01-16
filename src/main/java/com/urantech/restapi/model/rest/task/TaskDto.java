package com.urantech.restapi.model.rest.task;

import com.urantech.restapi.model.entity.Task;

public record TaskDto(
        long id,
        String description,
        boolean done
) {
    public static TaskDto fromEntity(Task task) {
        return new TaskDto(
                task.getId(),
                task.getDescription(),
                task.isDone()
        );
    }
}
