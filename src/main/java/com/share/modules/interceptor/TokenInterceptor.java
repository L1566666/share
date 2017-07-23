package com.share.modules.interceptor;

import com.share.modules.entity.Token;
import com.share.modules.service.TokenServicer;
import com.share.modules.tools.HttpTool;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lin on 2017/7/9 19:46.
 */
public class TokenInterceptor implements  HandlerInterceptor{

    @Autowired
    private TokenServicer tokenServicer;

    private static final Logger logger = Logger.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Object userIdSession = session.getAttribute("userId"); //获取session中的userId
        String userIdCookie = HttpTool.getCookieValue(httpServletRequest, "userId");
        String token = HttpTool.getCookieValue(httpServletRequest, "token");    //从cookie中获取token
        logger.info("拦截器:获取的userIdSession:"+userIdSession+",userIdCookie:"+userIdCookie+",token:"+token);
        //判断session中的userId与Cookie中的userId是否相同,如果不相同,则返回null;
        if(userIdSession ==null || userIdCookie ==null || "".equals(userIdCookie) || !userIdCookie.equals(userIdSession.toString())){
           logger.info("来自ip为:"+HttpTool.getIpAddress(httpServletRequest)+"的请求被拦截!原因是userIdSession或userIdCookie为空!.");
           return false;
        }

        Token tokenBean = tokenServicer.getTokenByTokenAndUserId(token, Long.valueOf(userIdCookie));
        if(tokenBean == null){
            logger.info("来自ip为:"+HttpTool.getIpAddress(httpServletRequest)+"的请求被拦截!原因是token为空.");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
