package com.tpcop.DataAPI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_BASE_SALARY")
public class BaseSalary {
	
	@Id
	@Column(name = "ID", nullable = false)
	private Long id;
	
	/*
	 * default base salary for each employee is 2.500.000 VND
	 */
	private Double baseSalary = (double) 2500000;
	
}
