package com.james.wj.controller;

import com.james.wj.pojo.AdminMenu;
import com.james.wj.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {


    @Autowired
    AdminMenuService adminMenuService;


    @GetMapping("/api/menu")
    public List<AdminMenu> menu() {
        return adminMenuService.getMenusByCurrentUser();
    }
}
