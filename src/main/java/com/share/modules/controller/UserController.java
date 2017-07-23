package com.share.modules.controller;

import com.share.modules.entity.Token;
import com.share.modules.entity.User;
import com.share.modules.repository.TokenRepository;
import com.share.modules.repository.UserRepository;
import com.share.modules.service.TokenServicer;
import com.share.modules.tools.HttpTool;
import com.share.modules.tools.Tool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.share.modules.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 2017年7月3日16:24:46
 * @author lin
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenServicer tokenServicer;

	private static final Logger logger = Logger.getLogger(UserController.class);

//	@Autowired
//    private UserDao userDao;

	/**
     * 2017年7月3日16:32:16
     * @return
	 * @param email
	 * @param password
	 */

    @RequestMapping(value = "register")
    public Map<String, Object> register(@RequestParam("email") String email, @RequestParam("password") String password,
                                        HttpServletRequest request){
        Map<String, Object> data = new HashMap<String, Object>();
        //获取ip地址
        String ip = HttpTool.getIpAddress(request);
        logger.info("来自ip地址为:"+ip+"的用户注册,邮箱为:"+email);
        if(email==null||"".equals(email)){
            data.put("resultCode", 0);
            data.put("message", "注册失败,用户名不能为空!");
            return data;
        }
        if(password==null||"".equals(password)){
            data.put("resultCode", 0);
            data.put("message", "注册失败,密码不能为空!");
            return data;
        }
        //判断邮箱长度
        if(email.length()>50){
            data.put("resultCode", 0);
            data.put("message", "注册失败,用户名长度超出范围!");
            return data;
        }
        //判断密码长度
        if(password.length()>16){
            data.put("resultCode", 0);
            data.put("message", "注册失败,密码长度超出范围!");
            return data;
        }
        //判断此邮箱是否存在
        if(userService.isExistByEmail(email)){
            data.put("resultCode", 0);
            data.put("message", "注册失败,该用户已存在!");
            return data;
        }
        User user = new User();
        Date date = new Date();
        user.setEmail(email);
        user.setPassword(password);
        user.setCreateDate(date);
        user.setUpdateDate(date);
        user.setLoginDate(date);
//        userRepository.save(user);
        //判断是否存储成功.
        int result = userService.register(user);
        if(result == 0){
            data.put("resultCode", 0);
            data.put("message", "注册失败!");
            return data;
        }
        data.put("resultCode", 1);
        data.put("message", "注册成功.");
        return data;
    }

    /**
     * 2017年7月7日13:19:26
     * lin
     * 登录
     * @return
     * 判断用户名密码成功后,将生成的唯一表示uuid 经过md5加密后生成token,然后,1,将token,和userid等信息存入数据库.2.将token和uid存入cookie,3.userid存入session
     * 拦截器拦截请求时步骤,1,将cookie中的userId与session中userId对比,如果相同则根据token和userid去匹配数据库存储的token信息,如果全部相同则证明用户登录.
     */
    @RequestMapping(value = "login")
    public Map<String,Object> login(@RequestParam("email") String email, @RequestParam("password") String password,
                                    @RequestParam("remember") String remember, HttpServletRequest request,
                                    HttpServletResponse response) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        Map<String, Object> data = new HashMap<String, Object>();   //存放返回数据

        //获取ip地址
        String ip = HttpTool.getIpAddress(request);
        logger.info("来自ip地址为:"+ip+"的用户登录,所使用邮箱为:"+email);
        if(email==null || "".equals(email)){
            data.put("resultCode",0);
            data.put("message","登录失败,用户名不能为空!");
            return data;
        }
        if(password==null || "".equals(password)){
            data.put("resultCode",0);
            data.put("message","登录失败,密码不能为空!");
            return data;
        }
        //判断邮箱长度
        if(email.length()>50){
            data.put("resultCode", 0);
            data.put("message", "登录失败,邮箱长度超出范围!");
            return data;
        }
        //判断密码长度
        if(password.length()>16){
            data.put("resultCode", 0);
            data.put("message", "登录失败,密码长度超出范围!");
            return data;
        }

        User user = userService.login(email, password);
        //判断是否有此用户
        if(user==null){
            data.put("resultCode",0);
            data.put("message","登录失败,未查找到该用户!");
            return data;
        }

        //判断用户密码是否正确
        if(!password.equals(user.getPassword())){
            logger.info("用户id为:"+user.getId()+"登录失败,密码错误.");
            data.put("resultCode",0);
            data.put("message","登录失败,密码错误!");
            return data;
        }
        //更改用户登录时间
        userService.updateLoginDate(user.getId());
        //登录成功,生成token
        String uuid = System.currentTimeMillis()+user.getId().toString()+user.getEmail();
        String token = Tool.MD5(uuid);
        Date date = new Date();

        int delTokenResult = tokenServicer.delTokenByUserId(user.getId());
        logger.debug("登录成功,生成token前删除该用户之前所生成的token,共删除"+delTokenResult+"条数据");
        Token tokenBean = new Token();
        tokenBean.setUserId(user.getId());
        tokenBean.setToken(token);
        tokenBean.setIp(ip);
        tokenBean.setCreateDate(new Date());
        tokenRepository.save(tokenBean);

        //存放cookie
        HttpTool.addCookie("token", token, request, response);
        //存放uid
        HttpTool.addCookie("userId", user.getId().toString(), request, response);
        HttpTool.addCookie("email", user.getEmail(),request,response);

        HttpTool.addSession("userId", user.getId(), request, response); //将userId存入session
        HttpTool.addCookie("JSESSIONID", request.getSession().getId(), request, response);  //将sessinid存入cookie
        HttpTool.addSession("email", user.getEmail(), request, response);   //将email存入cookie
        data.put("resultCode",1);
        data.put("message","登录成功!");
        return data;
    }

    /**
     * 2017年7月10日21:00:46
     * lin
     * 判断是否登录接口
     * @return
     */
    @RequestMapping(value = "isLogin")
    public Map<String, Object> isLogin(){
        Map<String, Object> data = new HashMap<String, Object>();   //存放返回数据

        data.put("resultCode",1);
        data.put("message","已登录!");
        return data;

    }

    /**
     * 2017年7月10日21:12:15
     * lin
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "loginOut")
    public Map<String,Object> loginOut(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> data = new HashMap<String, Object>();   //存放返回数据

        HttpSession session = request.getSession();
        session.invalidate();
        HttpTool.delAllCookie(request, response);

        data.put("resultCode",1);
        data.put("message","退出登录成功");
        return data;

    }


}
