package com.share.modules.service;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.share.modules.repository.StaticEntityManager;
import com.share.modules.repository.UserRepository;
import com.share.modules.repository.dao.UserDao;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.share.modules.entity.User;

/**
 * 2017年7月3日16:23:19
 * @author lin
 *
 */
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDao userDao;

	/**
	 * 2017年7月6日19:10:40
	 * lin
	 * 注册
	 * @param user
	 */
	public int register(User user){
		return userDao.register(user);
	}

	/**
	 * 2017年7月6日12:50:41
	 * lin
	 * 判断该邮箱是否存在
	 * @param email
	 * @return
	 */
	public boolean isExistByEmail(String email){ return userDao.isExistByEmail(email);}

	/**
	 * 2017年7月7日13:29:40
	 * lin
	 * 登录
	 * @param email
	 * @param password
	 * @return
	 */
	public User login(String email, String password){return userDao.login(email,password);}

	/**
	 * 2017年7月8日20:19:13
	 * lin
	 * 更改用户登录时间
	 * @param userId
	 */
	public void updateLoginDate(Long userId){
		userDao.updateLoginDate(userId);
	}

}
