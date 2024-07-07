package com.sky.dto;

import lombok.Data;

import java.io.Serializable;
//前端提交的数据和实体类中对应的属性差别比较大时，可以使用DTO类
@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
