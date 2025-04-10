package com.example.tarea1_20220270_20210845.repository;

import com.example.tarea1_20220270_20210845.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.job.jobTitle) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.department.departmentName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(e.department.location.city) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Employee> searchEmployees(@Param("search") String search);

    boolean existsByEmail(String email);
}