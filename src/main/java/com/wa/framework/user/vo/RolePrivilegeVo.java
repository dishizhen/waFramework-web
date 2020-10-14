package com.wa.framework.user.vo;

/**
 * 描述：角色权限关系对象
 * 创建人：guoyt
 * 创建时间：2016年2月16日下午4:02:09
 * 修改人：guoyt
 * 修改时间：2016年2月16日下午4:02:09
 */
public class RolePrivilegeVo {
	
	private String roleId;
	
	private String privilegeId;
	
	private String privilegeCode;
	
	private String menuId;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPrivilegeCode() {
        return privilegeCode;
    }

    public void setPrivilegeCode(String privilegeCode) {
        this.privilegeCode = privilegeCode;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

}
