package com.wa.framework.user.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the SYS_MENU table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="SYS_MENU"
 */

public abstract class BaseSysMenu  implements Serializable {

	private static final long serialVersionUID = 9031866822125382341L;
	
	public static String REF = "SysMenu";
	public static String PROP_PARENT_ID = "parentId";
	public static String PROP_MENU_NAME = "menuName";
	public static String PROP_DISPLAY_ORDER = "displayOrder";
	public static String PROP_MENU_URL = "menuUrl";
	public static String PROP_MENU_ICON = "menuIcon";
	public static String PROP_ID = "id";


	// constructors
	public BaseSysMenu () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseSysMenu (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseSysMenu (
		java.lang.String id,
		java.lang.String parentId,
		java.lang.String menuName,
		java.lang.String menuUrl,
		java.lang.Integer displayOrder) {

		this.setId(id);
		this.setParentId(parentId);
		this.setMenuName(menuName);
		this.setMenuUrl(menuUrl);
		this.setDisplayOrder(displayOrder);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String parentId;
	private java.lang.String menuName;
	private java.lang.String menuUrl;
	private java.lang.String menuIcon;
	private java.lang.Integer displayOrder;

	// collections
	private java.util.Set<com.wa.framework.user.model.SysPrivilege> sysPrivilege;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid"
     *  column="SYS_MENU_ID"
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
	 * Return the value associated with the column: MENU_NAME
	 */
	public java.lang.String getMenuName () {
		return menuName;
	}

	/**
	 * Set the value related to the column: MENU_NAME
	 * @param menuName the MENU_NAME value
	 */
	public void setMenuName (java.lang.String menuName) {
		this.menuName = menuName;
	}



	/**
	 * Return the value associated with the column: MENU_URL
	 */
	public java.lang.String getMenuUrl () {
		return menuUrl;
	}

	/**
	 * Set the value related to the column: MENU_URL
	 * @param menuUrl the MENU_URL value
	 */
	public void setMenuUrl (java.lang.String menuUrl) {
		this.menuUrl = menuUrl;
	}



	/**
	 * Return the value associated with the column: MENU_ICON
	 */
	public java.lang.String getMenuIcon () {
		return menuIcon;
	}

	/**
	 * Set the value related to the column: MENU_ICON
	 * @param menuIcon the MENU_ICON value
	 */
	public void setMenuIcon (java.lang.String menuIcon) {
		this.menuIcon = menuIcon;
	}



	/**
	 * Return the value associated with the column: DISPLAY_ORDER
	 */
	public java.lang.Integer getDisplayOrder () {
		return displayOrder;
	}

	/**
	 * Set the value related to the column: DISPLAY_ORDER
	 * @param displayOrder the DISPLAY_ORDER value
	 */
	public void setDisplayOrder (java.lang.Integer displayOrder) {
		this.displayOrder = displayOrder;
	}



	public java.util.Set<com.wa.framework.user.model.SysPrivilege> getSysPrivilege() {
        return sysPrivilege;
    }

    public void setSysPrivilege(java.util.Set<com.wa.framework.user.model.SysPrivilege> sysPrivilege) {
        this.sysPrivilege = sysPrivilege;
    }

    public void addTosysPrivilege (com.wa.framework.user.model.SysPrivilege sysPrivilege) {
		if (null == getSysPrivilege()) setSysPrivilege(new java.util.TreeSet<com.wa.framework.user.model.SysPrivilege>());
		getSysPrivilege().add(sysPrivilege);
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.wa.framework.user.model.SysMenu)) return false;
		else {
			com.wa.framework.user.model.SysMenu sysMenu = (com.wa.framework.user.model.SysMenu) obj;
			if (null == this.getId() || null == sysMenu.getId()) return false;
			else return (this.getId().equals(sysMenu.getId()));
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