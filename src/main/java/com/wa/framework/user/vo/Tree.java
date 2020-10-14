package com.wa.framework.user.vo;

import java.io.Serializable;
import java.util.Set;

/**
 * 描述：tree封装对象
 * 创建人：guoyt
 * 创建时间：2017年2月27日下午4:12:35
 * 修改人：guoyt
 * 修改时间：2017年2月27日下午4:12:35
 */
public class Tree implements Serializable {

    private static final long serialVersionUID = -8037197540943259232L;

    private String id;
    private String text;
    private String iconCls;
    private String checked;// true,false
    private String state;// open,closed
    private String attributes;// 自定义属性

    private Set<Tree> children;

    public Tree() {
    }

    public Tree(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Set<Tree> getChildren() {
        return children;
    }

    public void setChildren(Set<Tree> children) {
        this.children = children;
    }

    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof Tree))
            return false;
        else {
            Tree tree = (Tree) obj;
            if (null == this.getId() || null == tree.getId())
                return false;
            else
                return (this.getId().equals(tree.getId()));
        }
    }

    private int hashCode = Integer.MIN_VALUE;

    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId())
                return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

}
