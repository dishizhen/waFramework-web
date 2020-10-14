package com.wa.framework.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wa.framework.common.CacheConstants;
import com.wa.framework.common.cache.CacheFactory;
import com.wa.framework.dao.BaseDaoSupport;
import com.wa.framework.log.ExpLog;
import com.wa.framework.user.vo.RolePrivilegeVo;
import com.wa.framework.user.vo.UserPrivilegeVo;
import com.wa.framework.user.vo.UserRoleVo;

/**
 * 描述：内存管理服务类
 * 创建人：guoyt
 * 创建时间：2016年2月19日上午11:21:46
 * 修改人：guoyt
 * 修改时间：2016年2月19日上午11:21:46
 */
@Service("memCacheService")
@ExpLog(type="内存管理")
public class MemCacheService extends BaseDaoSupport {
    
    private static final Log logger = LogFactory.getLog(MemCacheService.class);

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("roleService")
    private RoleService roleService;

    /**
     * @Description: 初始化内存
     * @param: 
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    @PostConstruct
    public void initCache() {
        try {
            // 初始化用户角色关系
            initUserRoleMap(true);
            
            // 初始化用户权限关系
            initUserPrivilegeMap(true);
            
            // 初始化角色权限关系
            initRolePrivilegeMap(true);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @Description: 全量刷新内存
     * @param: 
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    public void refreshCache() {
        try {
            // 刷新用户角色关系
            initUserRoleMap(false);
            
            // 刷新用户权限关系
            initUserPrivilegeMap(false);
            
            // 刷新角色权限关系
            initRolePrivilegeMap(false);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
     * @Description: 初始化用户角色关系
     * @param:
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    private void initUserRoleMap(boolean isInit) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SYS_USER_ID AS USERID, SYS_ROLE_ID AS ROLEID FROM SYS_USER_TO_ROLE ");
        List<Map<String, Object>> list = findBySql(sql.toString());
        List<UserRoleVo> voList = new ArrayList<UserRoleVo>();
        for(Map<String, Object> map : list) {
            UserRoleVo vo = new UserRoleVo();
            vo.setUserId((String) map.get("USERID"));
            vo.setRoleId((String) map.get("ROLEID"));
            voList.add(vo);
        }

        if (isInit){
            CacheFactory.getInstance().createCache(CacheConstants.USER_ROLE_MAP);
        } else {
            CacheFactory.getInstance().clearCache(CacheConstants.USER_ROLE_MAP);
        }
        
        for (UserRoleVo userRoleVo : voList) {
            CacheFactory.getInstance().initCache(CacheConstants.USER_ROLE_MAP, userRoleVo.getUserId(),
                            userRoleVo.getRoleId());
        }
    }

    /**
     * @Description: 初始化用户权限关系
     * @param:
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    private void initUserPrivilegeMap(boolean isInit) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SYS_USER_ID AS USERID, UP.SYS_PRIVILEGE_ID AS PRIVILEGEID, PRIVILEGE_CODE AS PRIVILEGECODE, SYS_MENU_ID AS SYSMENUID ");
        sql.append("FROM SYS_USER_TO_PRIVILEGE UP LEFT JOIN SYS_PRIVILEGE P ON UP.SYS_PRIVILEGE_ID = P.SYS_PRIVILEGE_ID ");
        List<Map<String, Object>> list = findBySql(sql.toString());
        List<UserPrivilegeVo> voList = new ArrayList<UserPrivilegeVo>();
        for(Map<String, Object> map : list) {
            UserPrivilegeVo vo = new UserPrivilegeVo();
            vo.setUserId((String) map.get("USERID"));
            vo.setPrivilegeId((String) map.get("PRIVILEGEID"));
            vo.setPrivilegeCode((String) map.get("PRIVILEGECODE"));
            vo.setMenuId((String) map.get("SYSMENUID"));
            voList.add(vo);
        }

        if (isInit){
            CacheFactory.getInstance().createCache(CacheConstants.USER_PRIVILEGE_MAP);
        } else {
            CacheFactory.getInstance().clearCache(CacheConstants.USER_PRIVILEGE_MAP);
        }
        for (UserPrivilegeVo userPrivilegeVo : voList) {
            UserPrivilegeVo vo = new UserPrivilegeVo();
            vo.setPrivilegeId(userPrivilegeVo.getPrivilegeId());
            vo.setPrivilegeCode(userPrivilegeVo.getPrivilegeCode());
            vo.setMenuId(userPrivilegeVo.getMenuId());

            CacheFactory.getInstance().initCache(CacheConstants.USER_PRIVILEGE_MAP, userPrivilegeVo.getUserId(), vo);
        }
    }

    /**
     * @Description: 初始化角色权限关系
     * @param: 
     * @return: void
     * @throws
     * @since JDK 1.6
     */
    private void initRolePrivilegeMap(boolean isInit) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT SYS_ROLE_ID AS ROLEID, UP.SYS_PRIVILEGE_ID AS PRIVILEGEID, PRIVILEGE_CODE AS PRIVILEGECODE, SYS_MENU_ID AS SYSMENUID ");
        sql.append("FROM SYS_ROLE_TO_PRIVILEGE UP LEFT JOIN SYS_PRIVILEGE P ON UP.SYS_PRIVILEGE_ID = P.SYS_PRIVILEGE_ID ");
        List<Map<String, Object>> list = findBySql(sql.toString());
        List<RolePrivilegeVo> voList = new ArrayList<RolePrivilegeVo>();
        for(Map<String, Object> map : list) {
            RolePrivilegeVo vo = new RolePrivilegeVo();
            vo.setRoleId((String) map.get("ROLEID"));
            vo.setPrivilegeId((String) map.get("PRIVILEGEID"));
            vo.setPrivilegeCode((String) map.get("PRIVILEGECODE"));
            vo.setMenuId((String) map.get("SYSMENUID"));
            voList.add(vo);
        }

        if (isInit){
            CacheFactory.getInstance().createCache(CacheConstants.ROLE_PRIVILEGE_MAP);
        } else {
            CacheFactory.getInstance().clearCache(CacheConstants.ROLE_PRIVILEGE_MAP);
        }
        for (RolePrivilegeVo rolePrivilegeVo : voList) {
            UserPrivilegeVo vo = new UserPrivilegeVo();
            vo.setPrivilegeId(rolePrivilegeVo.getPrivilegeId());
            vo.setPrivilegeCode(rolePrivilegeVo.getPrivilegeCode());
            vo.setMenuId(rolePrivilegeVo.getMenuId());
            
            CacheFactory.getInstance().initCache(CacheConstants.ROLE_PRIVILEGE_MAP, rolePrivilegeVo.getRoleId(),
                            vo);
        }
    }
    
}
