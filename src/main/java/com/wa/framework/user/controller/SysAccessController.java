package com.wa.framework.user.controller;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.common.PageUtils;
import com.wa.framework.common.DTBean.DTRequestParamsBean;
import com.wa.framework.controller.BaseController;
import com.wa.framework.log.MethodDescription;
import com.wa.framework.user.service.SysAccessService;

/**
 * 描述：权限管理
 * 创建人：guoyt
 * 创建时间：2016年10月13日下午2:09:46
 * 修改人：guoyt
 * 修改时间：2016年10月13日下午2:09:46
 */
@Controller("/system/access")
@RequestMapping("/system/access")
public class SysAccessController extends BaseController<Object, Object> {

    @Autowired
    @Qualifier("sysAccessService")
    private SysAccessService sysAccessService;

    @RequestMapping("/access")
    @MethodDescription(desc="查询功能权限")
    @RequiresPermissions("system.user.access.query")
    public ModelAndView menu(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("/sys/user/access_list");
        return view;
    }

    @RequestMapping("/tree")
    public void getMenuTree(HttpServletRequest request,
            HttpServletResponse response, Writer writer) throws Exception {
        String json = sysAccessService.getTreeJson();
        writer.write(json);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping("/list")
    @MethodDescription(desc="查询功能权限")
    @RequiresPermissions("system.user.access.query")
    @ResponseBody
    public String listByParentId(HttpServletRequest request,
            String parentId)
            throws Exception {
        DTRequestParamsBean dtParams = PageUtils.getDTParams(request);
        Page page = dtParams.getPage();
        Pageable pageable = sysAccessService.findMenusByParentIdPage(page, parentId);
        return PageUtils.buildDTData(pageable, request);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/addAccess", method = RequestMethod.POST)
    @MethodDescription(desc="增加功能权限")
    @RequiresPermissions("system.user.access.add")
    public void addAccess(@RequestParam("menuId") String menuId,
            HttpServletRequest request, Writer writer) throws Exception {
        
        Map<String, String[]> requestParamMap = request.getParameterMap();
        String[] privilegeCodeArr =  requestParamMap.get("privilegeCode");
        String[] privilegeNameArr = requestParamMap.get("privilegeName");
        String[] desArr = requestParamMap.get("description");
        
        String json = sysAccessService.addAccess(menuId, privilegeCodeArr,
                        privilegeNameArr, desArr);
        writer.write(json);
    }

    @RequestMapping(value = "/editAccess", method = RequestMethod.POST)
    @MethodDescription(desc="修改功能权限")
    @RequiresPermissions("system.user.access.edit")
    public void editAccess(@RequestParam("privilegeId") String privilegeId,
            @RequestParam("privilegeCode") String privilegeCode,
            @RequestParam("privilegeName") String privilegeName,
            @RequestParam("privilegeOldName") String privilegeOldName,
            @RequestParam("editMenuId") String editMenuId,
            @RequestParam("description") String description,
            HttpServletRequest request, Writer writer) throws Exception {
        String json = sysAccessService.editAccess(privilegeId, privilegeCode,
                privilegeName, privilegeOldName, editMenuId, description);
        writer.write(json);
    }

    @RequestMapping(value = "/deleteAccess", method = RequestMethod.POST)
    @MethodDescription(desc="删除功能权限")
    @RequiresPermissions("system.user.access.delete")
    public void deleteAccess(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("id") String id,
            Writer writer) throws Exception {
        String json = sysAccessService.deleteAccess(id);
        writer.write(json);
    }
    
    /**
     * @Description: 用户授权时获取的权限树，已授权的需要打勾
     * @param: @param request
     * @param: @param response
     * @param: @param id
     * @param: @param writer
     * @param: @throws Exception
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/userPrivilege")
    @MethodDescription(desc="用户授权")
    public void userPrivilegeList(HttpServletRequest request,
            HttpServletResponse response, @RequestParam String id, Writer writer)
            throws Exception {
        String json = sysAccessService.getUserPrivilegeJson(id);
        writer.write(json);
    }
    
    /**
     * @Description: 新增角色时获取的权限树
     * @param: @param request
     * @param: @param response
     * @param: @param writer
     * @param: @throws Exception
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/privilegeList")
    @MethodDescription(desc="新增角色")
    public void privilegeList(HttpServletRequest request,
            HttpServletResponse response, Writer writer) throws Exception {
        String json = sysAccessService.getPrivilegeJson();
        writer.write(json);
    }

    /**
     * @Description: 角色授权时获取的权限树，已授权的需要打勾
     * @param: @param request
     * @param: @param response
     * @param: @param id
     * @param: @param writer
     * @param: @throws Exception
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @RequestMapping("/rolePrivilege")
    @MethodDescription(desc="角色授权")
    public void rolePrivilegeList(HttpServletRequest request,
            HttpServletResponse response, @RequestParam String id, Writer writer)
            throws Exception {
        String json = sysAccessService.getRolePrivilegeJson(id);
        writer.write(json);
    }

}
