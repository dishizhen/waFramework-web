package com.wa.framework.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wa.framework.user.model.SysMenu;

@Entity
@Table(name = "SYS_MODULE_DEFINITION")
public class SysModuleDefinition implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    public static String PROP_DISPLAYORDER = "displayOrder";
    public static String PROP_MENU = "menu";
    public static String PROP_NAME = "name";
    public static String PROP_MODULE_ID = "moduleId";

    private String moduleId;
    private String name;
    private String description;
    private Integer displayOrder;
    private SysMenu menu;
    
    public SysModuleDefinition() {
    }

    public SysModuleDefinition(String id) {
        this.moduleId = id;
    }

    public SysModuleDefinition(String id, SysMenu menu, String name, String description, Integer displayOrder) {
        this.moduleId = id;
        this.menu = menu;
        this.name = name;
        this.description = description;
        this.displayOrder = displayOrder;
    }

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "ID", unique = true, nullable = false, length = 50)
    public String getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(String id) {
        this.moduleId = id;
    }

    @Column(name = "NAME", length = 50)
    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", length = 500)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DISPLAY_ORDER", length = 10)
    public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@ManyToOne
    @JoinColumn(name = "MENU_ID")
    public SysMenu getMenu() {
        return menu;
    }

	public void setMenu(SysMenu menu) {
        this.menu = menu;
    }
}
