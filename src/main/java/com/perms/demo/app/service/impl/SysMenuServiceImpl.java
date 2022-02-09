package com.perms.demo.app.service.impl;

import com.perms.demo.app.domain.MetaVo;
import com.perms.demo.app.domain.RouterVo;
import com.perms.demo.app.domain.SysMenu;
import com.perms.demo.app.mapper.SysMenuMapper;
import com.perms.demo.app.service.SysMenuService;
import com.perms.demo.utils.SecurityUtils;
import com.perms.demo.utils.StringUtils;
import com.perms.demo.utils.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author chengxianwei
 * @description 菜单权限表(Menu)服务实现类
 * @date 2022-02-08
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = sysMenuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(userId)) {
            menus = sysMenuMapper.selectMenuTreeAll();
        } else {
            menus = sysMenuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     菜单列表
     * @param parentId 传入的父节点ID
     * @return 菜单列表
     */
    private List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        Iterator<SysMenu> iterator = list.iterator();
        while (iterator.hasNext()) {
            SysMenu t = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                //递归查询
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 菜单列表
     * @param t    父级菜单
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            //是否存在子菜单
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     *
     * @param list 菜单列表
     * @param t    父级菜单
     * @return 菜单列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> returnList = new ArrayList<>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                returnList.add(n);
            }
        }
        return returnList;
    }

    /**
     * 判断是否有子节点
     *
     * @param list 菜单列表
     * @param t    父级菜单
     * @return 结果
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            // 路由信息
            RouterVo router = new RouterVo();
            router.setQuery(menu.getQuery());
            router.setName(menu.getPathName());
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setHidden(Constants.NO_VISIBLE.equals(menu.getVisible()));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals(Constants.YES_CACHE, menu.getCache()), menu.getPath()));
            // 子菜单列表
            List<SysMenu> childMenus = menu.getChildren();
            switch (menu.getMenuType()) {
                //目录
                case Constants.TYPE_DIR:
                    if (StringUtils.isNotEmpty(childMenus)) {
                        // 目录总显示
                        router.setAlwaysShow(true);
                        // 目录无跳转
                        router.setRedirect("noRedirect");
                        // 递归处理子菜单列表
                        router.setChildren(buildMenus(childMenus));
                    }
                    break;
                // 菜单
                case Constants.TYPE_MENU:
                    // 子菜单
                    if (isHttpLink(menu)) {
                        // 链接地址
                        String httpPath = httpLinkReplaceEach(menu.getPath());
                        // 路由链接地址
                        router.setPath(httpPath);
                        // 外部链接标识
                        router.setComponent(Constants.INNER_LINK);
                        // 路由其它信息
                        router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), httpPath));
                    }
                    if (StringUtils.isNotEmpty(childMenus)) {
                        // 递归处理子菜单列表
                        router.setChildren(buildMenus(childMenus));
                    }
                    break;
                // 按钮
                case Constants.TYPE_BUTTON:
                    break;
                default:
                    break;
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        // 路由地址
        String routerPath = menu.getPath();
        // 非外链菜单
        if(Constants.NO_FRAME.equals(menu.getFrame())){
            if (StringUtils.isNotEmpty(routerPath)) {
                if (!menu.getPath().startsWith("/")) {
                    routerPath = "/" + menu.getPath();
                }
            } else {
                routerPath = "/";
            }
        } else {
            routerPath = httpLinkReplaceEach(routerPath);
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = Constants.LAYOUT;
        if(StringUtils.isEmpty(menu.getComponent())){
            // 非主目录
            if(menu.getParentId().intValue() != 0) {
                // 外部链接
                if (isHttpLink(menu)) {
                    component = Constants.INNER_LINK;
                } else if (Constants.TYPE_DIR.equals(menu.getMenuType())) {
                    component = Constants.PARENT_VIEW;
                }
            }
        } else {
            component = menu.getComponent();
        }
        return component;
    }

    /**
     * 是否外部链接
     *
     * @param menu 菜单信息
     * @return 结果
     */
    private boolean isHttpLink(SysMenu menu) {
        return menu.getFrame().equals(Constants.YES_FRAME) && StringUtils.ishttp(menu.getPath());
    }

    /**
     * 域名特殊字符替换
     *
     * @return
     */
    public String httpLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[] { Constants.HTTP, Constants.HTTPS },
                new String[] { "", "" });
    }
}
