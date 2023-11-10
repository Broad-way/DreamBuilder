package com.mingguang.dreambuilder.controller;

import com.mingguang.dreambuilder.entity.Task;
import com.mingguang.dreambuilder.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/all")
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskService.getAllTask(pageable);
    }

    @GetMapping("/search")
    public Page<Task> getTasksByContentAndTagName(@RequestParam(value = "tagName", required = false) List<String> tagName,
                                                  @RequestParam(value = "content", required = false) String content,
                                                  @RequestParam(value = "minPoint", required = false) Integer minPoint,
                                                  @RequestParam(value = "maxPoint", required = false) Integer maxPoint,
                                                  @RequestParam(value = "timeFilter", required = false) Boolean timeFilter,
                                                  Pageable pageable) {
        int minPointValue = minPoint != null ? minPoint : 0;
        int maxPointValue = maxPoint != null ? maxPoint : 0;
        boolean timeFilterValue = timeFilter != null ? timeFilter : false;
        return taskService.getTaskByMixedFilter(tagName, content, minPointValue, maxPointValue, timeFilterValue, pageable);
    }

}