package com.james.wj.controller;

import com.james.wj.pojo.AdminMenu;
import com.james.wj.pojo.AdminRole;
import com.james.wj.result.Result;
import com.james.wj.result.ResultFactory;
import com.james.wj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoleController {

    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminMenuService adminMenuService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;


    @GetMapping("/api/admin/role")
    public Result listRoles() {
        return ResultFactory.buildSuccessResult(adminRoleService.listWithPermsAndMenus());
    }

    @PutMapping("/api/admin/role")
    public Result editRole(@RequestBody AdminRole requsetRole) {
        adminRoleService.addOrUpdate(requsetRole);
        adminRolePermissionService.savePermChanges(requsetRole.getId(), requsetRole.getPerms());
        String message = "修改角色訊息成功";
        return ResultFactory.buildSuccessResult(message);
    }


    @PutMapping("/api/admin/role/status")
    public Result upateRoleStatus(@RequestBody AdminRole requestrole) {
        AdminRole adminRole = adminRoleService.updateRoleStatus(requestrole);
        String message = "用戶"+adminRole.getNameZh()+"更新成功";
        return ResultFactory.buildSuccessResult(message);
    }


    @GetMapping("/api/admin/role/perm")
    public Result listAllPerms() {
        return ResultFactory.buildSuccessResult(adminPermissionService.list());
    }

    @GetMapping("/api/admin/role/menu")
    public Result listAllMenus() {
        return ResultFactory.buildSuccessResult(adminMenuService.getMenuByRoleId(1));
    }

    @PutMapping("/api/admin/role/menu")
    public Result updateRoleMenu(@RequestParam int rid , @RequestBody Map<String,List<Integer>> menusIds){
        adminRoleMenuService.updateRoleMenu(rid, menusIds);
        return ResultFactory.buildSuccessResult("更新成功");
    }

}
