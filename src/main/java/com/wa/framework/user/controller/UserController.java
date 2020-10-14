package com.wa.framework.user.controller;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wa.framework.util.easyui.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.wa.framework.Pageable;
import com.wa.framework.common.CommonUtil;
import com.wa.framework.common.PageUtils;
import com.wa.framework.common.PropertyConfigurer;
import com.wa.framework.common.DTBean.DTRequestParamsBean;
import com.wa.framework.controller.BaseController;
import com.wa.framework.log.MethodDescription;
import com.wa.framework.user.model.SysUser;
import com.wa.framework.user.service.UserService;
import com.wa.framework.user.vo.SysUserVo;
import com.wa.framework.util.easyui.ResponseUtils;

/**
 * 用户管理.
 * @author Administrator
 *
 */
@Controller("/system/user/")
@RequestMapping("/system/user/")
public class UserController extends BaseController<Object, Object> {

    @Autowired
    @Qualifier("userService")
    private UserService userService;
    
    @RequestMapping("/editPwd")
    @MethodDescription(desc="用户修改密码")
    public void updatePwd(HttpServletRequest request,
            HttpServletResponse response, Writer writer) throws Exception {
        String id = request.getParameter("managerId");
        String oldPwd = request.getParameter("oldPwd");
        String password = request.getParameter("newPwd");
        Map<String, Object> result = userService.updatePassword(id, oldPwd, password);
        String json = ResponseUtils.buildResultJson(result);
        writer.write(json);
    }

    @RequestMapping("/user")
    @MethodDescription(desc="查询用户")
    @RequiresPermissions("system.user.user.query")
    public ModelAndView user(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("/sys/user/user_list");
        return view;
    }

    @RequestMapping("/list")
    @MethodDescription(desc="查询用户")
    @RequiresPermissions("system.user.user.query")
    @ResponseBody
    public String userList(HttpServletRequest request) throws Exception {
    	DTRequestParamsBean dtParams = PageUtils.getDTParams(request);
    	String username = request.getParameter("username");
    	String depart = request.getParameter("depart");
    	String status = request.getParameter("status");
        Pageable<SysUser> page = userService.findUserWithName(dtParams.getPage(),
                        username, depart, status);
        return PageUtils.buildDTData(page, request);
    }

    @RequestMapping("/listByDepartmentId")
    public void listByDepartmentId(HttpServletRequest request,
            HttpServletResponse response, @RequestParam String departmentId,
            Writer writer) throws Exception {
        List<SysUser> list = userService.findUserByDepartmentId(departmentId);
        for (SysUser user : list) {
            user.setSysDepartment(null);// 断开user与department的循环关联关系
        }
        String json = ResponseUtils.toJSONString(list);
        writer.write(json);
    }

    @RequestMapping("/add")
    @MethodDescription(desc="增加用户")
    @RequiresPermissions("system.user.user.add")
    public void userAdd(HttpServletRequest request,
            HttpServletResponse response, Writer writer, SysUser user,
            @RequestParam String departmentId) throws Exception {
        int result = userService.addUser(user, departmentId);
        String json = ResponseUtils.buildResultJson((result == 0), String.valueOf(result));
        writer.write(json);
    }

    @RequestMapping("/delete")
    @MethodDescription(desc="删除用户")
    @RequiresPermissions("system.user.user.delete")
    public void userDelete(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("id") String id,
            Writer writer) throws Exception {
        userService.deleteUserInlogic(id);
        String json = ResponseUtils.buildResultJson(true);
        writer.write(json);
    }

    @RequestMapping("/enable")
    @MethodDescription(desc="启用/禁用用户")
    @RequiresPermissions("system.user.user.enable")
    public void userEnable(HttpServletRequest request,
            HttpServletResponse response, @RequestParam String id, Writer writer)
            throws Exception {
        userService.enable(id);
        String json = ResponseUtils.buildResultJson(true);
        writer.write(json);
    }

    @RequestMapping("/onEdit")
    @ResponseBody
    public void onEdit(HttpServletRequest request, HttpServletResponse response, String userId, Writer writer)
            throws Exception {
        Map<String, Object> user = userService.getUserById(userId);
        String json = ResponseUtils.toJSONString(user);
        writer.write(json);
    }
    
    @RequestMapping("/edit")
    @MethodDescription(desc="修改用户信息")
    @RequiresPermissions("system.user.user.edit")
    public void userEdit(HttpServletRequest request,
            HttpServletResponse response, SysUser user,
            @RequestParam String userId, @RequestParam String departmentId,
            Writer writer) throws Exception {
        userService.updateUser(user, userId, departmentId);
        String json = ResponseUtils.buildResultJson(true);
        writer.write(json);
    }

    @RequestMapping("/resetPassword")
    @MethodDescription(desc="重置用户密码")
    @RequiresPermissions("system.user.user.resetPassword")
    public void resetPassword(HttpServletRequest request, HttpServletResponse response, @RequestParam String id,
            Writer writer) throws Exception {
        String password = PropertyConfigurer.getValue("reset.password");
        userService.resetPassword(id, password);
        String json = ResponseUtils.buildResultJson(true);
        writer.write(json);
    }

    @RequestMapping("/roleIdsAndPrivilegeIds")
    @RequiresPermissions("system.user.user.grant")
    public void roleIdsAndPrivilegeIds(HttpServletRequest request,
            HttpServletResponse response, @RequestParam String id, Writer writer)
            throws Exception {
        Map<String, List<Object>> map = userService.getRoleIdsAndPrivilegeIdsByUserId(id);
        String json = JSON.toJSONString(map);
        writer.write(json);
    }

    @RequestMapping("/grant")
    @MethodDescription(desc="用户授权")
    @RequiresPermissions("system.user.user.grant")
    public void grant(HttpServletRequest request, HttpServletResponse response,
            @RequestParam String userId, Writer writer) throws Exception {
        String roleIds = request.getParameter("roleIds");
        String privilegeIds = request.getParameter("privilegeIds");
        String[] rids = null;
        String[] pids = null;
        if (roleIds != null && roleIds.length() > 0) {
            rids = roleIds.split(";");
        }
        if (privilegeIds != null && privilegeIds.length() > 0) {
            pids = privilegeIds.split(";");
        }
        userService.grantUser(userId, rids, pids);
        String json = ResponseUtils.buildResultJson(true);
        writer.write(json);
    }

    /**
     * @Description: 获取所有激活用户列表，供表单下拉框使用
     * @param: @param request
     * @param: @param response
     * @param: @param writer
     * @param: @throws Exception
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/alluser")
    public void alluser(HttpServletRequest request, HttpServletResponse response, Writer writer) throws Exception {
        List<SysUser> users = userService.findUserOrderByName();
        List<SysUserVo> uservos = new ArrayList<SysUserVo>();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        for(SysUser user : users) {
            SysUserVo uservo = new SysUserVo();
            uservo.setId(user.getId());
            Map<String, Object> resultMap = new HashMap<String, Object>();

            if (StringUtils.isNotEmpty(user.getRealName())){
                uservo.setName(user.getRealName());
                resultMap.put("id",user.getId());
                resultMap.put("text",user.getRealName());
            } else {
                uservo.setName(user.getUsername());
                resultMap.put("id",user.getId());
                resultMap.put("text",user.getUsername());
            }
            uservos.add(uservo);
            resultList.add(resultMap);
        }
        String json = ResponseUtils.toJSONString(uservos);
        String jsonList = ResponseUtils.toJSONString(resultList);
        writer.write(jsonList);
    }
    
    /**
     * @Description: 获取所有激活用户列表，供表单下拉框使用
     * @param: @param request
     * @param: @param response
     * @param: @param writer
     * @param: @throws Exception
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/allUserNotInSelf")
    public void allUserNotInSelf(HttpServletRequest request, HttpServletResponse response, Writer writer) throws Exception {
        List<SysUser> users = userService.findUserOrderByName();
        List<SysUserVo> uservos = new ArrayList<SysUserVo>();
        
        String userId = CommonUtil.getCurrentUserId();
        for(SysUser user : users) {
            if (!user.getId().equals(userId)){
                SysUserVo uservo = new SysUserVo();
                uservo.setId(user.getId());
                if (StringUtils.isNotEmpty(user.getRealName())){
                    uservo.setName(user.getRealName());
                } else {
                    uservo.setName(user.getUsername());
                }
                uservos.add(uservo);
            }
        }
        String json = ResponseUtils.toJSONString(uservos);
        writer.write(json);
    }

}
