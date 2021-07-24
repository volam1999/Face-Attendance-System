package com.tpcop.DataAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tpcop.DataAPI.entity.Department;
import com.tpcop.DataAPI.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	DepartmentService deparmentService;

	@GetMapping("/department/count")
	private Long count() {
		return deparmentService.count();
	}

	@PostMapping("/department/add")
	private Department create(@RequestBody Department department) {
		return deparmentService.create(department);
	}

}
