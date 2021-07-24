package com.tpcop.DataAPI.service;

import com.tpcop.DataAPI.entity.Department;

public interface DepartmentService {

	long count();

	Department create(Department department);
}
