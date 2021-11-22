package com.james.wj.service;

import com.james.wj.dao.AdminRoleMenuDao;
import com.james.wj.pojo.AdminRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AdminRoleMenuService {

    @Autowired
    AdminRoleMenuDao adminRoleMenuDao;

    public List<AdminRoleMenu> listAllByRid(int rid){
        return adminRoleMenuDao.findAllByRid(rid);
    }

    public List<AdminRoleMenu> listAllByRid(List<Integer> rids) {
        return adminRoleMenuDao.findAllByRidIn(rids);
    }

    @Transactional
    public void updateRoleMenu(int rid, Map<String, List<Integer>> menusIds) {
        adminRoleMenuDao.deleteAllByRid(rid);
        List<AdminRoleMenu> rms = new ArrayList<>();
        for (Integer mid : menusIds.get("menusIds")) {
            AdminRoleMenu rm = new AdminRoleMenu();
            rm.setMid(mid);
            rm.setRid(rid);
            rms.add(rm);
        }

        adminRoleMenuDao.saveAll(rms);
    }

    @Transactional
    public void deleteAllByRid(int rid){
        adminRoleMenuDao.deleteAllByRid(rid);
    }


}
