package com.wa.framework.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wa.framework.common.CacheConstants;
import com.wa.framework.common.cache.CacheFactory;
import com.wa.framework.log.ExpLog;
import com.wa.framework.service.BaseService;
import com.wa.framework.user.dao.SysRoleDao;
import com.wa.framework.user.model.SysPrivilege;
import com.wa.framework.user.model.SysRole;
import com.wa.framework.user.vo.UserPrivilegeVo;

/**
 * 描述：角色管理service
 * 创建人：guoyt
 * 创建时间：2016年10月21日上午11:19:36
 * 修改人：guoyt
 * 修改时间：2016年10月21日上午11:19:36
 */
@Service("roleService")
@ExpLog(type="角色管理")
public class RoleService extends BaseService {

    @Autowired
    @Qualifier("roleDao")
    private SysRoleDao roleDao;
    
    /**
     * @Description: 新增角色
     * @param: @param roleName
     * @param: @param description
     * @param: @param privilegeIds
     * @param: @return
     * @return: boolean
     * @throws
     * @since JDK 1.6
     */
    public boolean addRole(String roleName, String description,
            String[] privilegeIds) {
        SysRole role = roleDao.findRoleWithName(roleName);
        if (role == null) {
            SysRole createRole = new SysRole();
            createRole.setRoleName(roleName);
            createRole.setDescription(description);
            createRole.setDepartmentsCount(0);
            createRole.setPrivilegesCount(0);
            createRole.setUsersCount(0);
            List<SysPrivilege> privileges = null;
            if (privilegeIds != null && privilegeIds.length > 0) {
                privileges = baseDao.getByIds(
                        SysPrivilege.class, privilegeIds);
                createRole.setSysPrivileges(new HashSet<SysPrivilege>(privileges));
            }
            roleDao.save(createRole);
            
            // 刷新缓存中的角色权限关系
            if (privileges != null && privileges.size() > 0){
                refreshRolePrivilegeVo(new HashSet<SysPrivilege>(privileges), createRole);
            }
            
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * @Description: 刷新缓存中的角色权限关系
     * @param: @param filterdPrivileges
     * @param: @param user
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    private void refreshRolePrivilegeVo(Set<SysPrivilege> privileges, SysRole role) {
        Set<Object> voSet = new HashSet<Object>();
        for (SysPrivilege sysPrivilege : privileges) {
            UserPrivilegeVo vo = new UserPrivilegeVo();
            vo.setPrivilegeId(sysPrivilege.getId());
            vo.setPrivilegeCode(sysPrivilege.getPrivilegeCode());
            vo.setMenuId(sysPrivilege.getSysMenu().getId());
            voSet.add(vo);
        }
        CacheFactory.getInstance().refreshCacheSet(CacheConstants.ROLE_PRIVILEGE_MAP, role.getId(), voSet);
    }

    /**
     * @Description: 修改角色
     * @param: @param roleId
     * @param: @param roleName
     * @param: @param description
     * @param: @param ids
     * @param: @return
     * @return: boolean
     * @throws
     * @since JDK 1.6
     */
    public boolean updateRole(String roleId, String roleName, String description,
            String[] ids) {
    	SysRole roleWithName = roleDao.findRoleWithName(roleName);
    	if (roleWithName == null || (roleWithName != null && roleWithName.getId().equals(roleId))){
    		SysRole role = roleDao.get(roleId);
            if (role != null) {
                role.setRoleName(roleName);
                role.setDescription(description);
                List<SysPrivilege> privileges = null;
                if (ids != null && ids.length > 0) {
                    privileges = baseDao.getByIds(SysPrivilege.class, ids);
                    role.setSysPrivileges(new HashSet<SysPrivilege>(privileges));
                } else {
                    role.setSysPrivileges(null);
                }
                roleDao.save(role);
                
                if (ids != null && ids.length > 0 && privileges != null) {
                    // 刷新缓存中的角色权限关系
                    refreshRolePrivilegeVo(new HashSet<SysPrivilege>(privileges), role);
                } else {
                    // 移除缓存中的用户角色关系
                    CacheFactory.getInstance().removeCacheByKey(CacheConstants.ROLE_PRIVILEGE_MAP, role.getId());
                }
            }
    		return true;
    	} else {
    		return false;
    	}
    }

    /**
     * 根据ID获取角色信息
     * <描述>:
     * @author 作者：何斐
     * @version 创建时间：2016年11月16日下午3:12:34
     * @param id
     * @return
     */
    public SysRole getSysRoleById(String id) {
        SysRole sysRole = baseDao.get(SysRole.class, id);
        return sysRole;
    }

}
