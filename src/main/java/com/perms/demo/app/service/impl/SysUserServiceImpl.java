package com.perms.demo.app.service.impl;

import com.perms.demo.app.domain.SysUser;
import com.perms.demo.app.mapper.SysUserMapper;
import com.perms.demo.app.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chengxianwei
 * @description 用户信息表(User)服务实现类
 * @date 2022-02-08
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(Long userId) {
        return this.sysUserMapper.queryById(userId);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return this.sysUserMapper.selectUserByUserName(userName);
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser update(SysUser sysUser) {
        this.sysUserMapper.update(sysUser);
        return this.queryById(sysUser.getUserId());
    }
}
