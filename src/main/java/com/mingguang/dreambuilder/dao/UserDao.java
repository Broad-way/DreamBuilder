package com.mingguang.dreambuilder.dao;

import com.mingguang.dreambuilder.entity.User;
import io.swagger.annotations.ApiModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@ApiModel(description = "Methods")
@RepositoryRestResource
public interface UserDao extends PagingAndSortingRepository<User,Long>, CrudRepository<User,Long> {
    User findByAccountAndEnabledTrue(String account);

    User findByAccount(String account);

    User findUserByWxId(String wx_id);

}
