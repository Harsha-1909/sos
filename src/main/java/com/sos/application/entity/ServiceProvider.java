package com.sos.application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, length = 10)
    private String phoneNumber;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdTime;

    @Column(unique = true)
    private String aadhaarPath;

    private Boolean hasVerified;

    @ManyToMany(mappedBy = "serviceProviders")
    private List<Zone> zones;

    @ManyToMany
    private List<SubService> subServices;

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdTime=" + createdTime +
                ", aadhaarPath='" + aadhaarPath + '\'' +
                ", hasVerified=" + hasVerified +
                '}';
    }

}
