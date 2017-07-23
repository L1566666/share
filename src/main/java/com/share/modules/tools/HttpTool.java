package com.share.modules.tools;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lin on 2017/7/8 20:05.
 */
public class HttpTool {

    /**
     * 2017年7月8日20:06:09
     * 获取用户ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");
        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded;
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
            }
        }
        return ip;
    }

    /**
     * 2017年7月8日21:34:49
     * lin
     * 添加cookie
     * @param response
     * @param request
     * @throws UnsupportedEncodingException
     */
    public static void addCookie(String key, String value, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        //创建Cookie
        Cookie tokenCookie = new Cookie(key, value);
        //获取是否保存Cookie
        String rememberMe = request.getParameter("remember");
        if (rememberMe == null || rememberMe.equals("false")) {//不保存Cookie
            tokenCookie.setMaxAge(-1);  //设置cookie关闭浏览器失效
        } else {//保存Cookie的时间长度，单位为秒
            tokenCookie.setMaxAge(7 * 24 * 60 * 60);
        }
        //加入Cookie到响应头
        response.addCookie(tokenCookie);
    }

    /**
     * 2017年7月9日11:00:24
     * lin
     * @param key
     * @param value
     * @param request
     * @param response
     */
    public static void addSession(String key, Object value, HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        HttpSession session = request.getSession();
        //将数据存储到session中
        session.setAttribute(key, value);
        String remember = request.getParameter("remember");
        if("true".equals(remember)){
            session.setMaxInactiveInterval(7 * 24 * 60 * 60);
        }else{
            session.setMaxInactiveInterval(24 * 60 * 60);
        }

    }

    /**
     * 2017年7月9日20:54:18
     * lin
     * 获取cookievalue
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        String cookieValue = "";
        if(cookies != null){
            for (int i = 0; i < cookies.length; i++) {
                if(cookieName.equals(cookies[i].getName())){
                    cookieValue = cookies[i].getValue();
                    break;
                }
            }
        }
        return cookieValue;
    }

    /**
     * 2017年7月10日21:12:02
     * lin
     * 删除所有cookie
     * @param request
     * @param response
     */
    public static void delAllCookie(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies=request.getCookies();
        if(cookies != null){
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                cookie.setValue("");
                response.addCookie(cookie);
            }
        }

    }


}
