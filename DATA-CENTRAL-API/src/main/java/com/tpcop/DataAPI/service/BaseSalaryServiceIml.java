package com.tpcop.DataAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpcop.DataAPI.entity.BaseSalary;
import com.tpcop.DataAPI.repository.IBaseSalaryRepository;

@Service("salaryService")
public class BaseSalaryServiceIml implements BaseSalaryService {

	@Autowired
	private IBaseSalaryRepository iSalaryRepository;

	@Override
	public Double caculateSalary(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	public BaseSalary create() {
		return iSalaryRepository.save(new BaseSalary((long) 1, (double) 2500000));
	}

}
