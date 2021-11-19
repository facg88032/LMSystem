package com.james.wj.controller;

import com.james.wj.pojo.AdminRole;
import com.james.wj.pojo.User;
import com.james.wj.result.Result;
import com.james.wj.result.ResultFactory;
import com.james.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/api/admin/user")
    public Result listUsers() {
        return ResultFactory.buildSuccessResult(userService.list());
    }

    @PutMapping("/api/admin/user")
    public Result editUser(@RequestBody User requestUser){
        List<AdminRole> roles = requestUser.getRoles();
        for (AdminRole role : roles) {
            System.out.println(role.getNameZh());
        }

        userService.editUser(requestUser);
        String message ="修改用户訊息成功";
        return ResultFactory.buildSuccessResult(message);
    }

    @PutMapping("/api/admin/user/password")
    public Result resetPassword(@Valid @RequestBody  User requestUser) {
        userService.resetPassword(requestUser);
        return ResultFactory.buildSuccessResult("重置密码成功");
    }
}
