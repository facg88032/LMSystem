package com.james.wj.service;

import com.james.wj.dao.AdminUserRoleDao;
import com.james.wj.pojo.AdminRole;
import com.james.wj.pojo.AdminUserRole;
import com.james.wj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserRoleService {

    @Autowired
    AdminUserRoleDao adminUserRoleDao;

    public List<AdminUserRole> listAllByUid(int uid){
        return adminUserRoleDao.findAllByUid(uid);
    }

    @Transactional
    public void saveRoleChanges(int uid , List<AdminRole> roles){
        adminUserRoleDao.deleteAllByUid(uid);
        List<AdminUserRole> urs = new ArrayList<>();
        roles.forEach(role -> {
            AdminUserRole ur = new AdminUserRole();
            ur.setUid(uid);
            ur.setRid(role.getId());
            urs.add(ur);
        });
        adminUserRoleDao.saveAll(urs);

    }

}
