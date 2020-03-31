package com.zys.service;

import com.zys.bean.UserAddress;

import java.util.List;

/**
 * @author zhangys
 * @description 用户服务接口
 * @date 2020/3/30
 */
public interface UserService {

    /**
     * @description 按照用户id获取所有的地址
     * @params userId
     * @return List<UserAddress>
     * @author zhangys
     * @date 2020/3/30
     */
    public List<UserAddress> getUserAddressList(String userId);

}
