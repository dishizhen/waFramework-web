package com.wa.framework.user.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wa.framework.dao.BaseEntityDaoImpl;
import com.wa.framework.user.dao.SysMenuDao;
import com.wa.framework.user.model.SysMenu;

/**
 * 描述：菜单dao实现类
 * 创建人：guoyt
 * 创建时间：2016年10月21日上午11:19:02
 * 修改人：guoyt
 * 修改时间：2016年10月21日上午11:19:02
 */
@Repository("menuDao")
public class SysMenuDaoImpl extends BaseEntityDaoImpl<SysMenu> implements SysMenuDao {

	/** 
	 * @Description: 查询有无相同排序对象
	 * @see： @see com.wa.framework.user.dao.SysMenuDao#getCountByOrderList(int)
	 * @since JDK 1.6
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SysMenu> getCountByOrderList(int order) {
			Criteria criteria = this.getSession().createCriteria(SysMenu.class);
			criteria.add(Restrictions.eq("displayOrder", order));
			 List<SysMenu> lst=criteria.list();
			return lst;
	}
	
	/** 
	 * @Description: 根据菜单id获取所有子菜单
	 * @see： @see com.wa.framework.user.dao.SysMenuDao#findChildsByMenuid(java.lang.String)
	 * @since JDK 1.6
	 */
    public List<SysMenu> findChildsByMenuid(String menuId) {
        String sql = "select  g.* from sys_menu g start with g.parent_id='" + menuId + "'"
                        + "connect by prior g.sys_menu_id=g.parent_id";
        List<SysMenu> list = findBySql(sql, SysMenu.class);
        return list;
    }
	
}
