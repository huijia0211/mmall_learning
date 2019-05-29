package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 检查用户名
     *
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 校验email
     *
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 查询问题
     *
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    /**
     * 查询答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 重置密码
     *
     * @param username
     * @param passwordNew
     * @return
     */
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    /**
     * 修改密码
     * @param password
     * @param userId
     * @return
     */
    int checkPassword(@Param("password") String password, @Param("userId") Integer userId);

    /**
     * 根据id判断email是否存在
     * @param email
     * @param userId
     * @return
     */
    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);
}