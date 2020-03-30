package com.zys;

import com.zys.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author zhangys
 * @description
 * @date 2020/3/30
 */
public class MainApplication {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("provider.xml");

        OrderService orderService = ioc.getBean(OrderService.class);
        orderService.initOrder("1");
        System.in.read();

    }



}
