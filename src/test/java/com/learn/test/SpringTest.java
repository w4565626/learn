package com.learn.test;


import com.learn.spring.ioc.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTest {
    @Autowired
    private Book book;

    @Test
    public void test(){
        book.setBookName("test");
        System.out.println(book.getBookName());
    }
}
