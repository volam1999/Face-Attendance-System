package com.tpcop.DataAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpcop.DataAPI.entity.TimeIn;

public interface ITimeInRepository extends JpaRepository<TimeIn, Long> {
	public TimeIn findByUid(Long uid);
}
