package com.share.modules.repository.dao;

import com.share.modules.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by lin on 2017/7/9 10:32.
 */
@Repository
public class TokenDao {

    @Autowired
    private EntityManager entityManager;

    /**
     * 2017年7月9日10:36:13
     * 根据用户id删除token
     * @param userId
     * @return
     */
    @Transactional
    public int delTokenByUserId(Long userId){
        String sql = "delete from token where user_id="+userId;
        Query query = entityManager.createNativeQuery(sql);
        return query.executeUpdate();
    }

    /**
     * 2017年7月9日21:07:21
     * lin
     * 根据token和userId来查询token信息
     * @param token
     * @param userId
     * @return
     */
    public Token getTokenByTokenAndUserId(String token, Long userId){
        String sql = "select * from token where token='"+token+"' and user_id="+userId;
        Query query = entityManager.createNativeQuery(sql, Token.class);
        query.setMaxResults(1);
        List<Token> list = query.getResultList();
        if(list != null && list.size() != 0){
            return list.get(0);
        }
        return null;
    }


}
