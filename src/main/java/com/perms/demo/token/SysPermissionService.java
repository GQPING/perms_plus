package com.perms.demo.token;

import com.perms.demo.app.domain.SysUser;
import com.perms.demo.app.service.SysMenuService;
import com.perms.demo.app.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author ruoyi
 */
@Component
public class SysPermissionService
{
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 获取角色数据权限
     *
     * @param sysUser 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser sysUser)
    {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (sysUser.isAdmin())
        {
            roles.add("admin");
        }
        else
        {
            roles.addAll(sysRoleService.selectRolePermissionByUserId(sysUser.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user)
    {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            perms.add("*:*:*");
        }
        else
        {
            perms.addAll(sysMenuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }
}
