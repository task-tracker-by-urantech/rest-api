package com.urantech.restapi.controller.task;

import com.urantech.restapi.model.rest.task.TaskDto;
import com.urantech.restapi.model.rest.task.TaskPayloadRequest;
import com.urantech.restapi.model.rest.task.TaskUpdateRequest;
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
    public TaskDto create(@RequestBody TaskPayloadRequest req) {
        return taskService.create(req);
    }

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskService.getTasks();
    }

    @PatchMapping
    public TaskDto update(@RequestBody TaskUpdateRequest req) {
        return taskService.update(req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        taskService.deleteById(id);
    }
}
