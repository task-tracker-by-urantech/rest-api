package com.urantech.restapi.service.task;

import com.urantech.restapi.entity.task.Task;
import com.urantech.restapi.entity.user.User;
import com.urantech.restapi.exception.TaskNotFoundException;
import com.urantech.restapi.repository.task.TaskRepository;
import com.urantech.restapi.rest.task.TaskDto;
import com.urantech.restapi.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepo;

    public TaskDto create(TaskDto taskDto, User user) {
        Task task = new Task(taskDto.description(), user);
        Task entity = taskRepo.save(task);
        return TaskDto.fromEntity(entity);
    }

    public List<TaskDto> getTasks(User user) {
        return taskRepo.findAllByUserId(user.getId()).stream()
                .map(TaskDto::fromEntity)
                .toList();
    }

    public TaskDto update(TaskDto taskDto) {
        Optional<Task> taskOpt = taskRepo.findById(taskDto.id());
        if (taskOpt.isEmpty()) {
            throw new TaskNotFoundException("Task with id: %d not found".formatted(taskDto.id()));
        }

        Task task = taskOpt.get();
        task.setDescription(taskDto.description());
        task.setDone(taskDto.done());

        Task entity = taskRepo.save(task);
        return TaskDto.fromEntity(entity);
    }

    public void deleteById(long id) {
        taskRepo.deleteById(id);
    }
}
