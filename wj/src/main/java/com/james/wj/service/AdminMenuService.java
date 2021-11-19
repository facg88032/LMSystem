package com.james.wj.service;

import com.james.wj.dao.AdminMenuDao;
import com.james.wj.pojo.AdminMenu;
import com.james.wj.pojo.AdminRoleMenu;
import com.james.wj.pojo.AdminUserRole;
import com.james.wj.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminMenuService {

    @Autowired
    AdminMenuDao adminMenuDao;

    @Autowired
    UserService userService;

    @Autowired
    AdminRoleMenuService adminRoleMenuService;

    @Autowired
    AdminUserRoleService adminUserRoleService;

    public List<AdminMenu> getAllByParentId(int parentId){
        return adminMenuDao.findAllByParentId(parentId);
    }

    public List<AdminMenu> getMenusByCurrentUser() {
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        User user = userService.findByUsername(username);

        List<Integer> rids = adminUserRoleService.listAllByUid(user.getId())
                .stream().map(AdminUserRole::getRid).collect(Collectors.toList());

        List<Integer> menuIds  = adminRoleMenuService.listAllByRid(rids)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());
        List<AdminMenu> menus = adminMenuDao.findAllById(menuIds).stream().distinct().collect(Collectors.toList());

        handleMenus(menus);
        return menus;

    }

    public List<AdminMenu> getMenuByRoleId(int rid){
        List<Integer> menuIds = adminRoleMenuService.listAllByRid(rid)
                .stream().map(AdminRoleMenu::getMid).collect(Collectors.toList());

        List<AdminMenu> menus = adminMenuDao.findAllById(menuIds);

        handleMenus(menus);
        return menus;
    }


    public void handleMenus(List<AdminMenu> menus) {
        menus.forEach(m->{
            List<AdminMenu> children = getAllByParentId(m.getId());
            m.setChildren(children);
        });

        menus.removeIf(m -> m.getParentId() != 0);
    }
}
