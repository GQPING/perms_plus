package com.perms.demo.app.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.perms.demo.app.domain.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengxianwei
 * @description 菜单权限表(Menu)数据库访问层
 * @date 2022-02-08
 */
@Mapper
@DS("test")
public interface SysMenuMapper {
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);
}

