package com.wa.framework.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.QueryConditions;
import com.wa.framework.log.ExpLog;
import com.wa.framework.service.BaseService;
import com.wa.framework.user.dao.SysDepartmentDao;
import com.wa.framework.user.model.SysDepartment;
import com.wa.framework.user.model.SysUser;

/**
 * 描述：部门管理service
 * 创建人：guoyt
 * 创建时间：2016年10月21日上午11:18:05
 * 修改人：guoyt
 * 修改时间：2016年10月21日上午11:18:05
 */
@Service("departmentService")
@ExpLog(type="部门管理")
public class DepartmentService extends BaseService {

    @Autowired
    @Qualifier("departmentDao")
    private SysDepartmentDao departmentDao;

    /**
     * @Description: 增加部门
     * @param: @param name
     * @param: @param parentId
     * @param: @param description
     * @param: @param adminUserId
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    public void addDepartment(String name, String parentId, String description,String adminUserId, String code) {
        SysDepartment department = new SysDepartment();
        department.setDepartmentName(name);
        department.setDescription(description);
        department.setAdminUserId(adminUserId);
        department.setParentId(parentId);
        department.setCode(code);
        if (StringUtils.isNotBlank(adminUserId)){
        	SysUser user = baseDao.get(SysUser.class, adminUserId);
        	user.setIsDepatmentAdmin("1");
        	baseDao.update(user);
        }
        departmentDao.save(department);
    }

    /**
     * @Description: 修改部门
     * @param: @param id
     * @param: @param name
     * @param: @param description
     * @param: @param adminUserId
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    public void editDepartment(String id, String name, String description, String adminUserId, String code) {
        SysDepartment department = departmentDao.get(id);
        String oldAdminUserId = department.getAdminUserId();
        department.setDepartmentName(name);
        department.setAdminUserId(adminUserId);
        department.setDescription(description);
        department.setCode(code);
        if (StringUtils.isNotBlank(adminUserId)){
        	SysUser user = baseDao.get(SysUser.class, adminUserId);
        	user.setIsDepatmentAdmin("1");
        	baseDao.update(user);
        } 
        if (StringUtils.isNotBlank(oldAdminUserId)){
        	// 如果修改负责人为空，需要设置原来旧的用户是否负责人为0，设置之前需要查看还有没有其他人设置此用户为负责人
        	QueryConditions queryConditions = new QueryConditions();
        	queryConditions.addEq(SysDepartment.PROP_ADMIN_USERID, oldAdminUserId);
        	List<SysDepartment> depList = find(SysDepartment.class, queryConditions);
        	if (depList != null && depList.size() < 1) {
        		SysUser user = baseDao.get(SysUser.class, oldAdminUserId);
            	user.setIsDepatmentAdmin("0");
            	baseDao.update(user);
        	}
        }
        departmentDao.update(department);
    }

    /**
     * @Description: 删除部门
     * @param: @param id
     * @param: @return
     * @return: boolean
     * @throws
     * @since JDK 1.6
     */
    public boolean deleteDepartment(String id) {
        int subCount = departmentDao.getSubCountByParentId(id);
        if (subCount == 0) {
            SysDepartment department = departmentDao.get(id);
            if (department.getSysUsers() != null && department.getSysUsers().size() > 0) {
                return false;
            }
            
            String oldAdminUserId = department.getAdminUserId();
            if (StringUtils.isNotBlank(oldAdminUserId)){
            	// 如果删除部门，需要设置原来旧的用户是否负责人为0，设置之前需要查看还有没有其他人设置此用户为负责人
            	QueryConditions queryConditions = new QueryConditions();
            	queryConditions.addEq(SysDepartment.PROP_ADMIN_USERID, oldAdminUserId);
            	List<SysDepartment> depList = find(SysDepartment.class, queryConditions);
            	if (depList != null && depList.size() <= 1) {
            		SysUser user = baseDao.get(SysUser.class, oldAdminUserId);
                	user.setIsDepatmentAdmin("0");
                	baseDao.update(user);
            	}
            }
            
            departmentDao.delete(department);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @Description: 判断部门名称有无相同的
     * @param: @param dptName
     * @param: @return
     * @return: Integer
     * @throws
     * @since JDK 1.6
     */
    public Integer getCountByDptName(String dptName){
    	int count=departmentDao.getCountByDptName(dptName);
		return count;
    }
    
    /**
     * @Description: 判断同一个父部门下有无部门名称相同的
     * @param: @param dptName
     * @param: @param parentId
     * @param: @return
     * @return: Integer
     * @throws
     * @since JDK 1.6
     */
    public Integer getCountByDptName(String dptName, String parentId){
    	int count=departmentDao.getCountByDptName(dptName, parentId);
		return count;
    }
    
    /**
     * @Description: 根据部门名称查询对象
     * @param: @param dptName
     * @param: @return
     * @return: SysDepartment
     * @throws
     * @since JDK 1.6
     */
    public SysDepartment getCountByDpt(String dptName){
    	SysDepartment sysDpt=null;
    	List<SysDepartment> lst=departmentDao.getCountByDptList(dptName);
    	if(lst.size()!=0){
    		sysDpt=lst.get(0);
    	}
    	return sysDpt;
    }
    
    /**
	 * @category 校验是否存在此code
	 * @param code
	 * @param id
	 * @return
	 */
	public boolean checkSameCode(String code, String id) {
		return departmentDao.checkSameCode(code, id);
	}
	
    /**
     * @Description: 查询所有父级部门
     * @param: @param sysDepartments
     * @param: @param childValue
     * @param: @return
     * @return: List<SysDepartment>
     * @throws
     * @since JDK 1.6
     */
    public List<SysDepartment> getParentMenus(List<SysDepartment> sysDepartments, String childValue){
    	List<SysDepartment> parentList = new ArrayList<SysDepartment>();
		for (SysDepartment sysDepartment : sysDepartments) {
			String id = sysDepartment.getId();
			if (!id.equals("ROOT")){
				if (id == childValue || (childValue != null && id.equals(childValue))) {
					String parentId = sysDepartment.getParentId();
					List<SysDepartment> subList = getParentMenus(sysDepartments, parentId);
					if (subList.size() > 0) {
						parentList.addAll(subList);
					}
					parentList.add(sysDepartment);
				}
			}
		}
        return parentList;
    }
  
    
    /**
     * @Description: 根据部门名称和部门负责人查询所有部门
     * @param: @param page
     * @param: @param departmentName
     * @param: @param adminUsername
     * @param: @return
     * @return: List<SysDepartment>
     * @throws
     * @since JDK 1.6
     */
    public List<SysDepartment> findDepartmentsByName(Page page,String departmentName,String adminUsername){
        List<SysDepartment> list = departmentDao.findDepartmentsByName(page,departmentName,adminUsername);
        Set<SysDepartment> depSet  =new HashSet<SysDepartment>();
        List<SysDepartment> departlist = new ArrayList<SysDepartment>();
        for(SysDepartment dep: list){
           List<SysDepartment> depList = departmentDao.findParentDeparments(dep.getId());
           for(SysDepartment dep1:depList){
               depSet.add(dep1);
           }
        }
        for(SysDepartment dep2: depSet){
            departlist.add(dep2);
        }
        return departlist;
    }
    
    /**
     * @Description: 根据查询条件分页查询
     * @param: @param criteria
     * @param: @param page
     * @param: @return
     * @return: Pageable<SysDepartment>
     * @throws
     * @since JDK 1.6
     */
    public Pageable<SysDepartment> getDeptList(DetachedCriteria criteria, Page page) {
		return departmentDao.getDeptList(criteria, page);
	}
}
