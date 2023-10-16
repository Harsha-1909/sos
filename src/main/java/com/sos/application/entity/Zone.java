package com.sos.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class Zone {
    @Id
    private Long id;
    private String state;
    private String district;
    private String subDistrict;
    private String area;
    @OneToMany(mappedBy = "zone")
    private List<SosUser> sosUsers;
    @ManyToMany
    private List<ServiceProvider> serviceProviders;


    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", district='" + district + '\'' +
                ", subDistrict='" + subDistrict + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
