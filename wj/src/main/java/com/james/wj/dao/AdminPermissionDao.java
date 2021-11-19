package com.james.wj.dao;

import com.james.wj.pojo.AdminPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPermissionDao extends JpaRepository<AdminPermission, Integer> {
    AdminPermission findById(int id);
}
