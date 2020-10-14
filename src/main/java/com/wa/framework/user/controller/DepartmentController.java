package com.wa.framework.user.controller;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wa.framework.Pageable;
import com.wa.framework.common.EscapeChar;
import com.wa.framework.common.PageUtils;
import com.wa.framework.common.DTBean.DTRequestParamsBean;
import com.wa.framework.controller.BaseController;
import com.wa.framework.log.MethodDescription;
import com.wa.framework.user.model.SysDepartment;
import com.wa.framework.user.model.SysUser;
import com.wa.framework.user.service.DepartmentService;
import com.wa.framework.user.service.UserService;
import com.wa.framework.util.easyui.ResponseUtils;

/**
 * 描述：部门管理
 * 创建人：guoyt
 * 创建时间：2016年10月13日上午11:17:21
 * 修改人：guoyt
 * 修改时间：2016年10月13日上午11:17:21
 */
@Controller("/system/department")
@RequestMapping("/system/department")
public class DepartmentController extends BaseController<Object, Object> {
    
    @Autowired
    @Qualifier("userService")
    private UserService userService;
     
    @Autowired
    @Qualifier("departmentService")
    private DepartmentService departmentService;

    @RequestMapping("/department")
    @MethodDescription(desc="查询部门")
    @RequiresPermissions("system.user.department.query")
    public ModelAndView department(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ModelAndView view = new ModelAndView();
        List<SysUser> users = userService.findUserOrderByName();
        view.addObject("users", users);
        view.setViewName("/sys/user/department_list");
        return view;
    }

    @RequestMapping("/list")
    @MethodDescription(desc="查询部门")
    @RequiresPermissions("system.user.department.query")
    @ResponseBody
    public String dpartmentList(HttpServletRequest request, String deptName, String deptCode) throws Exception {
    	 DTRequestParamsBean dtParams = PageUtils.getDTParams(request);
         DetachedCriteria criteria = DetachedCriteria.forClass(SysDepartment.class);
         if (StringUtils.isNotBlank(deptCode)) {
             criteria.add(EscapeChar.fuzzyCriterion("code", deptCode)); // 部门编码
         }
         if (StringUtils.isNotBlank(deptName)) {
             criteria.add(EscapeChar.fuzzyCriterion("DEPARTMENT_NAME", deptName)); // 部门名称
         }
         criteria.addOrder(Order.desc(SysDepartment.PROP_CREATE_DATE));
         Pageable<SysDepartment> pageable = departmentService.getDeptList(criteria, dtParams.getPage());
         return PageUtils.buildDTData(pageable, request);
    }

    @RequestMapping("/tree")
    public void dpartmentTree(HttpServletRequest request,
            HttpServletResponse response, Writer writer) throws Exception {
        List<SysDepartment> list = departmentService.findAll(SysDepartment.class);
        String json = ResponseUtils.buildTreeJson(list, "id", "parentId", "departmentName", "ROOT");
        writer.write(json);
    }

    @RequestMapping("/add")
    @MethodDescription(desc="增加部门")
    @RequiresPermissions("system.user.department.add")
    @ResponseBody
    public void dpartmentAdd(HttpServletRequest request,
            HttpServletResponse response, Writer writer,
            @RequestParam String departmentName, @RequestParam String parentId, @RequestParam String code)
            throws Exception {
        String description = request.getParameter("description");
        String adminUserId = request.getParameter("adminUserId1");
        code = StringUtils.trimToEmpty(code);
        int countName=departmentService.getCountByDptName(departmentName.trim());
        String json ="";
        if (departmentService.checkSameCode(code, null)) {
            json = "{\"result\":\"exsitCode\"}";
        } else if (countName == 0) {
            departmentService.addDepartment(departmentName, parentId, description, adminUserId, code);
            json = ResponseUtils.buildResultJson(true);
        } else {
            json = "{\"result\":\"exsit\"}";
        }
        writer.write(json);
    }

    @RequestMapping("/edit")
    @MethodDescription(desc="修改部门")
    @RequiresPermissions("system.user.department.edit")
    public void dpartmentEdit(HttpServletRequest request,
            HttpServletResponse response, Writer writer,
            @RequestParam String departmentName, @RequestParam String id, @RequestParam String code)
            throws Exception {
        String description = request.getParameter("description");
        String adminUserId = request.getParameter("adminUserId2");
        code = StringUtils.trimToEmpty(code);
        SysDepartment sysDepartment=departmentService.getCountByDpt(departmentName.trim());
        String json ="";
        if (departmentService.checkSameCode(code, id)) {
            json = "{\"result\":\"exsitCode\"}";
        } else if (sysDepartment == null || sysDepartment.getId().equals(id)) {
            departmentService.editDepartment(id, departmentName, description, adminUserId, code);
            json = ResponseUtils.buildResultJson(true);
        } else if (sysDepartment != null && !sysDepartment.getId().equals(id)) {
            json = "{\"result\":\"exsit\"}";
        }
        writer.write(json);
    }

    @RequestMapping("/delete")
    @MethodDescription(desc="删除部门")
    @RequiresPermissions("system.user.department.delete")
    public void dpartmentDelete(HttpServletRequest request,
            HttpServletResponse response, Writer writer, @RequestParam String id)
            throws Exception {
        boolean result = departmentService.deleteDepartment(id);
        if (result) {
            String json = ResponseUtils.buildResultJson(true);
            writer.write(json);
        } else {
            String json = ResponseUtils.buildResultJson(false, "1");
            writer.write(json);
        }
    }
    
    /**
     * @category 获取部门列表
     * @param request
     * @param response
     * @param writer
     * @throws Exception
     */
    @RequestMapping("/getDeptList")
    public void getDeptList(HttpServletRequest request, Integer type, HttpServletResponse response, Writer writer)
                    throws Exception {
        List<SysDepartment> list = departmentService.find(SysDepartment.class, SysDepartment.PROP_DEPARTMENT_NAME);
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (SysDepartment sys : list) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("id", sys.getId());
            resultMap.put("text", sys.getDepartmentName());
            resultList.add(resultMap);
        }
        String json = ResponseUtils.toJSONString(resultList);
        writer.write(json);
    }
}
