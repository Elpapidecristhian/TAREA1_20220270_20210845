package com.example.tarea1_20220270_20210845.repository;

import com.example.tarea1_20220270_20210845.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, String> {
}