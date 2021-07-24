package com.tpcop.DataAPI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "TBL_CAL_SALARY")
public class CalSalary {

	/*
	 * each user will got only one level salary
	 */
	@Id
	@Column(name = "UID", nullable = false, unique = true)
	private long uid;

	/*
	 * default level salary for each employee will be 5
	 */
	private double level = 5;

	/*
	 * total hours work per day will reset in the next day if total hour >= 6 will
	 * count 1 date other will count half a day if total hour equal <3 total DayWork
	 * will not plus
	 */
	private int dailyMinuteWork = 0;

	/*
	 * total day in work per month will reset in the first day of the next month
	 */
	private double toltalDayInWork = 0;

	/*
	 * total late day per month will reset in the first day of the next month minus
	 * 20.000 VND per late date
	 */
	private double totalLateDay = 0;
}
