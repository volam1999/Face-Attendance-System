package com.tpcop.DataAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpcop.DataAPI.entity.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Long> {

}
