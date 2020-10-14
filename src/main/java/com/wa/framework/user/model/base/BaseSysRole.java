package com.wa.framework.user.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the SYS_ROLE table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SYS_ROLE"
 */

public abstract class BaseSysRole  implements Serializable {

	private static final long serialVersionUID = 3224869554407057338L;
	
	public static String REF = "SysRole";
	public static String PROP_ROLE_NAME = "roleName";
	public static String PROP_USERS_COUNT = "usersCount";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_PRIVILEGES_COUNT = "privilegesCount";
	public static String PROP_CREATE_BY = "createBy";
	public static String PROP_ID = "id";
	public static String PROP_UPDATE_DATE = "updateDate";
	public static String PROP_UPDATE_BY = "updateBy";
	public static String PROP_CREATE_DATE = "createDate";


	// constructors
	public BaseSysRole () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSysRole (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSysRole (
		java.lang.String id,
		java.lang.String roleName,
		java.lang.Integer usersCount,
		java.lang.Integer privilegesCount,
		java.lang.Integer departmentsCount,
		java.lang.String createBy,
		java.util.Date createDate) {

		this.setId(id);
		this.setRoleName(roleName);
		this.setUsersCount(usersCount);
		this.setPrivilegesCount(privilegesCount);
		this.setDepartmentsCount(departmentsCount);
		this.setCreateBy(createBy);
		this.setCreateDate(createDate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String roleName;
	private java.lang.String description;
	private java.lang.Integer usersCount;
	private java.lang.Integer privilegesCount;
	private java.lang.Integer departmentsCount;
	private java.lang.String createBy;
	private java.util.Date createDate;
	private java.lang.String updateBy;
	private java.util.Date updateDate;

	// collections
	private java.util.Set<com.wa.framework.user.model.SysPrivilege> sysPrivileges;
	private java.util.Set<com.wa.framework.user.model.SysUser> sysUsers;


	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid"
     *  column="SYS_ROLE_ID"
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




	/**
	 * Return the value associated with the column: ROLE_NAME
	 */
	public java.lang.String getRoleName () {
		return roleName;
	}

	/**
	 * Set the value related to the column: ROLE_NAME
	 * @param roleName the ROLE_NAME value
	 */
	public void setRoleName (java.lang.String roleName) {
		this.roleName = roleName;
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
	 * Return the value associated with the column: PRIVILEGES_COUNT
	 */
	public java.lang.Integer getPrivilegesCount () {
		return privilegesCount;
	}

	/**
	 * Set the value related to the column: PRIVILEGES_COUNT
	 * @param privilegesCount the PRIVILEGES_COUNT value
	 */
	public void setPrivilegesCount (java.lang.Integer privilegesCount) {
		this.privilegesCount = privilegesCount;
	}



	/**
	 * Return the value associated with the column: DEPARTMENTS_COUNT
	 */
	public java.lang.Integer getDepartmentsCount () {
		return departmentsCount;
	}

	/**
	 * Set the value related to the column: DEPARTMENTS_COUNT
	 * @param departmentsCount the DEPARTMENTS_COUNT value
	 */
	public void setDepartmentsCount (java.lang.Integer departmentsCount) {
		this.departmentsCount = departmentsCount;
	}



	/**
	 * Return the value associated with the column: CREATE_BY
	 */
	public java.lang.String getCreateBy () {
		return createBy;
	}

	/**
	 * Set the value related to the column: CREATE_BY
	 * @param createBy the CREATE_BY value
	 */
	public void setCreateBy (java.lang.String createBy) {
		this.createBy = createBy;
	}



	/**
	 * Return the value associated with the column: CREATE_DATE
	 */
	public java.util.Date getCreateDate () {
		return createDate;
	}

	/**
	 * Set the value related to the column: CREATE_DATE
	 * @param createDate the CREATE_DATE value
	 */
	public void setCreateDate (java.util.Date createDate) {
		this.createDate = createDate;
	}



	/**
	 * Return the value associated with the column: UPDATE_BY
	 */
	public java.lang.String getUpdateBy () {
		return updateBy;
	}

	/**
	 * Set the value related to the column: UPDATE_BY
	 * @param updateBy the UPDATE_BY value
	 */
	public void setUpdateBy (java.lang.String updateBy) {
		this.updateBy = updateBy;
	}



	/**
	 * Return the value associated with the column: UPDATE_DATE
	 */
	public java.util.Date getUpdateDate () {
		return updateDate;
	}

	/**
	 * Set the value related to the column: UPDATE_DATE
	 * @param updateDate the UPDATE_DATE value
	 */
	public void setUpdateDate (java.util.Date updateDate) {
		this.updateDate = updateDate;
	}



	/**
	 * Return the value associated with the column: sysPrivileges
	 */
	public java.util.Set<com.wa.framework.user.model.SysPrivilege> getSysPrivileges () {
		return sysPrivileges;
	}

	/**
	 * Set the value related to the column: sysPrivileges
	 * @param sysPrivileges the sysPrivileges value
	 */
	public void setSysPrivileges (java.util.Set<com.wa.framework.user.model.SysPrivilege> sysPrivileges) {
		this.sysPrivileges = sysPrivileges;
	}

	public void addTosysPrivileges (com.wa.framework.user.model.SysPrivilege sysPrivilege) {
		if (null == getSysPrivileges()) setSysPrivileges(new java.util.TreeSet<com.wa.framework.user.model.SysPrivilege>());
		getSysPrivileges().add(sysPrivilege);
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
		if (!(obj instanceof com.wa.framework.user.model.SysRole)) return false;
		else {
			com.wa.framework.user.model.SysRole sysRole = (com.wa.framework.user.model.SysRole) obj;
			if (null == this.getId() || null == sysRole.getId()) return false;
			else return (this.getId().equals(sysRole.getId()));
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