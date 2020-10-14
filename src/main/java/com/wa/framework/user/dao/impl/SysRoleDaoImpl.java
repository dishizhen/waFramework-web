package com.wa.framework.user.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wa.framework.dao.BaseEntityDaoImpl;
import com.wa.framework.user.dao.SysRoleDao;
import com.wa.framework.user.model.SysRole;

/**
 * 描述：角色dao实现类
 * 创建人：guoyt
 * 创建时间：2016年10月21日上午11:18:30
 * 修改人：guoyt
 * 修改时间：2016年10月21日上午11:18:30
 */
@Repository("roleDao")
public class SysRoleDaoImpl extends BaseEntityDaoImpl<SysRole> implements SysRoleDao {

	/** 
	 * @Description: 根据角色名称查找有无重复角色
	 * @see： @see com.wa.framework.user.dao.SysRoleDao#findRoleWithName(java.lang.String)
	 * @since JDK 1.6
	 */
	@Override
	public SysRole findRoleWithName(String name) {
		Criteria criteria = getSession().createCriteria(SysRole.class);
		criteria.add(Restrictions.eq(SysRole.PROP_ROLE_NAME, name));
		SysRole role = (SysRole) criteria.uniqueResult();
		return role;
	}
	
}
