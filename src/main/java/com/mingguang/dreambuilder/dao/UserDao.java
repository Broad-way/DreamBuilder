package com.mingguang.dreambuilder.dao;

import com.mingguang.dreambuilder.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserDao extends PagingAndSortingRepository<User,Long>, CrudRepository<User,Long> {
    User findByAccountAndEnabledTrue(String account);

    User findByAccount(String account);

    User findUserByWxId(String wx_id);

}
