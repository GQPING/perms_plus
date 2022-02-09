package com.perms.demo.app.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.perms.demo.app.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengxianwei
 * @description 角色信息表(Role)数据库访问层
 * @date 2022-02-08
 */
@Mapper
@DS("test")
public interface SysRoleMapper {
    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolePermissionByUserId(Long userId);
}

