package com.share.modules.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.share.modules.entity.parentEntity.IdEntity;
@Entity
@Table(name = "share_users")
public class User extends IdEntity {

	@Column(name = "username")
	private String username;	//用户名

	@Column(name = "email")
	private String email;	//用户邮箱
	
	@Column(name = "password")
	private String password;	//用户密码
	
	@Column(name = "sex")
	private String sex;		//用户性别
	
	@Column(name = "birthday")
	private Date birthday;		//用户出生日期
	
	@Column(name = "login_date")
	private Date loginDate;	//用户最近一次登录时间

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	
	
	
	
	
	
}
