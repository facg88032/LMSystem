package com.james.wj.service;

import com.james.wj.dao.CategoryDao;
import com.james.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryDao categoryDAO;

    public List<Category> list(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    public Category get(int id){
        Category c = categoryDAO.findById(id).orElse(null);
        return c ;
    }
}
