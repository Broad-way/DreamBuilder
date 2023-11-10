package com.mingguang.dreambuilder.auto;


import com.mingguang.dreambuilder.dao.TaskDao;
import com.mingguang.dreambuilder.dao.TaskTagDao;
import com.mingguang.dreambuilder.dao.UserDao;
import com.mingguang.dreambuilder.entity.Task;
import com.mingguang.dreambuilder.entity.User;
import com.mingguang.dreambuilder.entity.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private TaskTagDao taskTagDao;

    public MyApplicationRunner(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createTaskBean(String name, String description, int point,
                               int year1, int month1, int day1,
                               int year2, int month2, int day2){
        Task task = new Task();
        task.setDescription(description);
        task.setName(name);
        task.setPoint(point);
        task.setValidateFrom(Timestamp.valueOf(LocalDateTime.of(year1, month1, day1, 0, 0, 0, 0)));
        task.setValidateUntil(Timestamp.valueOf(LocalDateTime.of(year2, month2, day2, 0, 0, 0, 0)));
        if (!taskDao.existsByName(task.getName())) taskDao.save(task);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Init Data");
        User admin = userDao.findByAccount("admin");
        if ( admin == null){
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
        System.out.println("Prepared user:");
        System.out.println("\t account: "+admin.getAccount());
        System.out.println("\t password: "+"admin");
        System.out.println("\t encodePwd: "+admin.getPassword());
        System.out.println("\t serial: "+ admin);



        System.out.println("Tasks have loaded!");


    }

}
