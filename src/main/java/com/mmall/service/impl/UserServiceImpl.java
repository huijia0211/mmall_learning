package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Admin
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        //先根据用户名查询是否有记录
        int resultCount = this.userMapper.checkUsername(username);
        //判断是否存在记录
        if (resultCount == 0) {
            //不存在则告知前端
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //将明文密码转换为MD5加密
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        //根据用户名和MD5密码查询用户实体类
        User user = this.userMapper.selectLogin(username, md5Password);
        //判断用户是否存在
        if (user == null) {
            //由于前面已经判断过username已存在，多了筛选密码后无记录则代表密码错误
            return ServerResponse.createByErrorMessage("密码错误");
        }
        //将返回的用户实体密码设置为空，不返给前端
        user.setPassword(StringUtils.EMPTY);
        //告知前端登陆成功
        return ServerResponse.createBySuccess("登陆成功", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        //查询用户是否已存在
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        //当用户存在时validResponse.isSuccess()为false，取反进入if
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        //查询email是否已存在
        validResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!validResponse.isSuccess()) {
            return validResponse;
        }
        //设置注册用户角色
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        //插入用户，返回影响行数
        int resultCount = this.userMapper.insert(user);
        //影响行数为0代表插入失败
        if (resultCount == 0) {
            //返给前端
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    @Override
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            //开始校验
            //当输入框失去焦点时校验用户名
            if (StringUtils.equals(Const.USERNAME, type)) {
                int resultCount = this.userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            //当输入框失去焦点时校验email
            if (StringUtils.equals(Const.EMAIL, type)) {
                int resultCount = this.userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createByErrorMessage("Email已存在");
                }
            }
        } else {
            //未输入type时直接返回错误信息
            return ServerResponse.createByErrorMessage("参数错误");
        }
        //校验成功代表不存在
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    @Override
    public ServerResponse<String> selectQuestion(String username) {
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        //当用户不存在时validResponse.isSuccess()为true
        if (validResponse.isSuccess()) {
            //用户名不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //根据用户名查询问题
        String question = this.userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            //问题不为空时将问题返给前端
            return ServerResponse.createBySuccess(question);
        }
        //问题为空时告知前端
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        //根据用户名，问题和答案查询是否有记录
        int resultCount = this.userMapper.checkAnswer(username, question, answer);
        //记录数大于0时
        if (resultCount > 0) {
            //使用UUID生成token
            String forgetToken = UUID.randomUUID().toString();
            //将token加入缓存，key为TokenCache.TOKEN_PREFIX
            TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, forgetToken);
            //将token返给前端
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        //前端将token传回后端
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，token需要传递");
        }
        //因为从缓存中获取token需要用户名，所以再次判断用户名是否存在
        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            //用户名不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //根据key从缓存获取token
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        //判断是否获取到token
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        //判断前后端的token是否相等
        if (StringUtils.equals(forgetToken, token)) {
            //相等则将密码MD5加密
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            //根据用户名修改密码
            int rowCount = this.userMapper.updatePasswordByUsername(username, md5Password);
            //影响函数大于0则告知前端修改成功
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("修改密码成功");
            } else {
                //不相等返回错误
                return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
            }
        }
        //流程走完没修改告知前端失败
        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user) {
        //防止横向越权
        String md5PasswordOld = MD5Util.MD5EncodeUtf8(passwordOld);
        int resultCount = this.userMapper.checkPassword(md5PasswordOld, user.getId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = this.userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }

    @Override
    public ServerResponse<User> updateInfomation(User user) {
        //username不能更新
        //email进行校验
        int resultCount = this.userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email已经存在，请更换email再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount = this.userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }


}
