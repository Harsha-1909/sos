package com.sos.application.repository;

import com.sos.application.entity.MainService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainServiceRepository extends JpaRepository<MainService, Long> {

    @Query("SELECT ms FROM MainService ms WHERE ms.name = :name AND ms.serviceCategory.id = :serviceCategoryId")
    Optional<MainService> findMainServiceByNameAndServiceCategoryId(@Param("name") String name, @Param("serviceCategoryId") Long serviceCategoryId);
}
