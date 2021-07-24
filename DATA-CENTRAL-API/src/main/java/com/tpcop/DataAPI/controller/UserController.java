package com.tpcop.DataAPI.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.AbstractJpaQuery;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tpcop.DataAPI.entity.User;
import com.tpcop.DataAPI.model.ATModel;
import com.tpcop.DataAPI.model.LogModel;
import com.tpcop.DataAPI.model.UserModel;
import com.tpcop.DataAPI.repository.IUserRepository;
import com.tpcop.DataAPI.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	IUserRepository iUserRepository;

	@GetMapping("/user/{username}")
	private List<UserModel> getAllUserIsNotCurrentUser(@PathVariable("username") String username) {
		return userService.getAllUserModel(username);
	}

	@GetMapping("/user/get/{username}")
	private User getByUserName(@PathVariable("username") final String username) {
		return iUserRepository.findByUsernameOrEmailIgnoreCase(username, username);
	}

	@GetMapping("/user/count/{status}")
	private Long countAllActiveUser(@PathVariable("status") final int status) {
		return iUserRepository.countByStatus(status);
	}

	@GetMapping("/user/count")
	private Long countAllUser() {
		return iUserRepository.count();
	}

	// 1 login success
	// -1 account has been ban
	// 0 wrong name or password
	@PostMapping("/user/validate")
	private int validateUser(@RequestBody User user) {
		User currentUser = iUserRepository.findByUsernameOrEmailAndPasswordIgnoreCase(user.getUsername(),
				user.getUsername(), user.getPassword());
		if (currentUser != null) {
			if (currentUser.getStatus() == 1) {
				return 1;
			} else {
				return -1;
			}
		}
		return 0;
	}

	@RequestMapping(value = "user/add", method = RequestMethod.POST)
	private String addUser(@RequestBody User user) {
		try {
			String md5Hex = user.getPassword();
			md5Hex = DigestUtils.md5DigestAsHex(md5Hex.getBytes("UTF-8"));
			user.setPassword(md5Hex);
			userService.create(user);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return "failed";
		}

	}

	@RequestMapping(value = "user/delete", method = RequestMethod.DELETE)
	private String delete(Long id) {
		try {
			iUserRepository.deleteById(id);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return "failed";
		}
	}

	@RequestMapping(value = "user/control", method = RequestMethod.POST)
	private String addUser(@RequestBody UserModel userModel) {
		try {
			User user = this.iUserRepository.getOne(userModel.getId());
			int status = (user.getStatus() == 1) ? 0 : 1;

			user.setStatus(status);
			user.setModifiedBy(userModel.getUsername());
			user.setModifiedDate(new Date());

			if (user != null) {
				this.iUserRepository.save(user);
			}

			return "successfully";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return "failed";
		}

	}

	@RequestMapping(value = "user/update", method = RequestMethod.PUT)
	private String update(@RequestBody User user) {
		try {
			User oldUser = iUserRepository.getOne(user.getId());
			iUserRepository.save(mapingUser(oldUser, user));
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
			return "failed";
		}
	}

	@GetMapping("/getLogModel")
	private List<LogModel> getLogModel() {
		return iUserRepository.getLogModel();
	}

	private User mapingUser(User oldUser, User newUser) {

		if (!oldUser.getUsername().equals(newUser.getUsername()) && newUser.getUsername() != null) {
			oldUser.setUsername(newUser.getUsername());
		}

		if (!oldUser.getEmail().equals(newUser.getEmail()) && newUser.getEmail() != null) {
			oldUser.setEmail(newUser.getEmail());
		}

		if (newUser.getFirstname() != null) {
			oldUser.setFirstname(newUser.getFirstname());
		}

		if (newUser.getLastname() != null) {
			oldUser.setLastname(newUser.getLastname());
		}

		if (newUser.getPassword() != null) {
			oldUser.setPassword(newUser.getPassword());
		}

		if (newUser.getSex() != null) {
			oldUser.setSex(newUser.getSex());
		}

		if (newUser.getPhoneNumber() != null) {
			oldUser.setPhoneNumber(newUser.getPhoneNumber());
		}

		if (newUser.getBirthDay() != null) {
			oldUser.setBirthDay(newUser.getBirthDay());
		}

		if (newUser.getLocal() != null) {
			oldUser.setLocal(newUser.getLocal());
		}

		if (newUser.getDepartmentId() != null) {
			oldUser.setDepartmentId(newUser.getDepartmentId());
		}

		oldUser.setModifiedBy(oldUser.getUsername());
		oldUser.setModifiedDate(new Date());

		return oldUser;
	}

}
