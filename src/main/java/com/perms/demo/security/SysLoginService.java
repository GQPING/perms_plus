package com.perms.demo.security;

import com.perms.demo.app.domain.LoginUser;
import com.perms.demo.app.domain.SysUser;
import com.perms.demo.app.service.SysUserService;
import com.perms.demo.exception.*;
import com.perms.demo.redis.RedisCache;
import com.perms.demo.token.TokenService;
import com.perms.demo.utils.ServletUtils;
import com.perms.demo.utils.constant.Constants;
import com.perms.demo.utils.ip.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Slf4j
@Component
public class SysLoginService {

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {

        // 验证码校验
        //validateCaptcha(username, code, uuid);

        // 用户身份验证
        Authentication authentication = null;

        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                log.info("-------------- 登录失败：密码错误! --------------");
                throw new BaseException("user.password.not.match");
            } else {
                log.info("-------------- 登录失败：" + e.getMessage() + "--------------");
                throw new BaseException(e.getMessage());
            }
        }

        log.info("-------------- 登录成功! --------------");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //记录登录信息
        recordLoginInfo(loginUser.getUserId());

        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            log.info("-------------- 登录失败：验证码超时! --------------");
            throw new BaseException("user.jcaptcha.expire");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            log.info("-------------- 登录失败：验证码错误! --------------");
            throw new BaseException("user.jcaptcha.error");
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(new Date());
        sysUserService.update(sysUser);
    }
}