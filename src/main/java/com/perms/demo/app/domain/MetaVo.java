package com.perms.demo.app.domain;

import com.perms.demo.utils.StringUtils;

/**
 * 路由显示信息
 *
 * @author ruoyi
 */
public class MetaVo {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 设置为 true，对应 keepAlive: true
     */
    private boolean cache;

    /**
     * 内链地址（http(s)://开头）
     */
    private String link;

    public MetaVo() {
    }

    public MetaVo(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MetaVo(String title, String icon, boolean cache) {
        this.title = title;
        this.icon = icon;
        this.cache = cache;
    }

    public MetaVo(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public MetaVo(String title, String icon, boolean cache, String link) {
        this.title = title;
        this.icon = icon;
        this.cache = cache;
        if (StringUtils.ishttp(link)) {
            this.link = link;
        }
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}