package com.mingguang.dreambuilder.controller;

import com.mingguang.dreambuilder.entity.TaskComplete;
import com.mingguang.dreambuilder.service.TaskCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
public class TaskCompleteController {
    @Autowired
    private TaskCompleteService taskCompleteService;

    @GetMapping("/all_user")
    public Page<TaskComplete> getAllUserTaskComplete(Pageable pageable){
        return taskCompleteService.getAllTaskComplete(pageable);
    }

    @GetMapping("/all")
    public Page<TaskComplete> getAllTaskComplete(@RequestParam("id") Long id, Pageable pageable){
        return taskCompleteService.getByVolunteerId(id, pageable);
    }

    @GetMapping("/undone")
    public Page<TaskComplete> getUndoneTaskComplete(@RequestParam("id") Long id, Pageable pageable){
        return taskCompleteService.getByVolunteerIdAndNotPassed(id, pageable);
    }

    @GetMapping("/done")
    public Page<TaskComplete> getDoneTaskComplete(@RequestParam("id") Long id, Pageable pageable){
        return taskCompleteService.getByVolunteerIdAndPassed(id, pageable);
    }
}
