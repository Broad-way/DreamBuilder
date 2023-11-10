package com.mingguang.dreambuilder.auto;


import com.mingguang.dreambuilder.dao.*;
import com.mingguang.dreambuilder.entity.*;
import com.mingguang.dreambuilder.entity.enums.Role;
import com.mingguang.dreambuilder.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

// run方法自动运行，所有的create*****Bean方法不会创建重复的实例
// 保存至数据库内的所有entity的id的值由jpa自动生成，生成规则：从1开始，每次累加1
@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskTagDao taskTagDao;
    @Autowired
    private TaskCompleteDao taskCompleteDao;
    @Autowired
    private ChildDao childDao;
    @Autowired
    private VolunteerDao volunteerDao;

    private User admin = new User();
    private Child child = new Child();
    private Volunteer volunteer = new Volunteer();

    public MyApplicationRunner(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createTaskBean(String name, String description, int point, int year1, int year2){
        if (!taskDao.existsByName(name)) {
            Task task = new Task();
            task.setName(name);
            task.setDescription(description);
            task.setPoint(point);
            task.setValidateFrom(Timestamp.valueOf(LocalDateTime.of(year1, 1, 1, 0, 0, 0, 0)));
            task.setValidateUntil(Timestamp.valueOf(LocalDateTime.of(year2, 1, 1, 0, 0, 0, 0)));
            taskDao.save(task);
        }
    }

    public void createTaskTagBean(Long taskId, String tagName){
        Task task = taskDao.findTaskById(taskId);
        if (!taskTagDao.existsByTagNameAndTask(tagName, task)) {
            TaskTag taskTag = new TaskTag();
            taskTag.setTask(task);
            taskTag.setTagName(tagName);
            if (tagName.equals("推荐")) taskTag.setVisibleUser(admin);
            else taskTag.setVisibleUser(null);
            taskTagDao.save(taskTag);
        }
    }

    public void createTaskCompleteBean(Long taskId){
        Task task = taskDao.findTaskById(taskId);
        if (!taskCompleteDao.existsByChildAndTaskAndVolunteerId(child, task, volunteer.getId())){
            TaskComplete taskComplete = new TaskComplete();
            taskComplete.setTask(task);
            taskComplete.setChild(child);
            taskComplete.setVolunteerId(volunteer.getId());
            taskCompleteDao.save(taskComplete);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        admin = userDao.findByAccount("admin");
        if (admin == null){
            User user = new User();
            user.setAccount("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setRole(Role.ADMIN);

            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            admin = userDao.save(user);
        }
//        System.out.println("Prepared user:");
//        System.out.println("\t account: "+admin.getAccount());
//        System.out.println("\t password: "+"admin");
//        System.out.println("\t encodePwd: "+admin.getPassword());
//        System.out.println("\t serial: "+ admin);

        System.out.println("User has loaded\n");

        final String childName = "kids";
        final String volunteerName = "helper";

        child = childDao.findByName(childName);
        volunteer = volunteerDao.findByName(volunteerName);
        if (child == null || volunteer == null){
            Child tempChild = new Child();
            Volunteer tempVolunteer = new Volunteer();
            tempChild.setName(childName);
            tempVolunteer.setName(volunteerName);
            tempChild.setVolunteer(tempVolunteer);
            tempVolunteer.setChild(tempChild);
            child = childDao.save(tempChild);
            volunteer = volunteerDao.save(tempVolunteer);
        }

        System.out.println("Child and Volunteer have loaded\n");

        createTaskBean("task-1", "description-1", 3, 2022, 2024);
        createTaskBean("task-2", "description-2", 5, 2022, 2024);
        createTaskBean("task-3", "description-3 temp", 7, 2025, 2026);
        createTaskBean("task-4", "description-4 temp", 9, 2025, 2026);
        createTaskBean("task-5", "It is a useless sentence for vague matching", 4, 2013, 2015);
        createTaskBean("task-6", "It is also a useless sentence for vague matching", 6, 2013, 2015);

        System.out.println("Tasks have loaded\n");

        createTaskTagBean(1L, "语文");
        createTaskTagBean(2L, "数学");
        createTaskTagBean(3L, "推荐");
        createTaskTagBean(4L, "推荐");
        createTaskTagBean(5L, "英语");
        createTaskTagBean(5L, "推荐");

        createTaskTagBean(1L, "必做");
        createTaskTagBean(2L, "必做");
        createTaskTagBean(3L, "选做");
        createTaskTagBean(4L, "选做");
        createTaskTagBean(5L, "选做");
        createTaskTagBean(6L, "必做");

        System.out.println("TaskTags have loaded\n");

        //createTaskCompleteBean(2L);
        //createTaskCompleteBean(3L);

        System.out.println("TaskComplete have loaded\n");
    }

}
