package com.sos.application.repository;

import com.sos.application.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService, Long> {

    @Query("SELECT ss FROM SubService ss " +
            "WHERE ss.name = :name AND " +
            "ss.mainService.id = :id")
    Optional<SubService> findBySubServiceNameAndMainServiceID(@Param("name") String subServiceName, @Param("id") Long mainServiceId);
}
