package com.mingguang.dreambuilder.dao;

import com.mingguang.dreambuilder.entity.Child;
import io.swagger.annotations.ApiModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@ApiModel(description = "Methods")
@RepositoryRestResource
public interface ChildDao extends CrudRepository<Child, Long> {
    @Query("SELECT t FROM Child t WHERE t.isValidate = true AND t.id = :id")
    Child findChildById(@Param("id") Long id);

    @Query("SELECT t FROM Child t WHERE t.isValidate = true AND t.name = :name")
    Child findByName(String name);

    boolean existsByName(String name);
}