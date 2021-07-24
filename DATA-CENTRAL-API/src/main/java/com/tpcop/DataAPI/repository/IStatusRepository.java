package com.tpcop.DataAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tpcop.DataAPI.entity.Status;

public interface IStatusRepository extends JpaRepository<Status, Long> {

	public Status findByUid(Long uid);
}
