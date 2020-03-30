package com.zys.service.impl;

import com.zys.bean.UserAddress;
import com.zys.service.OrderService;
import com.zys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description 订单接口的实现类
 * @author zhangys
 * @date 2020/3/30
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserService userService;

    /**
     * @description 1.查询用户的收货地址
     * @params userId
     * @return null
     * @author zhangys
     * @date 2020/3/30
     */
    @Override
    public void initOrder(String userId) {
        List<UserAddress> userAddressList = userService.getUserAddressList(userId);
        System.out.println(userAddressList);
    }
}
