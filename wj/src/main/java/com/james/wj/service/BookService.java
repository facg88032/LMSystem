package com.james.wj.service;

import com.james.wj.dao.BookDao;
import com.james.wj.pojo.Book;
import com.james.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookDao bookDao;

    @Autowired
    CategoryService categoryService;

    public Book getById(int id){
       return bookDao.findById(id);
    }

    public List<Book> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return bookDao.findAll(sort);
    }

    public void addOrUpdate(Book book){
        bookDao.save(book);
    }

    public void deleteById(int id){
        bookDao.deleteById(id);
    }

    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        return bookDao.findAllByCategory(category);
    }

    public List<Book> Search(String keyword){
        return bookDao.findAllByTitleLikeOrAuthorLike('%' + keyword + '%','%' + keyword + '%');
    }

}
