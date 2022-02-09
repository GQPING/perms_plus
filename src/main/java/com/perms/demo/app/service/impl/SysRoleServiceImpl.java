package com.perms.demo.app.service.impl;

import com.perms.demo.app.domain.SysRole;
import com.perms.demo.app.mapper.SysRoleMapper;
import com.perms.demo.app.service.SysRoleService;
import com.perms.demo.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chengxianwei
 * @description 角色信息表(Role)服务实现类
 * @date 2022-02-08
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = sysRoleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRolePerms().trim().split(",")));
            }
        }
        return permsSet;
    }
}
