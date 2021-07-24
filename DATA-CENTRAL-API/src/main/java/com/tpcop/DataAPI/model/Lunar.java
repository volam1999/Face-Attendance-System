package com.tpcop.DataAPI.model;

public class Lunar {
	public boolean isleap;
	public int lunarDay;
	public int lunarMonth;
	public int lunarYear;

	public int getLunarDay() {
		return lunarDay;
	}

	public void setLunarDay(int lunarDay) {
		this.lunarDay = lunarDay;
	}

	public int getLunarMonth() {
		return lunarMonth;
	}

	public void setLunarMonth(int lunarMonth) {
		this.lunarMonth = lunarMonth;
	}

	public int getLunarYear() {
		return lunarYear;
	}

	public void setLunarYear(int lunarYear) {
		this.lunarYear = lunarYear;
	}
}