package com.james.wj.controller;

import com.james.wj.result.Result;
import com.james.wj.result.ResultFactory;
import com.james.wj.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MenuController {


    @Autowired
    AdminMenuService adminMenuService;


    @GetMapping("/api/menu")
    public Result menu() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenusByCurrentUser());
    }
}
