package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * sys_menu
 * @author 
 */
@Data
public class SysMenu implements Serializable {
    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型;1：目录、2：菜单、3：按钮
     */
    private String menuType;

    /**
     * 菜单权限标识
     */
    private String menuPerms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 路由名称
     */
    private String pathName;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单状态;1：正常、2：停用
     */
    private String status;

    /**
     * 是否显示;1：显示、2：隐藏
     */
    private String visible;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 是否外链;1：外链、2：非外链
     */
    private String frame;

    /**
     * 是否缓存;1、缓存、2：不缓存
     */
    private String cache;

    private static final long serialVersionUID = 1L;
}