package com.james.wj.dao;

import com.james.wj.pojo.Book;
import com.james.wj.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book,Integer> {
    Book findById(int id);
    List<Book> findAllByCategory(Category category);
    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1,String keyword2);
}
