package com.wa.framework.user.model.base;

import java.io.Serializable;

import javax.persistence.Transient;


/**
 * This is an object that contains data related to the SYS_DEPARTMENT table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SYS_DEPARTMENT"
 */

public abstract class BaseSysDepartment  implements Serializable {

	private static final long serialVersionUID = -3290511862168291165L;
	
	public static String REF = "SysDepartment";
	public static String PROP_PARENT_ID = "parentId";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_CREATE_BY = "createBy";
	public static String PROP_ADMIN_USERID = "adminUserId";
	public static String PROP_ID = "id";
	public static String PROP_UPDATE_DATE = "updateDate";
	public static String PROP_UPDATE_BY = "updateBy";
	public static String PROP_CREATE_DATE = "createDate";
	public static String PROP_DEPARTMENT_NAME = "departmentName";


	// constructors
	public BaseSysDepartment () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSysDepartment (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSysDepartment (
		java.lang.String id,
		java.lang.String departmentName,
		java.lang.String description,
		java.lang.String adminUserId,
		java.lang.String createBy,
		java.util.Date createDate,
		java.lang.String adminUsername) {

		this.setId(id);
		this.setDepartmentName(departmentName);
		this.setDescription(description);
		this.setAdminUserId(adminUserId);
		this.setCreateBy(createBy);
		this.setCreateDate(createDate);
		this.setAdminUsername(adminUsername);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;
	private String code;

	// fields
	private java.lang.String departmentName;
	private java.lang.String description;
	private java.lang.String adminUserId;
	private java.lang.String parentId;
	private java.lang.String createBy;
	private java.util.Date createDate;
	private java.lang.String updateBy;
	private java.util.Date updateDate;
	private java.lang.String adminUsername;

	// collections
	private java.util.Set<com.wa.framework.user.model.SysUser> sysUsers;

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid"
     *  column="SYS_DEPARTMENT_ID"
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
	 * Return the value associated with the column: DEPARTMENT_NAME
	 */
	public java.lang.String getDepartmentName () {
		return departmentName;
	}

	/**
	 * Set the value related to the column: DEPARTMENT_NAME
	 * @param departmentName the DEPARTMENT_NAME value
	 */
	public void setDepartmentName (java.lang.String departmentName) {
		this.departmentName = departmentName;
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
	 * Return the value associated with the column: ADMIN_USERNAME
	 */
	public java.lang.String getAdminUserId () {
		return adminUserId;
	}

	/**
	 * Set the value related to the column: ADMIN_USERNAME
	 * @param adminUsername the ADMIN_USERNAME value
	 */
	public void setAdminUserId (java.lang.String adminUserId) {
		this.adminUserId = adminUserId;
	}

	/**
	 * Return the value associated with the column: PARENT_ID
	 */
	public java.lang.String getParentId () {
		return parentId;
	}

	/**
	 * Set the value related to the column: PARENT_ID
	 * @param parentId the PARENT_ID value
	 */
	public void setParentId (java.lang.String parentId) {
		this.parentId = parentId;
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
		if (!(obj instanceof com.wa.framework.user.model.SysDepartment)) return false;
		else {
			com.wa.framework.user.model.SysDepartment sysDepartment = (com.wa.framework.user.model.SysDepartment) obj;
			if (null == this.getId() || null == sysDepartment.getId()) return false;
			else return (this.getId().equals(sysDepartment.getId()));
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
	
	@Transient
	public java.lang.String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	
	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
	
	public String toString () {
		return super.toString();
	}


}