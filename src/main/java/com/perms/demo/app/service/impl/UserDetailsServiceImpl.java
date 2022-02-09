package com.perms.demo.app.service.impl;

import com.perms.demo.app.domain.LoginUser;
import com.perms.demo.app.domain.SysUser;
import com.perms.demo.app.service.SysUserService;
import com.perms.demo.exception.ServiceException;
import com.perms.demo.token.SysPermissionService;
import com.perms.demo.utils.StringUtils;
import com.perms.demo.utils.enums.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户验证处理
 *
 * Spring Security提供了 UserDetailsService接口 用于用户身份认证
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 对用户提供的用户详细信息进行身份验证时
     *
     * @param username 用户名
     * @return UserDetails实体类，用于保存用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        //验证通过，获取用户权限信息
        return createLoginUser(user);
    }

    /**
     * 生成登录用户对象
     * @param user
     * @return
     */
    public UserDetails createLoginUser(SysUser user) {
        //角色权限标识
        Set<String> rolePermissions = sysPermissionService.getRolePermission(user);
        //菜单权限标识
        Set<String> menuPermissions = sysPermissionService.getMenuPermission(user);
        //用户所有权限
        Set<String> permissions = new HashSet<>();
        if (rolePermissions != null && !rolePermissions.isEmpty()) {
            permissions.addAll(rolePermissions);
        }
        if (menuPermissions != null && !menuPermissions.isEmpty()) {
            permissions.addAll(menuPermissions);
        }
        return new LoginUser(user.getUserId(), user, permissions);
    }
}