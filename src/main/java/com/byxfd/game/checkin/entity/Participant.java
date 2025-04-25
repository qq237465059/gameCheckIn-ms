package com.byxfd.game.checkin.entity;

import com.byxfd.core.base.entity.BaseEntity;
import com.byxfd.core.base.entity.BaseEntityConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Participant extends BaseEntity {
    public static BaseEntityConfig DEFINE = define(createConfig());
    
    private static Map<String, Object> createConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("table", "tb_participant");
        config.put("pk", "id");
        
        // 字段映射配置
        Map<String, String> columnMap = new HashMap<>();
        // 注意：数据库表中的列名需要从 user_id 更改为 customer_id
        columnMap.put("customerId", "customer_id");
        config.put("columnMap", columnMap);
        
        return config;
    }

    private Long id;
    private Long activityId;
    /**
     * 该字段存储客户ID (customerId)
     * 由于历史原因，数据库表中的列名为 user_id，因此实体类中保留 userId 名称
     * 但在业务逻辑中应当理解为 customerId
     */
    private Long customerId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer range;
    private Integer status;
    private Integer depositPaid;
    private BigDecimal frozenAmount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(Integer depositPaid) {
        this.depositPaid = depositPaid;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
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