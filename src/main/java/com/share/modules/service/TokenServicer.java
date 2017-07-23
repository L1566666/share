package com.share.modules.service;

import com.share.modules.entity.Token;
import com.share.modules.repository.dao.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lin on 2017/7/9 10:36.
 */
@Service
public class TokenServicer {

    @Autowired
    private TokenDao tokenDao;

    /**
     * 2017年7月9日10:38:42
     * lin
     * @param userId
     * @return
     */
    public int delTokenByUserId(Long userId){
        return tokenDao.delTokenByUserId(userId);
    }
    public Token getTokenByTokenAndUserId(String token, Long userId){
        return tokenDao.getTokenByTokenAndUserId(token, userId);
    }


}
