package com.james.wj.controller;


import com.james.wj.pojo.Book;
import com.james.wj.result.Result;
import com.james.wj.result.ResultFactory;
import com.james.wj.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class LibraryController {

    @Autowired
    BookService bookService;

    @GetMapping("/api/books")
    public Result list() throws Exception{
        return ResultFactory.buildSuccessResult(bookService.list());
    }

    @GetMapping("/api/books/{id}")
    public Book getBook(@PathVariable("id") int id) {
       return bookService.getById(id);
    }


    @GetMapping("/api/categories/{cid}/books")
    public Result listByCategory(@PathVariable("cid") int cid) throws Exception {
        if (0 != cid) {
            return ResultFactory.buildSuccessResult(bookService.listByCategory(cid));
        } else {
            return ResultFactory.buildSuccessResult(bookService.list());
        }
    }

    @GetMapping("/api/search")
    public Result Search(@RequestParam("keywords") String keywords){
        if ("".equals(keywords)) {
            return ResultFactory.buildSuccessResult(bookService.list());
        } else {
            return ResultFactory.buildSuccessResult(bookService.Search(keywords));
        }
    }

    @PostMapping("/api/admin/content/books")
    public Result addOrUpdate (@RequestBody Book book) {
        bookService.addOrUpdate(book);
        return ResultFactory.buildSuccessResult("更新成功");
    }

    @PostMapping("/api/admin/content/books/delete")
    public Result deleteBook(@RequestBody @Valid Book book){
        bookService.deleteById(book.getId());
        return ResultFactory.buildSuccessResult("刪除成功");
    }



    @PostMapping("/api/admin/content/books/covers")
    public String coversUpload(MultipartFile file){
        String folder = "D:/vueSB/workspace/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder, UUID.randomUUID()+ "." + file.getOriginalFilename()
                .replaceAll("^.*\\.(.*)$", "$1"));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try {
            file.transferTo(f);
            String imgURL = "http://localhost:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

    }




}
