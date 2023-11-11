package com.mingguang.dreambuilder.dao;

import com.mingguang.dreambuilder.entity.Child;
import com.mingguang.dreambuilder.entity.Task;
import com.mingguang.dreambuilder.entity.TaskComplete;
import io.swagger.annotations.ApiModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@ApiModel(description = "Methods")
@RepositoryRestResource
public interface TaskCompleteDao extends PagingAndSortingRepository<TaskComplete, Long>, CrudRepository<TaskComplete, Long> {
    @Query("SELECT t FROM TaskComplete t WHERE t.isValidate = true")
    Page<TaskComplete> findAllByIsValidate(Pageable pageable);

    @Query("SELECT t FROM TaskComplete t WHERE t.isValidate = true " +
            "AND t.volunteerId = :volunteerId")
    Page<TaskComplete> findByVolunteerId(Long volunteerId, Pageable pageable);

    @Query("SELECT t FROM TaskComplete t WHERE t.isValidate = true " +
            "AND t.volunteerId = :volunteerId AND (t.passed = true OR t.passed = false )")
    Page<TaskComplete> findByVolunteerIdAndPassed(Long volunteerId, Pageable pageable);

    @Query("SELECT t FROM TaskComplete t WHERE t.isValidate = true " +
            "AND t.volunteerId = :volunteerId AND t.passed is NULL")
    Page<TaskComplete> findByVolunteerIdAndNotPassed(Long volunteerId, Pageable pageable);

    boolean existsByTask(Task task);
}
