package com.wa.framework.user.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the SYS_PRIVILEGE table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SYS_PRIVILEGE"
 */

public abstract class BaseSysPrivilege  implements Serializable {

	private static final long serialVersionUID = -9039152666761068766L;
	
	public static String REF = "SysPrivilege";
	public static String PROP_USERS_COUNT = "usersCount";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_ROLES_COUNT = "rolesCount";
	public static String PROP_PRIVILEGE_CODE = "privilegeCode";
	public static String PROP_PRIVILEGE_NAME = "privilegeName";
	public static String PROP_ID = "id";
	public static String PROP_ALL_USERS_COUNT = "allUsersCount";
	public static String PROP_SYS_MENU = "sysMenu";

	// constructors
	public BaseSysPrivilege () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSysPrivilege (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSysPrivilege (
		java.lang.String id,
		java.lang.Integer usersCount,
		java.lang.Integer allUsersCount,
		java.lang.Integer rolesCount,
		java.lang.Integer departmentsCount) {

		this.setId(id);
		this.setUsersCount(usersCount);
		this.setAllUsersCount(allUsersCount);
		this.setRolesCount(rolesCount);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String privilegeCode;
	private java.lang.String privilegeName;
	private java.lang.String description;
	private java.lang.Integer usersCount;
	private java.lang.Integer allUsersCount;
	private java.lang.Integer rolesCount;
	private java.lang.Integer displayOrder;

	// collections
	private com.wa.framework.user.model.SysMenu sysMenu;
	private java.util.Set<com.wa.framework.user.model.SysRole> sysRoles;
	private java.util.Set<com.wa.framework.user.model.SysUser> sysUsers;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid"
     *  column="SYS_PRIVILEGE_ID"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	public com.wa.framework.user.model.SysMenu getSysMenu() {
        return sysMenu;
    }

    public void setSysMenu(com.wa.framework.user.model.SysMenu sysMenu) {
        this.sysMenu = sysMenu;
    }

    /**
	 * Return the value associated with the column: PRIVILEGE_CODE
	 */
	public java.lang.String getPrivilegeCode () {
		return privilegeCode;
	}

	/**
	 * Set the value related to the column: PRIVILEGE_CODE
	 * @param privilegeCode the PRIVILEGE_CODE value
	 */
	public void setPrivilegeCode (java.lang.String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	
	/**
	 * Return the value associated with the column: PRIVILEGE_NAME
	 */
	public java.lang.String getPrivilegeName () {
		return privilegeName;
	}

	/**
	 * Set the value related to the column: PRIVILEGE_NAME
	 * @param privilegeName the PRIVILEGE_NAME value
	 */
	public void setPrivilegeName (java.lang.String privilegeName) {
		this.privilegeName = privilegeName;
	}

	/**
	 * Return the value associated with the column: DESCRIPTION
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: DESCRIPTION
	 * @param description the DESCRIPTION value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}

	/**
	 * Return the value associated with the column: USERS_COUNT
	 */
	public java.lang.Integer getUsersCount () {
		return usersCount;
	}

	/**
	 * Set the value related to the column: USERS_COUNT
	 * @param usersCount the USERS_COUNT value
	 */
	public void setUsersCount (java.lang.Integer usersCount) {
		this.usersCount = usersCount;
	}

	/**
	 * Return the value associated with the column: ALL_USERS_COUNT
	 */
	public java.lang.Integer getAllUsersCount () {
		return allUsersCount;
	}

	/**
	 * Set the value related to the column: ALL_USERS_COUNT
	 * @param allUsersCount the ALL_USERS_COUNT value
	 */
	public void setAllUsersCount (java.lang.Integer allUsersCount) {
		this.allUsersCount = allUsersCount;
	}

	/**
	 * Return the value associated with the column: ROLES_COUNT
	 */
	public java.lang.Integer getRolesCount () {
		return rolesCount;
	}

	/**
	 * Set the value related to the column: ROLES_COUNT
	 * @param rolesCount the ROLES_COUNT value
	 */
	public void setRolesCount (java.lang.Integer rolesCount) {
		this.rolesCount = rolesCount;
	}

	/**
	 * Return the value associated with the column: DISPLAY_ORDER
	 */
	public java.lang.Integer getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * Set the value related to the column: DISPLAY_ORDER
	 * @param displayOrder the DISPLAY_ORDER value
	 */
	public void setDisplayOrder(java.lang.Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * Return the value associated with the column: sysRoles
	 */
	public java.util.Set<com.wa.framework.user.model.SysRole> getSysRoles () {
		return sysRoles;
	}

	/**
	 * Set the value related to the column: sysRoles
	 * @param sysRoles the sysRoles value
	 */
	public void setSysRoles (java.util.Set<com.wa.framework.user.model.SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public void addTosysRoles (com.wa.framework.user.model.SysRole sysRole) {
		if (null == getSysRoles()) setSysRoles(new java.util.TreeSet<com.wa.framework.user.model.SysRole>());
		getSysRoles().add(sysRole);
	}

	/**
	 * Return the value associated with the column: sysUsers
	 */
	public java.util.Set<com.wa.framework.user.model.SysUser> getSysUsers () {
		return sysUsers;
	}

	/**
	 * Set the value related to the column: sysUsers
	 * @param sysUsers the sysUsers value
	 */
	public void setSysUsers (java.util.Set<com.wa.framework.user.model.SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	public void addTosysUsers (com.wa.framework.user.model.SysUser sysUser) {
		if (null == getSysUsers()) setSysUsers(new java.util.TreeSet<com.wa.framework.user.model.SysUser>());
		getSysUsers().add(sysUser);
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.wa.framework.user.model.SysPrivilege)) return false;
		else {
			com.wa.framework.user.model.SysPrivilege sysPrivilege = (com.wa.framework.user.model.SysPrivilege) obj;
			if (null == this.getId() || null == sysPrivilege.getId()) return false;
			else return (this.getId().equals(sysPrivilege.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString () {
		return super.toString();
	}


}