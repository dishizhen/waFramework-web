package com.wa.framework.user.controller;

import java.io.Writer;
import java.util.List;

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
import com.wa.framework.user.model.SysMenu;
import com.wa.framework.user.service.SysMenuService;
import com.wa.framework.util.easyui.ResponseUtils;

/**
 * 系统菜单管理.
 */
@Controller("/system/menu")
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController<Object, Object> {

    @Autowired
    @Qualifier("sysMenuService")
    private SysMenuService sysMenuService;

    @RequestMapping("/menu")
    @MethodDescription(desc="查询菜单")
    public ModelAndView menu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("/sys/user/menu_list");
        return view;
    }

    @RequestMapping("/menutree")
    public void menuTree(HttpServletRequest request,
            HttpServletResponse response, Writer writer) throws Exception {
        List<SysMenu> list = sysMenuService.findAll(SysMenu.class);
        String json = ResponseUtils.buildTreeJson(list, "id", "parentId", "menuName", "ROOT");
        writer.write(json);
    }

    @RequestMapping("/list")
    @MethodDescription(desc="查询菜单")
    @RequiresPermissions("system.user.menu.query")
    @ResponseBody
    public String listByParentId(HttpServletRequest request, String parentId) {
        DTRequestParamsBean dtParams = PageUtils.getDTParams(request);
        Page page = dtParams.getPage();
        Pageable<SysMenu> pageable = sysMenuService.findMenusByParentId(page,
                parentId);
        return PageUtils.buildDTData(pageable, request);
    }
    
    @RequestMapping("/tree")
    public void getMenuTree(HttpServletRequest request,
            HttpServletResponse response, Writer writer) throws Exception {
        String json = sysMenuService.getTreeJson();
        writer.write(json);
    }

    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    @MethodDescription(desc="增加菜单")
    @RequiresPermissions("system.user.menu.add")
    public void addMenu(SysMenu sysMenu, HttpServletRequest request,
            Writer writer) throws Exception {
    	String json ="";
    	SysMenu sysM=sysMenuService.getCountByOrder(sysMenu.getDisplayOrder());
    	if(sysM!=null){
        	json="{\"result\":\"exsit\"}";
        }else{
        	json = sysMenuService.addMenu(sysMenu);
        }
        writer.write(json);
    }
    

    @RequestMapping(value = "/editMenu", method = RequestMethod.POST)
    @MethodDescription(desc="修改菜单")
    @RequiresPermissions("system.user.menu.edit")
    public void editMenu(SysMenu sysMenu, HttpServletRequest request,
            Writer writer) throws Exception {
    	SysMenu sysM=sysMenuService.getCountByOrder(sysMenu.getDisplayOrder());
    	String json ="";
        if(sysM!=null&&sysMenu.getId().equals(sysM.getId())){
        	json = sysMenuService.editMenu(sysMenu);
        }else if(sysM!=null&&!sysMenu.getId().equals(sysM.getId())){
        	json="{\"result\":\"exsit\"}";
        }else{
        	json = sysMenuService.editMenu(sysMenu);
        }
        writer.write(json);
    }

    @RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
    @MethodDescription(desc="删除菜单")
    @RequiresPermissions("system.user.menu.delete")
    public void deleteMenu(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("id") String id,
            Writer writer) throws Exception {
        String json = sysMenuService.deleteMenu(id);
        writer.write(json);
    }

}
