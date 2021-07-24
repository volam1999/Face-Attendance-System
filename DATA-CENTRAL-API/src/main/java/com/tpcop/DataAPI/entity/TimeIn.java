package com.tpcop.DataAPI.entity;

import java.util.Date;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TBL_TIME_IN")
public class TimeIn {
	/*
	 * each employee will have only one record time in
	 */
	@Id
	@Column(name = "UID", nullable = false)
	private Long uid;

	private Date timeIn = new Date();

	private String dayAtWorks = "";
}
