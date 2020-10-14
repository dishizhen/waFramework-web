package com.wa.framework.user.dao;

import com.wa.framework.dao.BaseEntityDao;
import com.wa.framework.user.model.SysRole;

/**
 * 描述：角色dao
 * 创建人：guoyt
 * 创建时间：2017年2月27日下午4:50:57
 * 修改人：guoyt
 * 修改时间：2017年2月27日下午4:50:57
 */
public interface SysRoleDao extends BaseEntityDao<SysRole> {

	/**
	 * @Description: 根据角色名称查找有无重复角色
	 * @param: @param name
	 * @param: @return
	 * @return: SysRole
	 * @throws
	 * @since JDK 1.6
	 */
	public SysRole findRoleWithName(String name);
	
}
