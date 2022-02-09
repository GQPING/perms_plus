package com.perms.demo.app.service;

import java.util.Set;

/**
 * @author chengxianwei
 * @description 角色信息表(Role)服务接口
 * @date 2022-02-08
 */
public interface SysRoleService {
    /**
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRolePermissionByUserId(Long userId);
}
