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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_TIME_OUT")
public class TimeOut {
	/*
	 * each employee will have only one record time in
	 */
	@Id
	@Column(name = "UID", nullable = false)
	private Long uid;

	private Date timeOut = new Date();
}
