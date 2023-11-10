package com.mingguang.dreambuilder.service;

import com.mingguang.dreambuilder.dao.TaskDao;
import com.mingguang.dreambuilder.dao.TaskTagDao;
import com.mingguang.dreambuilder.entity.Task;
import com.mingguang.dreambuilder.entity.TaskTag;
import com.mingguang.dreambuilder.entity.User;
import com.mingguang.dreambuilder.util.NetworkTimeUtil;
import com.mingguang.dreambuilder.util.Utils;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Resource
    private TaskDao taskDao;
    @Resource
    private TaskTagDao taskTagDao;

    public Page<Task> getAllTask(Pageable pageable) {
        return taskDao.findAllByIsValidate(pageable);
    }

    //求多个page的交集
    public Page<Task> intersectPages(List<Page<Task>> pageList, Pageable pageable) {
        if (pageList.isEmpty()) {
            return Page.empty(pageable);
        }
        List<List<Task>> pageContentList = pageList.stream().map(Page::getContent).collect(Collectors.toList());
        List<Task> intersectedList = pageContentList.get(0).stream()
                .filter(task -> pageContentList.stream().allMatch(pageContent -> pageContent.contains(task)))
                .collect(Collectors.toList());
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();
        int start = Math.min(pageNumber * pageSize, intersectedList.size());
        int end = Math.min(start + pageSize, intersectedList.size());
        return new PageImpl<>(intersectedList.subList(start, end), pageable, intersectedList.size());
    }

    //查询主方法
    public Page<Task> getTaskByMixedFilter(List<String> tagName,
                                           String content,
                                           int minPoint,
                                           int maxPoint,
                                           boolean timeFilter,
                                           Pageable pageable){
        Page<Task> resultPage;
        List<Page<Task>> mergedList = new ArrayList<>();
        if (tagName != null){
            resultPage = getTaskByTag(tagName, pageable);
            if (resultPage!=null) mergedList.add(resultPage);
                else return Page.empty(pageable);
        }
        if (content != null){
            resultPage = getTaskByContent(content, pageable);
            if (resultPage!=null) mergedList.add(resultPage);
                else return Page.empty(pageable);
        }
        if (minPoint != 0 && maxPoint != 0){
            resultPage = getPointInRangeTask(minPoint, maxPoint, pageable);
            if (resultPage!=null) mergedList.add(resultPage);
                else return Page.empty(pageable);
        }
        if (timeFilter){
            resultPage = getInTimeTask(pageable);
            if (resultPage!=null) mergedList.add(resultPage);
                else return Page.empty(pageable);
        }
        if (mergedList.isEmpty()){
            return taskDao.findAllByIsValidate(pageable);
        }
        resultPage = intersectPages(mergedList, pageable);
        return resultPage;
    }

    public Page<Task> getInTimeTask(Pageable pageable) {
        Timestamp nowTime = NetworkTimeUtil.getNetworkTime();
        return taskDao.findByValidateDate(nowTime, pageable);
    }

    public Page<Task> getPointInRangeTask(int minPoint, int maxPoint, Pageable pageable) {
        return taskDao.findByPointRange(minPoint, maxPoint, pageable);
    }

    public Page<Task> getTaskByContent(String content, Pageable pageable) {
        return taskDao.findByContent(content, pageable);
    }

    public Page<Task> getTaskByTag(List<String> tagName, Pageable pageable){
        assert false;
        List<Page<Task>> mergedList = null;
        for (String i:tagName) {
            mergedList.add(taskTagDao.findByTagNameContaining(i, pageable).map(TaskTag::getTask));
        }
        return intersectPages(mergedList, pageable);
    }
}