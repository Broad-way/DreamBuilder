package com.mingguang.dreambuilder.dao;

import com.mingguang.dreambuilder.entity.Task;
import com.mingguang.dreambuilder.entity.TaskTag;
import com.mingguang.dreambuilder.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@ApiModel(description = "Methods")
@RepositoryRestResource
public interface TaskTagDao extends PagingAndSortingRepository<TaskTag,Long>, CrudRepository<TaskTag,Long> {
    @Query("SELECT t FROM TaskTag t WHERE t.isValidate = true AND t.tagName LIKE %:tagName%")
    Page<TaskTag> findByTagNameContaining(String tagName, Pageable pageable);

    @NonNull
    Optional<TaskTag> findById(@NonNull Long id);

    @Query("SELECT t FROM TaskTag t WHERE t.isValidate = true AND t.tagName = :recommended")
    Page<TaskTag> findAllTaskTagRecommended(String recommended, Pageable pageable);

    @Query("SELECT t FROM TaskTag t WHERE t.isValidate = true AND t.tagName = :recommended AND t.visibleUser = :id")
    Page<TaskTag> findTaskTagRecommendedById(String recommended, Long id, Pageable pageable);

    boolean existsByTagNameAndTask(String tagName, Task task);

    @Modifying
    @Query("UPDATE TaskTag t SET t.isValidate = false WHERE t.task = :task " +
           "AND t.tagName = :tagName AND t.visibleUser = :user")
    void deleteByTaskAndTagNameAndVisibleUser(Task task, String tagName, User user);
}
