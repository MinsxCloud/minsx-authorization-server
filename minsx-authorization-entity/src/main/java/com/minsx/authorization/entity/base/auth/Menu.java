package com.minsx.authorization.entity.base.auth;

import com.alibaba.fastjson.JSON;
import com.minsx.authorization.entity.base.simple.SimpleMinsxEntity;
import com.minsx.authorization.entity.base.type.MenuState;
import com.minsx.authorization.entity.base.type.MenuType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 菜单
 * Created by Joker on 2017/9/17.
 */
@Entity
@Table(name = "minsx_menu")
public class Menu extends SimpleMinsxEntity implements Serializable{

	private static final long serialVersionUID = 7418527767372866553L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "menu_id")
    private Integer id;

    @Column(nullable = false, name = "parent_menu_id")
    private Integer parentMenuId;

    @Column(nullable = false, unique = true, name = "name")
    private String name;

    @Column(nullable = false,name = "icon")
    private String icon;

    @Column(nullable = false,name = "alias")
    private String alias;

    @Column(nullable = false,name = "state")
    private Integer state;

    @Column(nullable = false,name = "sort")
    private Integer sort;

    @Column(name = "value")
    private String value;

    @Column(nullable = false, name = "type")
    private String type;
    
    @Column(name = "description")
    private String description;

    //创建者ID
	@Column(nullable = false, name = "create_user_id")
    private Integer createUserId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Integer parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MenuState getState() {
		return MenuState.getMenuState(this.state);
	}

	public void setState(MenuState menuState) {
		this.state = menuState.getValue();
	}

	public void setState(Integer state) {
		this.state = state;
	}

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MenuType getType() {
        return MenuType.valueOf(type);
    }

    public void setType(MenuType menuType) {
        this.type = menuType.getValue();
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}
