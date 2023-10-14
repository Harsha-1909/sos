package com.sos.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
