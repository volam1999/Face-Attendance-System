package com.tpcop.DataAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpcop.DataAPI.entity.Holiday;

public interface IHolidayRepository extends JpaRepository<Holiday, Long> {

	Holiday findByUid(Long uid);

}
