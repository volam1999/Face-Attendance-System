package com.tpcop.DataAPI.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogModel {
	private Long uid;
	private Long lid;
	private Date detectedTime;
	private String username;
	private String firstname;
	private String lastname;
}
