package com.wa.framework.user.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.dao.BaseEntityDao;
import com.wa.framework.user.model.SysDepartment;

/**
 * 描述：部门dao
 * 创建人：guoyt
 * 创建时间：2017年2月27日下午4:54:01
 * 修改人：guoyt
 * 修改时间：2017年2月27日下午4:54:01
 */
public interface SysDepartmentDao extends BaseEntityDao<SysDepartment> {

	/**
	 * @Description: 根据部门id查询有无子部门，返回0代表没有，其他代表有
	 * @param: @param parentId
	 * @param: @return
	 * @return: Integer
	 * @throws
	 * @since JDK 1.6
	 */
	public Integer getSubCountByParentId(String parentId);
	
	/**
	 * @Description: 判断部门名称有无相同的，返回0代表没有，其他代表有
	 * @param: @param dptName
	 * @param: @return
	 * @return: Integer
	 * @throws
	 * @since JDK 1.6
	 */
	public Integer getCountByDptName(String dptName);
	
	/**
	 * @Description: 判断部门名称有无相同的，返回0代表没有，其他代表有
	 * @param: @param dptName
	 * @param: @param parentId
	 * @param: @return
	 * @return: Integer
	 * @throws
	 * @since JDK 1.6
	 */
	public Integer getCountByDptName(String dptName, String parentId);
	
	/**
	 * @Description: 根据部门名称查询对象
	 * @param: @param dptName
	 * @param: @return
	 * @return: List<SysDepartment>
	 * @throws
	 * @since JDK 1.6
	 */
	public List<SysDepartment> getCountByDptList(String dptName);
	
	/**
	 * @Description: 根据部门名称和部门负责人查询所有部门
	 * @param: @param page
	 * @param: @param dptName
	 * @param: @param adName
	 * @param: @return
	 * @return: List<SysDepartment>
	 * @throws
	 * @since JDK 1.6
	 */
	public List<SysDepartment> findDepartmentsByName(Page page,String dptName,String adName);
	
	/**
	 * @Description: 根据部门名称和部门负责人查询所有部门
	 * @param: @param departmentId
	 * @param: @return
	 * @return: List<SysDepartment>
	 * @throws
	 * @since JDK 1.6
	 */
	public List<SysDepartment> findParentDeparments(String departmentId);
	
	/**
	 * @Description: 根据查询条件分页查询
	 * @param: @param criteria
	 * @param: @param page
	 * @param: @return
	 * @return: Pageable<SysDepartment>
	 * @throws
	 * @since JDK 1.6
	 */
	public Pageable<SysDepartment> getDeptList(DetachedCriteria criteria, Page page);
	
	/**
	 * @category 校验是否存在此code
	 * @param code
	 * @param id
	 * @return
	 */
	boolean checkSameCode(String code, String id);
}
