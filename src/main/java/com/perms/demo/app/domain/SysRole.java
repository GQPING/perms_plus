package com.perms.demo.app.domain;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author chengxianwei
 * @description 角色信息表类实体
 * @date 2022-02-08
 */
public class SysRole implements Serializable {
    private static final long serialVersionUID = 624836802999248903L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色权限标识
     */
    private String rolePerms;
    /**
     * 角色数据范围;1：全部数据权限、2：自定数据权限、3：仅本人数据权限
     */
    private String dataScope;

    /**
     * 角色菜单对象列表
     */
    private List<SysMenu> menus;

    public SysRole() { }

    public SysRole(Long roleId)
    {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRolePerms() {
        return rolePerms;
    }

    public void setRolePerms(String rolePerms) {
        this.rolePerms = rolePerms;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }
}
