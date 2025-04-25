package com.byxfd.game.checkin.entity;

import com.byxfd.core.base.entity.BaseEntity;
import com.byxfd.core.base.entity.BaseEntityConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Checkin extends BaseEntity {
    public static BaseEntityConfig DEFINE = define(createConfig());
    
    private static Map<String, Object> createConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("table", "tb_checkin");
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
    private LocalDateTime checkinTime;
    private BigDecimal checkinLatitude;
    private BigDecimal checkinLongitude;
    private Integer isInRange;
    private Integer faceVerified;
    private String photoUrl;
    private Integer status;
    private LocalDateTime createTime;

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

    public LocalDateTime getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(LocalDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }

    public BigDecimal getCheckinLatitude() {
        return checkinLatitude;
    }

    public void setCheckinLatitude(BigDecimal checkinLatitude) {
        this.checkinLatitude = checkinLatitude;
    }

    public BigDecimal getCheckinLongitude() {
        return checkinLongitude;
    }

    public void setCheckinLongitude(BigDecimal checkinLongitude) {
        this.checkinLongitude = checkinLongitude;
    }

    public Integer getIsInRange() {
        return isInRange;
    }

    public void setIsInRange(Integer isInRange) {
        this.isInRange = isInRange;
    }

    public Integer getFaceVerified() {
        return faceVerified;
    }

    public void setFaceVerified(Integer faceVerified) {
        this.faceVerified = faceVerified;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
} 