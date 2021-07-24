package com.tpcop.DataAPI.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tpcop.DataAPI.entity.User;
import com.tpcop.DataAPI.model.UserModel;
import com.tpcop.DataAPI.repository.IUserRepository;

@Transactional
@Service("userService")
public class UserSerrviceIml implements UserService {

	@Autowired
	private IUserRepository iUserRespository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		return iUserRespository.save(user);
	}

	@Override
	public List<UserModel> getAllUserModel(String username) {
		// TODO Auto-generated method stub
		return iUserRespository.findByUsernameIsNot(username)
								.stream()
								.map(this::userToModel)
								.collect(Collectors.toList());
	}

	private UserModel userToModel(User user) {
		UserModel userModel = new UserModel();
		modelMapper.map(user, userModel);
		return userModel;
	}

}
