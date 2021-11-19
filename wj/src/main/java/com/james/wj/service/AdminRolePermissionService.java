package com.james.wj.service;

import com.james.wj.dao.AdminPermissionDao;
import com.james.wj.dao.AdminRolePermissionDao;
import com.james.wj.pojo.AdminPermission;
import com.james.wj.pojo.AdminRole;
import com.james.wj.pojo.AdminRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminRolePermissionService {

    @Autowired
    AdminRolePermissionDao adminRolePermissionDao;

    List<AdminRolePermission> findAllByRid(int rid) {
        return adminRolePermissionDao.findAllByRid(rid);
    }

    @Transactional
    public void savePermChanges(int rid, List<AdminPermission> perms) {
        adminRolePermissionDao.deleteAllByRid(rid);
        List<AdminRolePermission> rps = new ArrayList<>();
        perms.forEach(perm ->{
            AdminRolePermission rp = new AdminRolePermission();
            rp.setRid(rid);
            rp.setPid(perm.getId());
            rps.add(rp);
        });
        adminRolePermissionDao.saveAll(rps);
    }
}

