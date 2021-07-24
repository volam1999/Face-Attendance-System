package com.tpcop.DataAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ATModel {

	private Long id;
	private String firstname;
	private String lastname;
	private String dayAtWorks;
}
