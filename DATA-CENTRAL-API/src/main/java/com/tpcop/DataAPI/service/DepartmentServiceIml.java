package com.tpcop.DataAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpcop.DataAPI.entity.Department;
import com.tpcop.DataAPI.repository.IDepartmentRepository;

@Service("departmentService")
public class DepartmentServiceIml implements DepartmentService {

	@Autowired
	IDepartmentRepository iDepartmentRepository;

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return iDepartmentRepository.count();
	}

	@Override
	public Department create(Department department) {
		// TODO Auto-generated method stub
		return iDepartmentRepository.save(department);
	}

}
