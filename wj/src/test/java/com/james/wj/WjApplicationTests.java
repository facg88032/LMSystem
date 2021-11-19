package com.james.wj;

import com.james.wj.dao.AdminRoleMenuDao;
import com.james.wj.dao.BookDao;
import com.james.wj.dao.CategoryDao;
import com.james.wj.pojo.AdminRoleMenu;
import com.james.wj.pojo.Book;
import com.james.wj.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class WjApplicationTests {

    @Autowired
    AdminRoleMenuDao adminRoleMenuDao;

    @Autowired
    BookService bookService;

    @Test
    void contextLoads() {

        Book book = bookService.getById(1);
        System.out.println(book);
    }

}
