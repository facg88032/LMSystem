package com.james.wj.dao;

import com.james.wj.pojo.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleDao extends JpaRepository<AdminRole,Integer> {

    AdminRole findById(int id);
}
