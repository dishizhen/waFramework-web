package com.wa.framework.user.model;

import com.wa.framework.user.model.base.BaseSysUser;



public class SysUser extends BaseSysUser {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SysUser () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SysUser (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SysUser (
		java.lang.String id,
		com.wa.framework.user.model.SysDepartment sysDepartment,
		java.lang.String username,
		java.lang.String password,
		java.lang.String state,
		boolean enabled,
		java.lang.Integer rolesCount,
		java.lang.Integer privilegesCount,
		java.lang.String createBy,
		java.util.Date createDate,
		java.lang.String realName,
		java.lang.String idCard,
		java.lang.String address,
		java.lang.String email,
		java.lang.String phoneNumber,
		java.lang.String isDepatmentAdmin) {

		super (
			id,
			sysDepartment,
			username,
			password,
			state,
			enabled,
			rolesCount,
			privilegesCount,
			createBy,
			createDate,
			realName,
			idCard,
			address,
			email,
			phoneNumber,
			isDepatmentAdmin);
	}

/*[CONSTRUCTOR MARKER END]*/


}