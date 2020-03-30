package com.zys.service.impl;

import com.zys.bean.UserAddress;
import com.zys.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangys
 * @description 用户服务实现
 * @date 2020/3/30
 */
public class UserServiceImpl implements UserService {

    /**
     * @return List<UserAddress>
     * @description 根据用户id获取所有的地址
     * @params userId
     * @author zhangys
     * @date 2020/3/30
     */
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress userAddress1 = new UserAddress(1, "上海", "123", "1234", "y", "y");
        UserAddress userAddress2 = new UserAddress(2, "北京", "123", "1234", "y", "y");
        List<UserAddress> userAddressList = new ArrayList<UserAddress>();
        userAddressList.add(userAddress1);
        userAddressList.add(userAddress2);
        System.out.println("userId:"+userId);
        return userAddressList;
    }
}
