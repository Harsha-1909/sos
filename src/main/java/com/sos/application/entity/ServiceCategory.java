package com.sos.application.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor

@Entity
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "serviceCategory", cascade = CascadeType.REMOVE)
    private List<MainService> mainServices;

    public void addMainService(MainService mainService){
        mainServices.add(mainService);
    }

    @Override
    public String toString() {
        return "ServiceCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
