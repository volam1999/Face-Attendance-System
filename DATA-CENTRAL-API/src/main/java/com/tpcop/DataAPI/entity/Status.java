package com.tpcop.DataAPI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_STATUS")
public class Status {

	@Id
	@Column(name = "UID", nullable = false)
	private Long uid;

	/*
	 * 1 in -1 out
	 */
	private int status = 1;
}
