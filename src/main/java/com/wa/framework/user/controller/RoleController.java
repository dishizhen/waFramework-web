package com.wa.framework.user.controller;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wa.framework.Pageable;
import com.wa.framework.common.CacheConstants;
import com.wa.framework.common.PageUtils;
import com.wa.framework.common.DTBean.DTRequestParamsBean;
import com.wa.framework.common.cache.CacheFactory;
import com.wa.framework.controller.BaseController;
import com.wa.framework.log.MethodDescription;
import com.wa.framework.user.model.SysRole;
import com.wa.framework.user.service.RoleService;
import com.wa.framework.util.easyui.ResponseUtils;

/**
 * 角色管理.
 * @author Administrator
 *
 */
@Controller("/system/role")
@RequestMapping("/system/role")
public class RoleController extends BaseController<Object, Object> {

    @Autowired
    @Qualifier("roleService")
    private RoleService roleService;

    @RequestMapping("/role")
    @MethodDescription(desc="查询角色")
    @RequiresPermissions("system.user.role.query")
    public ModelAndView role(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("/sys/user/role_list");
        return view;
    }

    @RequestMapping("/list")
    @MethodDescription(desc="查询角色")
    @RequiresPermissions("system.user.role.query")
    public void roleList(HttpServletRequest request,
            HttpServletResponse response, Writer writer) throws Exception {
    	 DTRequestParamsBean dtParams = PageUtils.getDTParams(request);
    	 Pageable<SysRole> pageable=baseService.findWithPage(SysRole.class, dtParams.getPage(), SysRole.PROP_CREATE_DATE, false);
    	 String json = PageUtils.buildDTData(pageable, request);
         writer.write(json);
    }

    @RequestMapping("/addRole")
    @MethodDescription(desc="新增角色")
    @RequiresPermissions("system.user.role.add")
    @ResponseBody
    public void addRole(HttpServletRequest request, HttpServletResponse response, Writer writer)
            throws Exception {
        String roleName = request.getParameter("roleName");
        String description = request.getParameter("description");
        String privilegeIds = request.getParameter("privilegeIds");
        String[] ids = null;
        if (privilegeIds != null && privilegeIds.length() > 0) {
            ids = privilegeIds.split(";");
        }
        boolean result = roleService.addRole(roleName, description, ids);
        String json = ResponseUtils.buildResultJson(result);
        writer.write(json);
    }

    @RequestMapping("/deleteRole")
    @MethodDescription(desc="删除角色")
    @RequiresPermissions("system.user.role.delete")
    public void roleDelete(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("id") String id,
            Writer writer) throws Exception {
        baseService.deleteById(SysRole.class, id);
        
        // 移除缓存中的角色权限关系
        CacheFactory.getInstance().removeCacheByKey(CacheConstants.ROLE_PRIVILEGE_MAP, id);
        String json = ResponseUtils.buildResultJson(true);
        writer.write(json);
    }
    
    /**
    *
    * <描述>: 打开修改页面
    * @author 作者：何斐
    * @version 创建时间：2016年11月15日上午9:37:31
    * @param request
    * @param response
    * @param user
    * @param userId
    * @param writer
    * @throws Exception
    */
   @RequestMapping("/onEdit")
   @ResponseBody
   public void onEdit(HttpServletRequest request, HttpServletResponse response, String id, Writer writer) throws Exception {
       SysRole sysRole = roleService.getSysRoleById(id);
       String json = ResponseUtils.toJSONString(sysRole);
       writer.write(json);
   }

    @RequestMapping("/editRole")
    @MethodDescription(desc="角色授权")
    @RequiresPermissions("system.user.role.grant")
    public void editRole(HttpServletRequest request,
            HttpServletResponse response, @RequestParam String roleId,
            @RequestParam String roleName, Writer writer) throws Exception {
        String description = request.getParameter("description");
        String privilegeIds = request.getParameter("privilegeIds");
        String[] ids = null;
        if (privilegeIds != null && privilegeIds.length() > 0) {
            ids = privilegeIds.split(";");
        }
        boolean result = roleService.updateRole(roleId, roleName, description, ids);
        String json = ResponseUtils.buildResultJson(result);
        writer.write(json);
    }
}
