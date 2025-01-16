package com.urantech.restapi.service.task;

import com.urantech.restapi.exception.TaskNotFoundException;
import com.urantech.restapi.model.entity.Task;
import com.urantech.restapi.model.rest.task.TaskDto;
import com.urantech.restapi.model.rest.task.TaskPayloadRequest;
import com.urantech.restapi.model.rest.task.TaskUpdateRequest;
import com.urantech.restapi.repository.task.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskDto create(TaskPayloadRequest req) {
        // todo: get userId from auth
        Task task = new Task(req.description(), 0L);

        long savedId = taskRepository.save(task);
        Task entity = findById(savedId);
        return TaskDto.fromEntity(entity);
    }

    public List<TaskDto> getTasks() {
        // todo: get userId from auth
        return taskRepository.findAllByUserId(0L).stream()
                .map(TaskDto::fromEntity)
                .toList();
    }

    public TaskDto update(TaskUpdateRequest req) {
        Optional<Task> taskOpt = taskRepository.findById(req.id());
        if (taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Task with id: %d not found".formatted(req.id()));
        }

        Task task = taskOpt.get();
        task.setDescription(req.description());
        if (req.done() != null ) {
            task.setDone(req.done());
        }

        long savedId = taskRepository.save(task);
        Task entity = findById(savedId);
        return TaskDto.fromEntity(entity);
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }

    private Task findById(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: %d not found".formatted(id)));
    }
}
