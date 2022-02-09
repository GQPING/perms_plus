package com.perms.demo.app.domain;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chengxianwei
 * @description 菜单权限表类实体
 * @date 2022-02-08
 */
@Data
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 445481041920212228L;
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 父菜单ID
     */
    private Long parentId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单类型;1：大屏、2：菜单、3：按钮
     */
    private String menuType;
    /**
     * 菜单权限标识
     */
    private String menuPerms;

    /**
     * 子菜单对象列表
     */
    private List<SysMenu> children = new ArrayList<SysMenu>();

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuPerms() {
        return menuPerms;
    }

    public void setMenuPerms(String menuPerms) {
        this.menuPerms = menuPerms;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
    }
}
