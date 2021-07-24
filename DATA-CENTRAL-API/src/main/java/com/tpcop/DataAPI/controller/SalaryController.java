package com.tpcop.DataAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tpcop.DataAPI.entity.BaseSalary;
import com.tpcop.DataAPI.service.BaseSalaryServiceIml;

@RestController
@RequestMapping("/salary")
public class SalaryController {

	@Autowired
	private BaseSalaryServiceIml salaryService;

	@PostMapping("/add")
	public BaseSalary createDefaultSalary(@RequestBody BaseSalary salary) {
		return salaryService.create();
	}
}
