package com.perms.demo.app.service;

import com.perms.demo.app.domain.SysUser;

import java.util.List;

/**
 * @author chengxianwei
 * @description 用户信息表(User)服务接口
 * @date 2022-02-08
 */
public interface SysUserService {
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUser queryById(Long userId);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByUserName(String userName);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    SysUser update(SysUser sysUser);
}
