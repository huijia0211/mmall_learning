package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import com.mmall.vo.OrderVo;
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
@RequestMapping("/manage/order/")
public class OrdermanageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpServletRequest request,
                                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
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
//            return this.iOrderService.manageList(pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及权限
        return this.iOrderService.manageList(pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpServletRequest request, Long orderNo) {
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
//            return this.iOrderService.manageDetail(orderNo);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及权限
        return this.iOrderService.manageDetail(orderNo);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest request,
                                                Long orderNo,
                                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
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
//            return this.iOrderService.manageSearch(orderNo, pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及权限
        return this.iOrderService.manageSearch(orderNo, pageNum, pageSize);
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpServletRequest request, Long orderNo) {
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
//            return this.iOrderService.manageSendGoods(orderNo);
//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //全部通过拦截器验证是否登录以及权限
        return this.iOrderService.manageSendGoods(orderNo);
    }

}
