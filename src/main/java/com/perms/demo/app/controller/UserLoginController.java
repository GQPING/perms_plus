package com.perms.demo.app.controller;

import com.perms.demo.app.domain.LoginUser;
import com.perms.demo.app.domain.SysUser;
import com.perms.demo.app.domain.AjaxResult;
import com.perms.demo.app.domain.LoginBody;
import com.perms.demo.security.SysLoginService;
import com.perms.demo.security.SysPermissionService;
import com.perms.demo.utils.SecurityUtils;
import com.perms.demo.utils.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
//    @PostMapping("/login1")
//    public AjaxResult login1(@RequestBody LoginBody loginBody) {
//        AjaxResult ajax = AjaxResult.success();
//        // 生成令牌
//        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
//                loginBody.getUuid());
//        ajax.put(Constants.TOKEN, token);
//        return ajax;
//    }

    /**
     * 登录方法 admin
     *
     * @return 结果
     */
    @GetMapping("/login")
    public AjaxResult login() {
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("admin");
        loginBody.setPassword("123456");
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

//    /**
//     * 登录方法 普通用戶
//     *
//     * @return 结果
//     */
//    @GetMapping("/login")
//    public AjaxResult login() {
//        LoginBody loginBody = new LoginBody();
//        loginBody.setUsername("cxw1");
//        loginBody.setPassword("123456");
//        AjaxResult ajax = AjaxResult.success();
//        // 生成令牌
//        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
//                loginBody.getUuid());
//        ajax.put(Constants.TOKEN, token);
//        return ajax;
//    }

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
