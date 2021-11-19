package com.james.wj.controller;

import com.james.wj.pojo.User;
import com.james.wj.result.Result;
import com.james.wj.result.ResultFactory;
import com.james.wj.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public Result notLogin() {
        String message = "尚未登入！";
        return ResultFactory.buildFailResult(message);
    }


    @PostMapping( "api/login")
    public Result login(@RequestBody User requestUser , HttpSession session) {

        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        usernamePasswordToken.setRememberMe(true);
        try {
            subject.login(usernamePasswordToken);
            return ResultFactory.buildSuccessResult(username);
        }catch (AuthenticationException e){
            String message = "帳號密碼錯誤";
            return ResultFactory.buildFailResult(message);
        }
    }


    @PostMapping("api/register")
    public Result register(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);

        System.out.println(username);
        System.out.println(password);

        boolean exist = userService.isExist(username);
        if (exist) {
            String message = "用戶名已被使用";
            return ResultFactory.buildFailResult(message);
        }


        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        user.setSalt(salt);
        user.setPassword(encodedPassword);
        userService.add(user);
        return ResultFactory.buildSuccessResult(user);
    }


    @GetMapping("api/logout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }


    @GetMapping("api/authentication")
    public Result authentication(){
        String message = "認證成功";
        return ResultFactory.buildSuccessResult(message);
    }

}
