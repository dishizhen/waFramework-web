package com.wa.framework.user.dao;

import java.util.List;
import java.util.Map;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.dao.BaseEntityDao;
import com.wa.framework.user.model.SysDepartment;
import com.wa.framework.user.model.SysUser;

/**
 * 描述：用户dao
 * 创建人：guoyt
 * 创建时间：2017年2月27日下午4:50:05
 * 修改人：guoyt
 * 修改时间：2017年2月27日下午4:50:05
 */
public interface SysUserDao extends BaseEntityDao<SysUser> {

	/**
	 * @Description: 按照用户名分页查询
	 * @param: @param page
	 * @param: @param name
	 * @param: @param depart
	 * @param: @param status
	 * @param: @return
	 * @return: Pageable<SysUser>
	 * @throws
	 * @since JDK 1.6
	 */
	public Pageable<SysUser> findUserWithName(Page page, String name, String depart, String status);
	
	/**
	 * @Description: 根据用户名查询有无重复用户
	 * @param: @param name
	 * @param: @return
	 * @return: SysUser
	 * @throws
	 * @since JDK 1.6
	 */
	public SysUser findUserWithName(String name);

	/**
	 * @Description: 查找所有未删除的启用状态的用户，并按照姓名排序（支持中文）
	 * @param: @return
	 * @return: List<SysUser>
	 * @throws
	 * @since JDK 1.6
	 */
	public List<SysUser> findUserOrderByName();
	
	/**
	 * @Description: 根据用户id查找部门
	 * @param: @param userId
	 * @param: @return
	 * @return: SysDepartment
	 * @throws
	 * @since JDK 1.6
	 */
	public SysDepartment findDepWithUserId(String userId);
	
	/**
	 * @Description: 根据用户Id获取用户信息
	 * @param: @param userId
	 * @param: @return
	 * @return: Map<String,Object>
	 * @throws
	 * @since JDK 1.6
	 */
	public Map<String, Object> findUserById(String userId);
	
}
