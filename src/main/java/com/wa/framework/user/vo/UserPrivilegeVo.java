package com.wa.framework.user.vo;

/**
 * 描述：用户权限关系对象
 * 创建人：guoyt
 * 创建时间：2016年2月16日下午4:02:25
 * 修改人：guoyt
 * 修改时间：2016年2月16日下午4:02:25
 */
public class UserPrivilegeVo {
	
	private String userId;
	
	private String privilegeId;
	
	private String privilegeCode;
	
	private String menuId;

	public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeCode() {
        return privilegeCode;
    }

    public void setPrivilegeCode(String privilegeCode) {
        this.privilegeCode = privilegeCode;
    }

}
