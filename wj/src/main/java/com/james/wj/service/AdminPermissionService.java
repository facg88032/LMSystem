package com.james.wj.service;

import com.james.wj.dao.AdminPermissionDao;
import com.james.wj.dao.AdminRolePermissionDao;
import com.james.wj.pojo.AdminPermission;
import com.james.wj.pojo.AdminRole;
import com.james.wj.pojo.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminPermissionService {
    @Autowired
    AdminRoleService adminRoleService;
    @Autowired
    AdminRolePermissionDao adminRolePermissionDao;
    @Autowired
    AdminPermissionDao adminPermissionDao;
    @Autowired
    AdminRolePermissionService adminRolePermissionService;


    public List<AdminPermission> list() {return adminPermissionDao.findAll();}

    public Set<String> listPermissionURLsByUser(String username) {
        List<Integer> rids = adminRoleService.listRolesByUser(username)
                .stream().map(AdminRole::getId).collect(Collectors.toList());

        List<Integer> pids = adminRolePermissionDao.findAllByRidIn(rids)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());

        List<AdminPermission> perms = adminPermissionDao.findAllById(pids);

        Set<String> URLs = perms.stream().map(AdminPermission::getUrl).collect(Collectors.toSet());

        return URLs;
    }

    public boolean needFilter(String requestAPI) {
        List<AdminPermission> ps = adminPermissionDao.findAll();
        for (AdminPermission p: ps) {
            if (p.getUrl().equals(requestAPI)) {
                return true;
            }
        }
        return false;
    }

    public List<AdminPermission> listPermsByRoleId(int rid) {
        List<Integer> pids = adminRolePermissionService.findAllByRid(rid)
                .stream().map(AdminRolePermission::getPid).collect(Collectors.toList());
        return adminPermissionDao.findAllById(pids);
    }
}
