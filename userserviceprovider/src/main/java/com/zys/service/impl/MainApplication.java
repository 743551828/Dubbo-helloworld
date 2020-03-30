package com.zys.service.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zhangys
 * @description
 * @date 2020/3/30
 */
public class MainApplication {


    public static void main(String[] args){
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("provider.xml");
        ioc.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
