package com.wa.framework.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.dao.BaseEntityDaoImpl;
import com.wa.framework.user.dao.SysDepartmentDao;
import com.wa.framework.user.model.SysDepartment;
import com.wa.framework.user.model.base.BaseSysDepartment;

/**
 * 描述：部门dao实现类
 * 创建人：guoyt
 * 创建时间：2016年10月21日上午11:19:15
 * 修改人：guoyt
 * 修改时间：2016年10月21日上午11:19:15
 */
@Repository("departmentDao")
public class SysDepartmentDaoImpl extends BaseEntityDaoImpl<SysDepartment> implements SysDepartmentDao {

	/** 
	 * @Description: 根据部门id查询有无子部门，返回0代表没有，其他代表有
	 * @see： @see com.wa.framework.user.dao.SysDepartmentDao#getSubCountByParentId(java.lang.String)
	 * @since JDK 1.6
	 */
	@Override
	public Integer getSubCountByParentId(String parentId) {
		Criteria criteria = this.getSession().createCriteria(SysDepartment.class);
		criteria.add(Restrictions.eq("parentId", parentId));
		criteria.setProjection(Projections.rowCount());
		int totalRecords = ((Long) criteria.uniqueResult()).intValue();
		return totalRecords;
	}

	/** 
	 * @Description: 判断部门名称有无相同的，返回0代表没有，其他代表有
	 * @see： @see com.wa.framework.user.dao.SysDepartmentDao#getCountByDptName(java.lang.String)
	 * @since JDK 1.6
	 */
	@Override
	public Integer getCountByDptName(String departmentName) {
		Criteria criteria = this.getSession().createCriteria(SysDepartment.class);
		criteria.add(Restrictions.eq("departmentName", departmentName));
		criteria.setProjection(Projections.rowCount());
		int totalRecords = ((Long) criteria.uniqueResult()).intValue();
		return totalRecords;
	}

	/** 
	 * @Description: 根据部门名称查询对象
	 * @see： @see com.wa.framework.user.dao.SysDepartmentDao#getCountByDptList(java.lang.String)
	 * @since JDK 1.6
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysDepartment> getCountByDptList(String departmentName) {
		Criteria criteria = this.getSession().createCriteria(SysDepartment.class);
		criteria.add(Restrictions.eq("departmentName", departmentName));
		 List<SysDepartment> lst=criteria.list();
		return lst;
	}

	/** 
	 * @Description: 判断部门名称有无相同的，返回0代表没有，其他代表有
	 * @see： @see com.wa.framework.user.dao.SysDepartmentDao#getCountByDptName(java.lang.String, java.lang.String)
	 * @since JDK 1.6
	 */
	@Override
	public Integer getCountByDptName(String dptName, String parentId) {
		Criteria criteria = this.getSession().createCriteria(SysDepartment.class);
		criteria.add(Restrictions.eq("departmentName", dptName));
		criteria.add(Restrictions.eq(BaseSysDepartment.PROP_PARENT_ID, parentId));
		criteria.setProjection(Projections.rowCount());
		int totalRecords = ((Long) criteria.uniqueResult()).intValue();
		return totalRecords;
	}

    /** 
     * @Description: 根据部门名称和部门负责人查询所有部门
     * @see： @see com.wa.framework.user.dao.SysDepartmentDao#findDepartmentsByName(com.wa.framework.Page, java.lang.String, java.lang.String)
     * @since JDK 1.6
     */
    @Override
    public List<SysDepartment> findDepartmentsByName(Page page, String dptName, String adName) {
        List<SysDepartment> depList=new  ArrayList<SysDepartment>();
        Map<String,String> map = new HashMap<String,String>();
        StringBuffer createSqlQuery=new  StringBuffer("select d.*,u.USERNAME from sys_department d left join sys_user u on d.ADMIN_USER_ID = u.SYS_USER_ID where 1=1");
        
        if (StringUtils.isNotEmpty(dptName)){
            if(dptName.contains("%")||dptName.contains("_")){
                dptName=dptName.replace("%","\\%").replace("_", "\\_");
                createSqlQuery.append(" and d.DEPARTMENT_NAME like :departmentName  escape '\\' ");
                map.put("departmentName", "%"+ dptName+ "%");
            }else{
                createSqlQuery.append(" and d.DEPARTMENT_NAME like :departmentName  ");
                map.put("departmentName", "%"+ dptName+ "%");
            }
        }
        
        if (StringUtils.isNotEmpty(adName)){
            if(adName.contains("%")||adName.contains("_")){
                adName=adName.replace("%","\\%").replace("_", "\\_");
                createSqlQuery.append(" and u.USERNAME like  :username  escape '\\' ");
                map.put("username", "%"+ adName+ "%");
            }else{
                createSqlQuery.append(" and u.USERNAME like :username  ");
                map.put("username", "%"+ adName+ "%");
            }
        }
        
        createSqlQuery.append(" order by d.create_Date desc ");
        depList = findBySql(createSqlQuery.toString(), map, SysDepartment.class);
        return depList;

    }

    /** 
     * @Description: 根据部门名称和部门负责人查询所有部门
     * @see： @see com.wa.framework.user.dao.SysDepartmentDao#findParentDeparments(java.lang.String)
     * @since JDK 1.6
     */
    @Override
    public List<SysDepartment> findParentDeparments(String departmentId) {
        String sql = "select  g.* from sys_department g start with g.sys_department_id='"+departmentId+"'"+
                     "connect by prior g.parent_id=g.sys_department_id";
        List<SysDepartment> list = findBySql(sql, SysDepartment.class);
        return list;
    }
    
    /** 
     * @Description: 根据查询条件分页查询
     * @see： @see com.wa.framework.user.dao.SysDepartmentDao#getDeptList(org.hibernate.criterion.DetachedCriteria, com.wa.framework.Page)
     * @since JDK 1.6
     */
    @Override
	public Pageable<SysDepartment> getDeptList(DetachedCriteria criteria, Page page) {
		return findByDetachedCriteriaWithPage(criteria, page);
	}
    
    /**
	 * @category 校验是否存在此code
	 * @param code
	 * @param id
	 * @return
	 */
	@Override
	public boolean checkSameCode(String code, String id) {
		Criteria criteria = this.getSession().createCriteria(
				SysDepartment.class);
		criteria.add(Restrictions.eq("code", code));
		if (StringUtils.isNotBlank(id)) {
			criteria.add(Restrictions.not(Restrictions.eq("id", id)));
		}
		criteria.setProjection(Projections.rowCount());
		int totalRecords = ((Long) criteria.uniqueResult()).intValue();
		return totalRecords > 0;
	}
}
