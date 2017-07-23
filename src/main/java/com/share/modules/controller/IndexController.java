package com.share.modules.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lin on 2017/7/9 13:15.
 */
@RestController
@RequestMapping(value = "index")
public class IndexController {


    private static final Logger logger = Logger.getLogger(IndexController.class);

    @RequestMapping(value = "index1")
    public Map<String,Object> idnex1(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> data = new HashMap<String, Object>();



        logger.info("进入indexcontroller");
        data.put("message","成功访问,登录验证成功通过");

        return data;

    }

}
