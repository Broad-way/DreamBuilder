package com.mingguang.dreambuilder.controller;

import com.mingguang.dreambuilder.entity.Task;
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
@RequestMapping("/api/tasks")
@Api(tags = "任务查询控制类")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/all")
    @ApiOperation(value = "获取所有任务", notes = "测试方法，返回所有有效的任务")
    public Page<Task> getAllTasks(@ApiParam(name = "pageable",value = "分页信息，只需要传入两个参数  page:需要获取的页号  size:以何大小进行分页") Pageable pageable) {
        return taskService.getAllTask(pageable);
    }

    @GetMapping("/search")
    @ApiOperation(value = "查询任务", notes = "依照筛选条件查询任务，所有的筛选参数均不为必选参数")
    public Page<Task> getTasksByContentAndTagName(@ApiParam(name = "tagName", value = "标签名称", defaultValue = "null")
                                                      @RequestParam(value = "tagName", required = false) List<String> tagName,
                                                  @ApiParam(name = "content", value = "搜索内容", defaultValue = "null")
                                                      @RequestParam(value = "content", required = false) String content,
                                                  @ApiParam(name = "minPoint", value = "积分搜索左边界", defaultValue = "0")
                                                      @RequestParam(value = "minPoint", required = false) Integer minPoint,
                                                  @ApiParam(name = "maxPoint", value = "积分搜索右边界", defaultValue = "0")
                                                      @RequestParam(value = "maxPoint", required = false) Integer maxPoint,
                                                  @ApiParam(name = "timeFilter", value = "是否仅看正在进行任务", defaultValue = "false")
                                                      @RequestParam(value = "timeFilter", required = false) Boolean timeFilter,
                                                  @ApiParam(name = "pageable",value = "分页信息，只需要传入两个参数  page:需要获取的页号  size:以何大小进行分页")
                                                      Pageable pageable) {
        int minPointValue = minPoint != null ? minPoint : 0;
        int maxPointValue = maxPoint != null ? maxPoint : 0;
        boolean timeFilterValue = timeFilter != null ? timeFilter : false;
        return taskService.getTaskByMixedFilter(tagName, content, minPointValue, maxPointValue, timeFilterValue, pageable);
    }

}