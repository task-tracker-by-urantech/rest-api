package com.urantech.restapi.controller.task;

import com.urantech.restapi.rest.task.TaskDto;
import com.urantech.restapi.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskDto create(@RequestBody TaskDto taskDto) {
        return taskService.create(taskDto);
    }

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskService.getTasks();
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
