package com.tpcop.DataAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpcop.DataAPI.entity.Log;

public interface ILogRepository extends JpaRepository<Log, Long> {

	public Log findByUid(long uid);
}
