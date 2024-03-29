package com.james.wj.dao;

import com.james.wj.pojo.AdminRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRolePermissionDao extends JpaRepository<AdminRolePermission, Integer> {
        List<AdminRolePermission> findAllByRid(int rid);
        List<AdminRolePermission> findAllByRidIn(List<Integer> rids);
        void deleteAllByRid(int rid);
        }
