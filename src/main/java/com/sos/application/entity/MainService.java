package com.sos.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class MainService {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private ServiceCategory serviceCategory;

    @OneToMany(mappedBy = "mainService")
    private List<SubService> subServices;

    @Override
    public String toString() {
        return "MainService{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
