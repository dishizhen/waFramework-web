package com.wa.framework.user.model;

import com.wa.framework.user.model.base.BaseSysDepartment;



public class SysDepartment extends BaseSysDepartment {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SysDepartment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SysDepartment (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SysDepartment (
		java.lang.String id,
		java.lang.String departmentName,
		java.lang.String description,
		java.lang.String adminUserId,
		java.lang.String createBy,
		java.util.Date createDate,
		java.lang.String adminUsername) {

		super (
			id,
			departmentName,
			description,
			adminUserId,
			createBy,
			createDate,
			adminUsername);
	}

/*[CONSTRUCTOR MARKER END]*/


}