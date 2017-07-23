package com.share.modules.repository.dao;

import com.share.modules.entity.User;
import com.share.modules.repository.StaticEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lin on 2017/7/6 9:57.
 */
@Repository
public class UserDao {

    @Autowired
    private EntityManager entityManager;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    /**
     * 2017年7月7日13:28:33
     * lin
     * 登录
     * @param email
     * @param password
     * @return
     */
    public User login(String email,String password){
//        EntityManager entityManager = StaticEntityManager.getEntityManger();    //获取entityManager
        String sql = "select * from share_users where email='"+email+"'";
        Query query = entityManager.createNativeQuery(sql, User.class);
        List<User> list = query.getResultList();
        if(list!=null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    /**
     * 2017年7月6日19:09:40
     * lin
     * @param user
     * @return
     */
    @Transactional
    public int register(User user){

//        EntityManager entityManager = StaticEntityManager.getEntityManger();    //获取entityManager
        //格式化开始和更新日期
        String createDate = simpleDateFormat.format(user.getCreateDate());
        String updateDate = simpleDateFormat.format(user.getUpdateDate());
        //拼接sql语句,使用md5进行密码加密
        StringBuffer sql = new StringBuffer("");
        sql.append("insert into share_users (email,password,create_date,update_date) ");
        //使用md5加密的sql
//        sql.append("values ('"+user.getEmail()+"',md5('"+user.getPassword()+"'),'"+createDate+"','"+updateDate+"')");

        //未使用md5加密 ,供测试使用
        sql.append("values ('"+user.getEmail()+"','"+user.getPassword()+"','"+createDate+"','"+updateDate+"')");

        Query query = entityManager.createNativeQuery(sql.toString());
        return query.executeUpdate();
    }

    /**
     * 2017年7月6日12:31:32
     * lin
     * @param email
     * @return
     */
    public boolean isExistByEmail(String email){
        String hql = "select u from User u where email='"+email+"'";

        Query query = entityManager.createQuery(hql, User.class);
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<User> list = query.getResultList();
        if(list == null || list.size() == 0){
            return false;
        }
        return true;
    }

    /**
     * 2017年7月8日20:18:12
     * lin
     * 更改用户登录时间
     * @param userId
     */
    @Transactional
    public void updateLoginDate(Long userId){
        String sql = "update share_users set login_date='"+simpleDateFormat.format(new Date())+"' where id="+userId;
        Query query = entityManager.createNativeQuery(sql);

        query.executeUpdate();

    }


}
