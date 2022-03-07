package com.lin.appapidemo.model.shixun;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 新增修改，根据重构数据库重写实体
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "t_reader")
public class Reader {
    /**
     * 账户
     */
    @Column(name = "account")
    private String account;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;
    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;
    /**
     * 手机号
     */
    @Column(name="phone")
    private String phone;
    /**
     * 邮箱
     */
    @Column(name="email")
    private String email;

    /**
     * 注册时间
     */
    @Column(name = "time")
    private String time;

    /**
     * 读者类别
     */
    @Column(name = "condi")
    private int condi;


    /**
     *构造函数
     */
    public Reader(String account, String password, String name, String sex,String phone,String email, String time, int condi) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.email=email;
        this.phone=phone;
        this.time = time;
        this.condi = condi;
    }
}
