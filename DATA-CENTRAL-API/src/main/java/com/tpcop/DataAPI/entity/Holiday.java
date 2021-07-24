package com.tpcop.DataAPI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TBL_HOLIDAY")
public class Holiday {
	
	@Id
	@Column(name = "UID", nullable = false, unique = true)
	private Long uid;
	
	/*
	 * day format XX:YY
	 * XX: start leave day
	 * YY: num of day
	 */
	private String numOfDays;
}
