package com.wa.framework.user.vo;

/**
 * 描述：权限vo封装对象
 * 创建人：guoyt
 * 创建时间：2017年2月27日下午4:17:32
 * 修改人：guoyt
 * 修改时间：2017年2月27日下午4:17:32
 */
public class SysAccessVO {

    private String menuId;
    private String menuName;
    private String privilegeId;
    private String privilegeCode;
    private String privilegeName;
    private String description;

    public SysAccessVO(String menuId, String menuName, String privilegeId,
            String privilegeCode, String privilegeName, String description) {
        super();
        this.menuId = menuId;
        this.menuName = menuName;
        this.privilegeId = privilegeId;
        this.privilegeCode = privilegeCode;
        this.privilegeName = privilegeName;
        this.description = description;
    }

    public SysAccessVO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
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

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
