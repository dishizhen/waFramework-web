package com.wa.framework.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.wa.framework.Page;
import com.wa.framework.Pageable;
import com.wa.framework.QueryCondition;
import com.wa.framework.QueryConditions;
import com.wa.framework.SimplePageable;
import com.wa.framework.dao.BaseEntityDaoImpl;
import com.wa.framework.user.dao.SysUserDao;
import com.wa.framework.user.model.SysDepartment;
import com.wa.framework.user.model.SysUser;

/**
 * 描述：用户dao实现类
 * 创建人：guoyt
 * 创建时间：2016年10月21日上午11:18:18
 * 修改人：guoyt
 * 修改时间：2016年10月21日上午11:18:18
 */
@Repository("userDao")
public class SysUserDaoImpl extends BaseEntityDaoImpl<SysUser> implements SysUserDao {
	
    /** 
     * @Description: 按照用户名分页查询
     * @see： @see com.wa.framework.user.dao.SysUserDao#findUserWithName(com.wa.framework.Page, java.lang.String, java.lang.String, java.lang.String)
     * @since JDK 1.6
     */
    public Pageable<SysUser> findUserWithName(Page page, String name, String depart, String status) {
    	List<String> lst=new ArrayList<String>();
    	Pageable<SysUser> pageable=new  SimplePageable<SysUser>();
    	
    	StringBuffer createSqlQuery=new  StringBuffer("from SysUser where 1=1 and state='1' ");
        StringBuffer countSqlQuery=new  StringBuffer("select count(*) from SysUser where 1=1 and state='1' ");
        
        if (StringUtils.isNotEmpty(name)){
            if(name.contains("%")||name.contains("_")){
                name=name.replace("%","\\%").replace("_", "\\_");
                lst.add("%"+ name+ "%");
                createSqlQuery.append(" and username like ?  escape '\\' ");
                countSqlQuery.append(" and username like ?  escape '\\' ");
            }else{
                lst.add("%"+ name+ "%");
                createSqlQuery.append(" and username like ? ");
                countSqlQuery.append(" and username like ? ");
            }
        }
        
        if (StringUtils.isNotEmpty(depart)){
            if(depart.contains("%")||depart.contains("_")){
                depart=depart.replace("%","\\%").replace("_", "\\_");
                lst.add("%"+ depart+ "%");
                createSqlQuery.append(" and sysDepartment.departmentName like ?  escape '\\' ");
                countSqlQuery.append(" and sysDepartment.departmentName like ?  escape '\\' ");
            }else{
                lst.add("%"+ depart+ "%");
                createSqlQuery.append(" and sysDepartment.departmentName like ? ");
                countSqlQuery.append(" and sysDepartment.departmentName like ? ");
            }
        }
        
        if (StringUtils.isNotEmpty(status)){
            if ("1".equals(status)){
                createSqlQuery.append(" and enabled = 1 ");
                countSqlQuery.append(" and enabled = 1 ");
            } else {
                createSqlQuery.append(" and enabled = 0 ");
                countSqlQuery.append(" and enabled = 0 ");
            }
        }
        
        createSqlQuery.append(" order by createDate desc ");
        pageable=findByHqlWithPage(createSqlQuery.toString(),countSqlQuery.toString(),page,lst);
        
        for (SysUser user : pageable.getList()) {
            Hibernate.initialize(user.getSysDepartment());
        }
        return pageable;
    }

    /** 
     * @Description: 根据用户名查询有无重复用户
     * @see： @see com.wa.framework.user.dao.SysUserDao#findUserWithName(java.lang.String)
     * @since JDK 1.6
     */
    @Override
    public SysUser findUserWithName(String name) {
    	QueryConditions queryConditions = new QueryConditions();
    	queryConditions.add(QueryCondition.eq(SysUser.PROP_STATE, "1"));
    	queryConditions.add(QueryCondition.eq(SysUser.PROP_USERNAME, name));
        return unique(queryConditions);
    }

	/** 
	 * @Description: 查找所有未删除的启用状态的用户，并按照姓名排序（支持中文）
	 * @see： @see com.wa.framework.user.dao.SysUserDao#findUserOrderByName()
	 * @since JDK 1.6
	 */
	@Override
	public List<SysUser> findUserOrderByName() {
		StringBuffer createSqlQuery=new  StringBuffer();
		createSqlQuery.append("from SysUser where enabled = 1 and state = 1 order by nlssort(username,'NLS_SORT=SCHINESE_PINYIN_M')");
		return findByHql(createSqlQuery.toString());
	}
	
    /** 
     * @Description: 根据用户id查找部门
     * @see： @see com.wa.framework.user.dao.SysUserDao#findDepWithUserId(java.lang.String)
     * @since JDK 1.6
     */
    @SuppressWarnings("rawtypes")
    @Override
    public SysDepartment findDepWithUserId(String userId) {
        Map<String, String> parameters = new HashMap<String, String>();
        String hql = "select u.sysDepartment from SysUser u where u.id =:userId";
        parameters.put("userId", userId);
        List list = findByHql(hql, parameters);
        if (list.size() > 0) {
            return (SysDepartment) list.get(0);
        } else {
            return null;
        }
    }

    /** 
     * @Description: 根据用户Id获取用户信息
     * @see： @see com.wa.framework.user.dao.SysUserDao#findUserById(java.lang.String)
     * @since JDK 1.6
     */
    @Override
    public Map<String,Object> findUserById(String userId) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("I_USERID", userId);
        String sql = "select * from SYS_USER where SYS_USER_ID = :I_USERID";
        Map<String,Object> res = this.findBySql(sql, param).get(0);
        return res;
    }
}
