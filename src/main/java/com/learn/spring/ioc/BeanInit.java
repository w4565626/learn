package com.learn.spring.ioc;

import com.learn.spring.aop.Chinese;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ioc {
    @Autowired
    private Chinese chinese;

}