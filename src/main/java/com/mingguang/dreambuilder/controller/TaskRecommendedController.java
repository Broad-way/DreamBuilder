package com.mingguang.dreambuilder.controller;

import com.mingguang.dreambuilder.entity.Task;
import com.mingguang.dreambuilder.service.TaskRecommendedService;
import com.mingguang.dreambuilder.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@Api(tags = "推荐任务控制类")
public class TaskRecommendedController {
    @Autowired
    private TaskRecommendedService taskRecommendedService;

    @GetMapping("/all_user")
    @ApiOperation(value = "获取所有用户的所有推荐任务", notes = "测试方法，返回所有用户的所有有效任务")
    public Page<Task> getAllTaskRecommended(@ApiParam(name = "pageable",value = "分页信息，只需要传入两个参数  page:需要获取的页号  size:以何大小进行分页") Pageable pageable) {
        return taskRecommendedService.getAllTaskRecommended(pageable);
    }

    @GetMapping("/all")
    @ApiOperation(value = "获取当前用户的所有推荐任务", notes = "返回当前登录的用户的所有有效任务（后端能够获取当前登录用户的id）")
    public Page<Task> getAllTaskRecommendedById(@ApiParam(name = "pageable",value = "分页信息，只需要传入两个参数  page:需要获取的页号  size:以何大小进行分页") Pageable pageable) {
        return taskRecommendedService.getTaskRecommendedById(pageable);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "根据tagName删除推荐任务", notes = "根据标签名删除推荐任务")
    public void deleteTaskRecommendedByTagName(@ApiParam(name = "id", value = "任务id") @RequestParam("id") Long taskID) {
        taskRecommendedService.deleteTaskRecommended(taskID,"推荐");
    }

}