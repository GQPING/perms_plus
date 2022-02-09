package com.perms.demo.app.service;

import java.util.Set;

/**
 * @author chengxianwei
 * @description 菜单权限表(Menu)服务接口
 * @date 2022-02-08
 */
public interface SysMenuService {
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectMenuPermsByUserId(Long userId);
}
