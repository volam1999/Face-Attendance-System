package com.tpcop.DataAPI.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpcop.DataAPI.entity.Holiday;
import com.tpcop.DataAPI.entity.Status;
import com.tpcop.DataAPI.entity.User;
import com.tpcop.DataAPI.repository.IHolidayRepository;
import com.tpcop.DataAPI.repository.IStatusRepository;
import com.tpcop.DataAPI.repository.IUserRepository;
import com.tpcop.DataAPI.service.LogServiceIml;

@RestController
@RequestMapping("/check")
public class AttendanceController {

	@Autowired
	private LogServiceIml logServiceIml;

	@Autowired
	private IHolidayRepository iHolidayRepository;

	@Autowired
	private IStatusRepository iStatusRepository;

	@Autowired
	private IUserRepository iUserRepository;

	@GetMapping("/{uid}")
	public String check(@PathVariable("uid") Long uid) {
		/*
		 * if during holiday the system will bypass the access of that employee
		 */
		try {
			User user = iUserRepository.getOne(uid);
			if (user.getStatus() == 0) {
				return "This employee has quit";
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "Not authority access detected!. Please try again!";
		}

		// check for holiday
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DATE);

		if (uid != -1) {
			Holiday holiday = iHolidayRepository.findByUid(uid);
			if (holiday != null) {
				String stringHolidayRaw = holiday.getNumOfDays();
				String[] listSplitRaw = stringHolidayRaw.split("-");
				for (String leaveDayRaw : listSplitRaw) {
					int startDay = Integer.parseInt(leaveDayRaw.split(":")[0]);
					int endDay = Integer.parseInt(leaveDayRaw.split(":")[1]);

					if (startDay >= day && day <= endDay) {
						return "User " + uid + " during holiday. Access Denied";
					}
				}
				return listSplitRaw.length + "";
			}

			Status status = iStatusRepository.findByUid(uid);

			if (status == null) {
				return logServiceIml.checkIn(uid);
			}

			if (status.getStatus() == 1) {
				return logServiceIml.checkOut(uid);
			} else {
				return logServiceIml.checkIn(uid);
			}
		}
		return logServiceIml.checkIn(uid);
	}
}
