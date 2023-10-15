package com.sos.application.repository;

import com.sos.application.entity.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
    @Query("SELECT sc FROM ServiceCategory sc WHERE sc.name = :name")
    Optional<ServiceCategory> findServiceCategoryByName(@Param("name") String name);
}
