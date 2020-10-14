package com.wa.framework.user.model;

import com.wa.framework.user.model.base.BaseSysMenu;



public class SysMenu extends BaseSysMenu {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public SysMenu () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public SysMenu (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public SysMenu (
		java.lang.String id,
		java.lang.String parentId,
		java.lang.String menuName,
		java.lang.String menuUrl,
		java.lang.Integer displayOrder) {

		super (
			id,
			parentId,
			menuName,
			menuUrl,
			displayOrder);
	}

/*[CONSTRUCTOR MARKER END]*/


}