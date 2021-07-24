package com.tpcop.DataAPI.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TBL_USER")
public class User extends BaseEntity {

	@Column(unique = true, nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	private String password;

	private Long departmentId;

	private String phoneNumber;

	private Date birthDay;

	private String firstname;

	private String lastname;
	
	private String sex = "male";

	private String local = "VN";
	
	/**
	 * STATUS OF ROW DATA<br>
	 * 1 is active.<br>
	 * -1 is inactive.
	 */
	@Column(name = "STATUS", nullable = false)
	private Integer status = 1;
}
