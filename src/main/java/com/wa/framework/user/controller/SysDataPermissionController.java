package com.wa.framework.user.controller;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wa.framework.QueryCondition;
import com.wa.framework.controller.BaseController;
import com.wa.framework.log.MethodDescription;
import com.wa.framework.user.model.SysDataPermissionFilter;
import com.wa.framework.user.service.SysMenuService;
import com.wa.framework.util.easyui.ResponseUtils;

/**
 * 描述：数据权限管理
 * 创建人：guoyt
 * 创建时间：2016年10月13日下午2:09:46
 * 修改人：guoyt
 * 修改时间：2016年10月13日下午2:09:46
 */
@Controller("/system/datapermission")
@RequestMapping("/system/datapermission")
public class SysDataPermissionController extends BaseController<Object, Object> {

    @Autowired
    @Qualifier("sysMenuService")
    private SysMenuService sysMenuService;

    @RequestMapping("/datapermission")
    @MethodDescription(desc = "查询数据权限")
    @RequiresPermissions("system.user.datapermission.query")
    public ModelAndView menu(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        view.setViewName("/sys/user/data_permission_list");
        return view;
    }

    @RequestMapping("/tree")
    public void getMenuTree(HttpServletRequest request, HttpServletResponse response, Writer writer) throws Exception {
        String json = sysMenuService.getTreeJson();
        writer.write(json);
    }

    @ResponseBody
    @RequestMapping("/getFilter/{menuId}")
    public SysDataPermissionFilter findByModuleId(@PathVariable String menuId) {
        SysDataPermissionFilter filter = baseService.unique(SysDataPermissionFilter.class,
                        QueryCondition.eq(SysDataPermissionFilter.PROP_MENU, menuId));
        return filter;
    }

    @ResponseBody
    @RequestMapping("/saveOrUpdate")
    @MethodDescription(desc = "修改数据权限")
    @RequiresPermissions("system.user.datapermission.edit")
    public String saveConfig(@RequestBody SysDataPermissionFilter filter) {
        if (StringUtils.isNotBlank(filter.getId())) {
            baseService.update(filter);
        } else {
            baseService.add(filter);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", true);
        map.put("id", filter.getId());
        return ResponseUtils.toJSONString(map);
    }
}
