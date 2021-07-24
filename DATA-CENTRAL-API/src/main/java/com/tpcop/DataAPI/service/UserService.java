package com.tpcop.DataAPI.service;

import java.util.List;

import com.tpcop.DataAPI.entity.User;
import com.tpcop.DataAPI.model.UserModel;

public interface UserService {
	public User create(User user);
	
	public List<UserModel> getAllUserModel(String username);
}
