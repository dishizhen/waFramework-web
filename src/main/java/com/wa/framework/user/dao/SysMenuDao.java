package com.wa.framework.user.dao;

import java.util.List;

import com.wa.framework.dao.BaseEntityDao;
import com.wa.framework.user.model.SysMenu;

/**
 * 描述：菜单dao
 * 创建人：guoyt
 * 创建时间：2017年2月27日下午4:52:48
 * 修改人：guoyt
 * 修改时间：2017年2月27日下午4:52:48
 */
public interface SysMenuDao extends BaseEntityDao<SysMenu> {
	
    /**
     * @Description: 查询有无相同排序对象
     * @param: @param order
     * @param: @return
     * @return: List<SysMenu>
     * @throws
     * @since JDK 1.6
     */
    public List<SysMenu> getCountByOrderList(int order);
    
    /**
     * @Description: 根据菜单id获取所有子菜单
     * @param: @param menuId
     * @param: @return
     * @return: List<SysMenu>
     * @throws
     * @since JDK 1.6
     */
    public List<SysMenu> findChildsByMenuid(String menuId);

}
