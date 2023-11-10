package com.mingguang.dreambuilder.service;

import com.mingguang.dreambuilder.dao.TaskCompleteDao;
import com.mingguang.dreambuilder.entity.TaskComplete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TaskCompleteService {
    @Resource
    private TaskCompleteDao taskCompleteDao;

    public Page<TaskComplete> getAllTaskComplete(Pageable pageable){
        return taskCompleteDao.findAllByIsValidate(pageable);
    }

    public Page<TaskComplete> getByVolunteerId(Long volunteer, Pageable pageable){
        return taskCompleteDao.findByVolunteerId(volunteer, pageable);
    }

    public Page<TaskComplete> getByVolunteerIdAndPassed(Long volunteer, Pageable pageable){
        return taskCompleteDao.findByVolunteerIdAndPassed(volunteer, pageable);
    }

    public Page<TaskComplete> getByVolunteerIdAndNotPassed(Long volunteer, Pageable pageable){
        return taskCompleteDao.findByVolunteerIdAndNotPassed(volunteer, pageable);
    }
}
