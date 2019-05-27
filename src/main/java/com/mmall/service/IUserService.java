package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 *
 */
public interface IUserService {
    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username, String password);
    /**
     * 注册
     *
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 参数校验
     *
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkValid(String str,String type);
}
