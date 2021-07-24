package com.tpcop.DataAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tpcop.DataAPI.entity.Holiday;
import com.tpcop.DataAPI.repository.IHolidayRepository;

@RestController
public class HolidayController {

	@Autowired
	IHolidayRepository iHolidayRepository;

	@GetMapping("/holiday/leave/{uid}/{numOfDay}")
	public String registerLeaveWithSalary(@PathVariable long uid, @PathVariable String numOfDay) {
		if (!numOfDay.contains(":")) {
			return "Not Valid DayFormat";
		}

		Holiday holiday = iHolidayRepository.findByUid(uid);
		if (holiday != null) {
			for (String day : numOfDay.split(":")) {
//				if (holiday.getNumOfDays().contains(day)) {
//					return day + " is not valid. Please try again!";
//				}

				String stringHolidayRaw = holiday.getNumOfDays();
				String[] listSplitRaw = stringHolidayRaw.split("-");
				for (String leaveDayRaw : listSplitRaw) {
					int startDay = Integer.parseInt(leaveDayRaw.split(":")[0]);
					int endDay = Integer.parseInt(leaveDayRaw.split(":")[1]);
					System.out.println("Start Leave With Salary From " + startDay + " To " + endDay);
					if (startDay >= Integer.parseInt(day) && Integer.parseInt(day) <= endDay) {
						return "User " + uid + " already on holiday from date " + startDay + " to " + endDay +"\nPlease try again!";
					}
				}
			}
			holiday.setNumOfDays(holiday.getNumOfDays() + "-" + numOfDay);
			iHolidayRepository.save(holiday);
		} else {
			iHolidayRepository.save(new Holiday(uid, numOfDay));
		}
		return "Register Leave With Salary Success";
	}
}
