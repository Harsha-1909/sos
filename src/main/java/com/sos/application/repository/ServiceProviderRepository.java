package com.sos.application.repository;

import com.sos.application.entity.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    @Query("SELECT sp FROM ServiceProvider sp " +
            "WHERE sp.phoneNumber = :phoneNumber")
    Optional<ServiceProvider> checkPhoneNumberExists(@Param("phoneNumber") String phoneNumber);
}
