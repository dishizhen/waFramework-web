package com.wa.framework.user.model;

import com.wa.framework.user.model.base.BaseSysRole;



public class SysRole extends BaseSysRole {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SysRole () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SysRole (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SysRole (
		java.lang.String id,
		java.lang.String roleName,
		java.lang.Integer usersCount,
		java.lang.Integer privilegesCount,
		java.lang.Integer departmentsCount,
		java.lang.String createBy,
		java.util.Date createDate) {

		super (
			id,
			roleName,
			usersCount,
			privilegesCount,
			departmentsCount,
			createBy,
			createDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}