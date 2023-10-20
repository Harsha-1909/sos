package com.sos.application.repository;

import com.sos.application.entity.SosUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SosUserRepository extends JpaRepository<SosUser, Long> {
    @Query("SELECT user from SosUser user " +
            "WHERE user.phoneNumber = :phoneNumber")
    Optional<SosUser> isPhoneNumberExists(@Param("phoneNumber") String phoneNumber);
}
