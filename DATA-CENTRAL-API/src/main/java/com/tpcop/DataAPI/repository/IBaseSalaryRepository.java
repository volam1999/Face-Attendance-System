package com.tpcop.DataAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpcop.DataAPI.entity.BaseSalary;

public interface IBaseSalaryRepository extends JpaRepository<BaseSalary, Long> {
	BaseSalary findById(long id);
}
