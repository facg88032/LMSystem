package com.james.wj.service;


import com.james.wj.dao.AdminRoleDao;
import com.james.wj.pojo.AdminMenu;
import com.james.wj.pojo.AdminPermission;
import com.james.wj.pojo.AdminRole;
import com.james.wj.pojo.AdminUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminRoleService {

    @Autowired
    AdminRoleDao adminRoleDao;
    @Autowired
    UserService userService;
    @Autowired
    AdminPermissionService adminPermissionService;
    @Autowired
    AdminMenuService adminMenuService;
    @Autowired
    AdminUserRoleService adminUserRoleService;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;
    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    public List<AdminRole> listWithPermsAndMenus(){
        List<AdminRole> roles = adminRoleDao.findAll();
        List<AdminPermission> perms;
        List<AdminMenu> menus;
        for (AdminRole role : roles) {
            perms = adminPermissionService.listPermsByRoleId(role.getId());
            menus = adminMenuService.getMenuByRoleId(role.getId());
            role.setMenus(menus);
            role.setPerms(perms);
        }

        return roles;
    }

    public List<AdminRole> listRolesByUser(String username) {
        int uid = userService.findByUsername(username).getId();
        List<Integer> rids = adminUserRoleService.listAllByUid(uid)
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());
        return adminRoleDao.findAllById(rids);
    }


    public void addOrUpdate(AdminRole role){
      adminRoleDao.save(role);
    }

    public AdminRole updateRoleStatus(AdminRole role){
        AdminRole roleInDB = adminRoleDao.findById(role.getId());
        roleInDB.setEnabled(role.isEnabled());
        return adminRoleDao.save(roleInDB);
    }

    @Transactional
    public void deleteById(int id){
        adminRoleDao.deleteById(id);
        adminRolePermissionService.deleteAllByRid(id);
        adminRoleMenuService.deleteAllByRid(id);
    }


}
