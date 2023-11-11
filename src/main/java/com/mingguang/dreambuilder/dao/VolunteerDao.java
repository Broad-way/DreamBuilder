package com.mingguang.dreambuilder.dao;

import com.mingguang.dreambuilder.entity.Volunteer;
import io.swagger.annotations.ApiModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@ApiModel(description = "Methods")
@RepositoryRestResource
public interface VolunteerDao extends CrudRepository<Volunteer, Long> {
    @Query("SELECT t FROM Volunteer t WHERE t.isValidate = true AND t.name = :name")
    Volunteer findByName(String name);

    boolean existsByName(String name);
}