package com.tpcop.DataAPI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TBL_DEPARTMENT")
public class Department extends BaseEntity {

    @Column(name = "DEPARTMENT_NAME", nullable = false)
    private String name;

    @Column(name = "DEPARTMENT_DESCRIPTION", length = 1500)
    private String description;


}
