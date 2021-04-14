package com.gome.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/7 11:13
 */
@Data
@ToString
@AllArgsConstructor
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private Date birthday;
}
