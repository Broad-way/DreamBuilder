package com.mingguang.dreambuilder.service;

import com.mingguang.dreambuilder.dao.TaskDao;
import com.mingguang.dreambuilder.dao.TaskTagDao;
import com.mingguang.dreambuilder.entity.Task;
import com.mingguang.dreambuilder.entity.TaskTag;
import com.mingguang.dreambuilder.entity.User;
import com.mingguang.dreambuilder.util.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskRecommendedService {
    @Resource
    private TaskTagDao taskTagDao;
    @Resource
    private TaskDao taskDao;

    private final String recommended = "推荐";

    public Page<Task> getAllTaskRecommended(Pageable pageable){
        return taskTagDao.findAllTaskTagRecommended(recommended, pageable).map(TaskTag::getTask);
    }

    public Page<Task> getTaskRecommendedById(Pageable pageable){
        User user = Utils.getUser();
        long defaultId = 1L;
        Long userId = (user == null)? defaultId : user.getId();
        return taskTagDao.findTaskTagRecommendedById(recommended, userId, pageable).map(TaskTag::getTask);
    }

    public void deleteTaskRecommended(Long taskId, String tagName){
        Task task = taskDao.findTaskById(taskId);
        taskTagDao.deleteByTaskAndTagNameAndVisibleUser(task, tagName, Utils.getUser());
    }
}
