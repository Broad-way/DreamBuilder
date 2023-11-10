package com.mingguang.dreambuilder.controller;

import com.mingguang.dreambuilder.entity.TaskComplete;
import com.mingguang.dreambuilder.service.TaskCompleteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/check")
@Api(tags = "批改任务控制类")
public class TaskCompleteController {
    @Autowired
    private TaskCompleteService taskCompleteService;

    @GetMapping("/all_user")
    @ApiOperation(value = "获取所有志愿者的所有批改任务", notes = "测试方法，返回所有有效的批改任务，无论任务属于哪个志愿者")
    public Page<TaskComplete> getAllUserTaskComplete(@ApiParam(name = "pageable",value = "分页信息，只需要传入两个参数  page:需要获取的页号  size:以何大小进行分页") Pageable pageable){
        return taskCompleteService.getAllTaskComplete(pageable);
    }

    @GetMapping("/all")
    @ApiOperation(value = "获取某志愿者的所有批改任务", notes = "返回属于某志愿者的所有有效的批改任务")
    public Page<TaskComplete> getAllTaskComplete(@ApiParam(name = "id", value = "志愿者Id", required = true) @RequestParam("id") Long volunteerId,
                                                 @ApiParam(name = "pageable",value = "分页信息，只需要传入两个参数  page:需要获取的页号  size:以何大小进行分页") Pageable pageable){
        return taskCompleteService.getByVolunteerId(volunteerId, pageable);
    }

    @GetMapping("/undone")
    @ApiOperation(value = "获取某志愿者的所有未完成批改任务", notes = "返回属于某志愿者的所有有效的未完成批改任务")
    public Page<TaskComplete> getUndoneTaskComplete(@ApiParam(name = "id", value = "志愿者Id", required = true) @RequestParam("id") Long volunteerId,
                                                    @ApiParam(name = "pageable",value = "分页信息，只需要传入两个参数  page:需要获取的页号  size:以何大小进行分页") Pageable pageable){
        return taskCompleteService.getByVolunteerIdAndNotPassed(volunteerId, pageable);
    }

    @GetMapping("/done")
    @ApiOperation(value = "获取某志愿者的所有已完成批改任务", notes = "返回属于某志愿者的所有有效的已完成批改任务")
    public Page<TaskComplete> getDoneTaskComplete(@ApiParam(name = "id", value = "志愿者Id", required = true) @RequestParam("id") Long volunteerId,
                                                  @ApiParam(name = "pageable",value = "分页信息，只需要传入两个参数  page:需要获取的页号  size:以何大小进行分页") Pageable pageable){
        return taskCompleteService.getByVolunteerIdAndPassed(volunteerId, pageable);
    }
}
