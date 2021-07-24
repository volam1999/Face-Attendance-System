package com.tpcop.DataAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpcop.DataAPI.entity.CalSalary;

public interface ICalSalaryRepository extends JpaRepository<CalSalary, Long> {

	CalSalary findByUid(Long UID);
}
