package com.urantech.restapi.controller.task;

import com.urantech.restapi.entity.user.User;
import com.urantech.restapi.rest.task.TaskDto;
import com.urantech.restapi.service.task.TaskService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskDto create(@RequestBody TaskDto taskDto, @AuthenticationPrincipal User user) {
        return taskService.create(taskDto, user);
    }

    @GetMapping
    public List<TaskDto> getTasks(@AuthenticationPrincipal User user) {
        return taskService.getTasks(user);
    }

    @PatchMapping
    public TaskDto update(@RequestBody TaskDto taskDto) {
        return taskService.update(taskDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        taskService.deleteById(id);
    }
}
