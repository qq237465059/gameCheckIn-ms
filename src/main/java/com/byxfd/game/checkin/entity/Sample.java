package com.byxfd.game.checkin.entity;

import com.byxfd.core.base.entity.BaseEntity;
import com.byxfd.core.base.entity.BaseEntityConfig;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Sample extends BaseEntity {
    public static BaseEntityConfig DEFINE = define(createConfig());
    
    private static Map<String, Object> createConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("table", "sample");
        config.put("pk", "id");
        return config;
    }

    private Long id;
    private String name;
    private String background; //背景图
    private String tags; //标签
    private Integer isPublic; //是否公开
    private Integer status; //状态 待提交、审核中、已发布

    private String createBy; //创建者
    private String updateBy; //更新者
    private Integer version = 0; //版本号
    private LocalDateTime createTime; //创建日期
    private LocalDateTime updateTime; //更新时间
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getBackground() {
        return background;
    }
    
    public void setBackground(String background) {
        this.background = background;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public Integer getIsPublic() {
        return isPublic;
    }
    
    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getCreateBy() {
        return createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    public String getUpdateBy() {
        return updateBy;
    }
    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    
    public Integer getVersion() {
        return version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
