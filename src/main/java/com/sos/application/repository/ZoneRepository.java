package com.sos.application.repository;

import com.sos.application.entity.Zone;
import com.sos.application.model.zone.request.ZoneRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    @Query("SELECT DISTINCT z.state FROM Zone z")
    List<String> getStates();

    @Query("SELECT DISTINCT z.district From Zone z WHERE z.state = :state")
    List<String> getDistricts(@Param("state") String state);

    @Query("SELECT DISTINCT z.subDistrict " +
            "FROM Zone z" +
            " WHERE z.state = :state " +
            "and z.district = :district")
    List<String> getSubDistricts(@Param("state") String state, @Param("district") String district);

    @Query("SELECT z.area " +
            "FROM Zone z " +
            "WHERE z.state = :state " +
            "and z.district = :district " +
            "and z.subDistrict = :subDistrict"
    )
    List<String> getAreas(@Param("state") String state, @Param("district") String district, @Param("subDistrict") String subDistrict);

    @Query("SELECT zone from Zone zone " +
            "WHERE zone.state = :state AND " +
            "zone.district = :district AND " +
            "zone.subDistrict = :subDistrict AND " +
            "zone.area = :area")
    Optional<Zone> getZone(@Param("state") String state, @Param("district") String district, @Param("subDistrict") String subDistrict, @Param("area") String area);
}
