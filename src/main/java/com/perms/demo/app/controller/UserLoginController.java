package com.perms.demo.app.controller;

import com.perms.demo.app.domain.*;
import com.perms.demo.security.SysLoginService;
import com.perms.demo.security.SysPermissionService;
import com.perms.demo.utils.SecurityUtils;
import com.perms.demo.utils.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 测试接口控制层
 *
 * @author cxw
 * @date 2022/2/8
 */
@Slf4j
@RestController
public class UserLoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysPermissionService sysPermissionService;

//    /**
//     * 登录方法 post请求
//     *
//     * @param loginBody 登录信息
//     * @return 结果
//     */
//    @PostMapping("/login")
//    public AjaxResult login(@RequestBody LoginBody loginBody) {
//        AjaxResult ajax = AjaxResult.success();
//        // 生成令牌
//        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
//                loginBody.getUuid());
//        ajax.put(Constants.TOKEN, token);
//        return ajax;
//    }

    /**
     * 登录方法 get请求
     *
     * @return 结果
     */
    @GetMapping("/login")
    public AjaxResult login(String userName, String password) {
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername(userName);
        loginBody.setPassword(password);
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息，先登录获取 token，再给该请求的 Headers 配置：
     * Authorization ： Bearer + token
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        // 角色权限集合
        Set<String> rolePermissions = sysPermissionService.getRolePermission(user);
        // 菜单权限集合
        Set<String> menuPermissions = sysPermissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", rolePermissions);
        ajax.put("permissions", menuPermissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
//    @GetMapping("getRouters")
//    public AjaxResult getRouters() {
//        //获取当前用户
//        Long userId = SecurityUtils.getUserId();
//        //获取当前用户所有菜单权限
//        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
//        //获取用户可访问的路由信息
//        return AjaxResult.success(menuService.buildMenus(menus));
//    }

    /**
     * 测试接口权限1 , 200 OK
     *
     * @return 用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:energy:index')")
    @GetMapping("testPerms1")
    public AjaxResult testPerms1() {
       return AjaxResult.success("有权访问!");
    }

    /**
     * 测试接口权限2 , 403 Forbidden
     *
     * @return 用户信息
     */
    @PreAuthorize("@ss.hasPermi('system:energy:carbon')")
    @GetMapping("testPerms2")
    public AjaxResult testPerms2() {
        return AjaxResult.success("有权访问!");
    }
}
