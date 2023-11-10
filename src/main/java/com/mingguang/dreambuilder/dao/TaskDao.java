package com.mingguang.dreambuilder.dao;

import com.mingguang.dreambuilder.entity.Task;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.Timestamp;
import java.util.Optional;

@RepositoryRestResource
public interface TaskDao extends PagingAndSortingRepository<Task, Long>, CrudRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.isValidate = true")
    Page<Task> findAllByIsValidate(Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.isValidate = true " +
            "AND (t.name LIKE %:content% OR t.description LIKE %:content%)")
    Page<Task> findByContent(@Param("content") String content,
                             Pageable pageable);

    //not used in Service
    @NonNull
    @Query("SELECT t FROM Task t WHERE t.isValidate = true AND t.id = :id")
    Optional<Task> findById(@NonNull @Param("id") Long id);

    @Query("SELECT t FROM Task t WHERE t.isValidate = true AND t.validateFrom <= :nowTime " +
            "AND t.validateUntil >= :nowTime")
    Page<Task> findByValidateDate(@Param("nowTime") Timestamp nowTime, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.isValidate = true AND t.point >= :minPoint AND t.point <= :maxPoint")
    Page<Task> findByPointRange(@Param("minPoint") int minPoint,
                                @Param("maxPoint") int maxPoint,
                                Pageable pageable);

    boolean existsByName(String name);
}