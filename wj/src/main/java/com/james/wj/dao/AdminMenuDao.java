package com.james.wj.dao;

import com.james.wj.pojo.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminMenuDao extends JpaRepository<AdminMenu,Integer> {

    AdminMenu findById(int id);

    List<AdminMenu> findAllByParentId(int parentId);
}
