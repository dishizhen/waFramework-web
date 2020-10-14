package com.wa.framework.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wa.framework.common.ComConstants;
import com.wa.framework.common.CommonUtil;
import com.wa.framework.controller.BaseController;
import com.wa.framework.security.user.LoginedUser;
import com.wa.framework.user.service.MemCacheService;
import com.wa.framework.user.service.UserService;

/**
 * 描述：登录管理
 * 创建人：guoyt
 * 创建时间：2016年10月12日下午4:45:42
 * 修改人：guoyt
 * 修改时间：2016年10月12日下午4:45:42
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController<Object, Object> {

    @Autowired
    private UserService userService;

    @Resource
    private MemCacheService memCacheService;

    /**
     * @Description: 登录
     * @param: @param request
     * @param: @return
     * @return: ModelAndView
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping(value = "/login")
    public ModelAndView showLoginForm(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        String error = (String) request.getAttribute("shiroLoginFailure");
        modelAndView.addObject("message", error);
        return modelAndView;
    }

    /**
     * @Description: 跳转首页
     * @param: @return
     * @return: ModelAndView
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("index")
    public ModelAndView index() {
        LoginedUser loginUser = CommonUtil.getCurrentUser();
        if (loginUser == null){
            ModelAndView modelAndView = new ModelAndView("login");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("version", ComConstants.VERSION_NO);
            return modelAndView;
        }
    }
    
    /**
     * @Description: 刷新缓存
     * @param: @param session
     * @param: @param request
     * @param: @throws Exception
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/refreshCache")
    public void refreshCache(HttpSession session, HttpServletRequest request) throws Exception {
        memCacheService.refreshCache();
    }

}
