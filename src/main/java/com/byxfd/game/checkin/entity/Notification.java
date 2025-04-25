package com.byxfd.game.checkin.entity;

import com.byxfd.core.base.entity.BaseEntity;
import com.byxfd.core.base.entity.BaseEntityConfig;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Notification extends BaseEntity {
    public static BaseEntityConfig DEFINE = define(createConfig());
    
    private static Map<String, Object> createConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("table", "tb_notification");
        config.put("pk", "id");
        
        // 字段映射配置
        Map<String, String> columnMap = new HashMap<>();
        // 注意：数据库表中的列名需要从 user_id 更改为 customer_id
        columnMap.put("customerId", "customer_id");
        config.put("columnMap", columnMap);
        
        return config;
    }

    private Long id;
    /**
     * 该字段存储客户ID (customerId)
     * 由于历史原因，数据库表中的列名为 user_id，因此实体类中保留 userId 名称
     * 但在业务逻辑中应当理解为 customerId
     */
    private Long customerId;
    private Long activityId;
    private Integer type;
    private String title;
    private String content;
    private Integer isRead;
    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
} 