package com.tpcop.DataAPI.service;

import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpcop.DataAPI.entity.BaseSalary;
import com.tpcop.DataAPI.entity.CalSalary;
import com.tpcop.DataAPI.entity.Log;
import com.tpcop.DataAPI.entity.Status;
import com.tpcop.DataAPI.entity.TimeIn;
import com.tpcop.DataAPI.entity.TimeOut;
import com.tpcop.DataAPI.entity.User;
import com.tpcop.DataAPI.repository.IBaseSalaryRepository;
import com.tpcop.DataAPI.repository.ICalSalaryRepository;
import com.tpcop.DataAPI.repository.ILogRepository;
import com.tpcop.DataAPI.repository.IStatusRepository;
import com.tpcop.DataAPI.repository.ITimeInRepository;
import com.tpcop.DataAPI.repository.ITimeOutRepository;
import com.tpcop.DataAPI.repository.IUserRepository;

@Service("logService")
public class LogServiceIml implements LogService {

	@Autowired
	private ILogRepository iLogRepository;

	@Autowired
	private IBaseSalaryRepository iBaseSalaryRepository;

	@Autowired
	private ICalSalaryRepository iCalSalaryRepository;

	@Autowired
	private ITimeInRepository iTimeInRepository;

	@Autowired
	private ITimeOutRepository iTimeOutRepository;

	@Autowired
	private IStatusRepository iStatusRepository;

	@Autowired
	private IUserRepository iUserRepository;

	private Calendar getCalendarByStringHours(String hours) {
		String[] parts = hours.split(":");
		Calendar lateTime = Calendar.getInstance();
		lateTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
		lateTime.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
		lateTime.set(Calendar.SECOND, 0);
		return lateTime;
	}

	@Override
	public String checkIn(Long uid) {
//		// TODO Auto-generated method stub
		User user = iUserRepository.getOne(uid);
		if (user == null) {
			return null;
		}

		Boolean isCheckInTheSameMonth;
		Boolean isUserChecked = false;
		int lastCheckDate, lastCheckMonth;

		// handle status and loging
		iStatusRepository.save(new Status(uid, 1));
		iLogRepository.save(new Log((long) 0, uid, new Date()));
		iBaseSalaryRepository.save(new BaseSalary((long) 1, (double) 2500000));

		Calendar calendarNow = Calendar.getInstance();
		// default 8:15 user must be check in or will be late
		Calendar calendarAt815 = getCalendarByStringHours("08:15");

		// get current date and month
		int curMonth = calendarNow.get(Calendar.MONTH);
		int curDate = calendarNow.get(Calendar.DATE);

		// check for the last time user out
		TimeOut timeUserOut = iTimeOutRepository.findByUid(uid);
		if (timeUserOut != null) {
			calendarNow.setTime(timeUserOut.getTimeOut());
			lastCheckDate = calendarNow.get(Calendar.DATE);
			lastCheckMonth = calendarNow.get(Calendar.MONTH);
		} else {
			lastCheckDate = -1;
			lastCheckMonth = -1;
		}

		// innit the value
		isCheckInTheSameMonth = (curMonth == lastCheckMonth) ? true : false;
		isUserChecked = (curDate == lastCheckDate) ? true : false;

		// next month
		if (!isCheckInTheSameMonth) {
			iCalSalaryRepository.save(new CalSalary(uid, (double) 5, 0, 0, 0));
		}

		// handle TimeIn Record table
		TimeIn userTimeIn = iTimeInRepository.findByUid(uid);
		if (userTimeIn == null) {
			userTimeIn = new TimeIn(uid, new Date(), curDate + "");
		} else {
			if (!isUserChecked) {
				userTimeIn.setDayAtWorks(userTimeIn.getDayAtWorks() + "-" + curDate);
			}
			userTimeIn.setTimeIn(new Date());
		}
		iTimeInRepository.save(userTimeIn);

		// handle calSalary such as late day hourwork
		CalSalary calSalary = iCalSalaryRepository.findByUid(uid);
		if (!isUserChecked) {
			if (calendarNow.after(calendarAt815)) {
				// employ go late
				if (calSalary == null) {
					iCalSalaryRepository.save(new CalSalary(uid, (double) 5, 0, 1, 1));
				} else {
					calSalary.setTotalLateDay(calSalary.getTotalLateDay() + 1);
					calSalary.setToltalDayInWork(calSalary.getToltalDayInWork() + 1);
					iCalSalaryRepository.save(calSalary);
				}

				return "user " + uid + " go late";
			} else {
				// employ go in time
				if (calSalary == null) {
					iCalSalaryRepository.save(new CalSalary(uid, (double) 5, 0, 1, 0));
				} else {
					calSalary.setToltalDayInWork(calSalary.getToltalDayInWork() + 1);
					iCalSalaryRepository.save(calSalary);
				}
				return "user " + uid + " go in time";
			}
		}
		return "user " + uid + " go inside";
	}

	@Override
	public String checkOut(Long uid) {
		// TODO Auto-generated method stub
		User user = iUserRepository.getOne(uid);
		if (user == null) {
			return null;
		}

		// save time user go out of office in database
		TimeOut out = new TimeOut(uid, new Date());
		iTimeOutRepository.save(out);

		// change status of user equal -1 mean out of office
		Status status = new Status(uid, -1);
		iStatusRepository.save(status);

		// write log
		iLogRepository.save(new Log((long) 0, uid, new Date()));

		// cal hours at work
		TimeIn in = iTimeInRepository.findByUid(uid);

		if (in == null) {
			return null;
		}

		// cal daily hours at work
		Date timeIn = in.getTimeIn();
		Date timeOut = new Date();

		long secs = (timeOut.getTime() - timeIn.getTime()) / 1000;
		int hours = (int) (secs / 3600);
		secs = secs % 3600;
		int mins = (int) (secs / 60) + hours * 60;

		CalSalary calSalary = iCalSalaryRepository.findByUid(uid);
		if (calSalary == null) {
			calSalary = new CalSalary(uid, 5, 0, 1,0);
		} else {
			calSalary.setDailyMinuteWork(mins + calSalary.getDailyMinuteWork());
		}
		iCalSalaryRepository.save(calSalary);

		return "user " + uid + " go out of office";
	}

}
