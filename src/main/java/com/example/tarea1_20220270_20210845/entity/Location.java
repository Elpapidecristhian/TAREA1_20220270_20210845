package com.example.tarea1_20220270_20210845.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @Column(name = "location_id")
    private Integer id;

    private String city;
}