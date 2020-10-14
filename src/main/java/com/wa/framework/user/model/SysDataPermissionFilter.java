package com.wa.framework.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "sys_data_permission_filter")
public class SysDataPermissionFilter implements java.io.Serializable {

    private static final long serialVersionUID = -4586859679913661822L;
    public static String PROP_MENU = "menuId";
    public static String PROP_UPDATE_DATE = "updateDate";
    private String id;
    private String dataFilter;
    private String description;
    private String menuId;
    private String menuName;

    public SysDataPermissionFilter() {
    }
    
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ID", unique = true, nullable = false, length = 50)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Column(name = "data_filter", length = 500)
    public String getDataFilter() {
        return dataFilter;
    }

    public void setDataFilter(String dataFilter) {
        this.dataFilter = dataFilter;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "SYS_MENU_ID", length = 50)
    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    @Column(name = "SYS_MENU_NAME", length = 50)
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

}
