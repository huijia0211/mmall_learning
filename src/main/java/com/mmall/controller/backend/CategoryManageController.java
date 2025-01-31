package com.mmall.controller.backend;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Admin
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
//        Session
//        User user = (User) session.getAttribute(Const.CURRENT_USER);

//        Cookie Redis
//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isBlank(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登陆，无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登录");
//        }
//        //校验一下是否是管理员
//        if (this.iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            return this.iCategoryService.addCategory(categoryName, parentId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }
        //全部通过拦截器验证是否登录以及权限
        return this.iCategoryService.addCategory(categoryName, parentId);
    }

    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);

//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isBlank(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登陆，无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登录");
//        }
//        //校验一下是否是管理员
//        if (this.iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            return this.iCategoryService.updateCategory(categoryId, categoryName);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }
        //全部通过拦截器验证是否登录以及权限
        return this.iCategoryService.updateCategory(categoryId, categoryName);
    }

    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);

//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isBlank(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登陆，无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登录");
//        }
//        //校验一下是否是管理员
//        if (this.iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            //查询子节点的category信息
//            return this.iCategoryService.getChildrenParallelCategory(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及权限
        return this.iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest request, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);

//        String loginToken = CookieUtil.readLoginToken(request);
//        if (StringUtils.isBlank(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登陆，无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登录");
//        }
//        //校验一下是否是管理员
//        if (this.iUserService.checkAdminRole(user).isSuccess()) {
//            //是管理员
//            return this.iCategoryService.selectCategoryAndChildrenById(categoryId);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及权限
        return this.iCategoryService.selectCategoryAndChildrenById(categoryId);
    }

}
