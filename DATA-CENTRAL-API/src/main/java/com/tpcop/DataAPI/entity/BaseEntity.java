package com.tpcop.DataAPI.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private long id;

	@Column(name = "createdDate", nullable = false)
	private Date createdDate = new Date();

	private String createdBy = "SYSTEM";

	@Column(name = "modifiedDate")
	private Date modifiedDate = new Date();

	private String modifiedBy = "SYSTEM";

}
