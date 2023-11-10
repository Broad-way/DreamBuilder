package com.mingguang.dreambuilder.controller;

import com.mingguang.dreambuilder.entity.Task;
import com.mingguang.dreambuilder.service.TaskRecommendedService;
import com.mingguang.dreambuilder.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class TaskRecommendedController {
    @Autowired
    private TaskRecommendedService taskRecommendedService;

    @GetMapping("/all_user")
    public Page<Task> getAllTaskRecommended(Pageable pageable) {
        return taskRecommendedService.getAllTaskRecommended(pageable);
    }

    @GetMapping("/all")
    public Page<Task> getAllTaskRecommendedById(Pageable pageable) {
        return taskRecommendedService.getTaskRecommendedById(pageable);
    }
}