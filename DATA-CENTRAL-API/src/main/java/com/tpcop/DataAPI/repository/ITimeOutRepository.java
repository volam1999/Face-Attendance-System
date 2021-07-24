package com.tpcop.DataAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpcop.DataAPI.entity.TimeOut;

public interface ITimeOutRepository extends JpaRepository<TimeOut, Long> {

	TimeOut findByUid(Long uid);
}
