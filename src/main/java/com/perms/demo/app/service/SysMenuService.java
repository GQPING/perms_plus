package com.perms.demo.app.service;

import com.perms.demo.app.domain.RouterVo;
import com.perms.demo.app.domain.SysMenu;

import java.util.List;
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

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);
}
