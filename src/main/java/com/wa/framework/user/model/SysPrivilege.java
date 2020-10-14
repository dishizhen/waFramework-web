package com.wa.framework.user.model;

import com.wa.framework.user.model.base.BaseSysPrivilege;



public class SysPrivilege extends BaseSysPrivilege {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SysPrivilege () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SysPrivilege (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SysPrivilege (
		java.lang.String id,
		java.lang.Integer usersCount,
		java.lang.Integer allUsersCount,
		java.lang.Integer rolesCount,
		java.lang.Integer departmentsCount) {

		super (
			id,
			usersCount,
			allUsersCount,
			rolesCount,
			departmentsCount);
	}

/*[CONSTRUCTOR MARKER END]*/


}