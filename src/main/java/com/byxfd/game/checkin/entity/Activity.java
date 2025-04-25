package com.byxfd.game.checkin.entity;

import com.byxfd.core.base.entity.BaseEntity;
import com.byxfd.core.base.entity.BaseEntityConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Activity extends BaseEntity {
    public static BaseEntityConfig DEFINE = define(createConfig());
    
    private static Map<String, Object> createConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("table", "tb_activity");
        config.put("pk", "id");
        return config;
    }

    private Long id;
    private Long creatorId;
    private String title;
    private String description;
    private LocalDateTime activityTime;
    private String activityLocation;
    private Integer needFaceRecognition;
    private Integer needPhoto;
    private Integer needDeposit;
    private BigDecimal depositAmount;
    private LocalDateTime reservationStartTime;
    private LocalDateTime reservationEndTime;
    private LocalDateTime checkinStartTime;
    private LocalDateTime checkinEndTime;
    private Integer status;
    private Integer participantCount;
    private Integer checkinCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(LocalDateTime activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public Integer getNeedFaceRecognition() {
        return needFaceRecognition;
    }

    public void setNeedFaceRecognition(Integer needFaceRecognition) {
        this.needFaceRecognition = needFaceRecognition;
    }

    public Integer getNeedPhoto() {
        return needPhoto;
    }

    public void setNeedPhoto(Integer needPhoto) {
        this.needPhoto = needPhoto;
    }

    public Integer getNeedDeposit() {
        return needDeposit;
    }

    public void setNeedDeposit(Integer needDeposit) {
        this.needDeposit = needDeposit;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public LocalDateTime getReservationStartTime() {
        return reservationStartTime;
    }

    public void setReservationStartTime(LocalDateTime reservationStartTime) {
        this.reservationStartTime = reservationStartTime;
    }

    public LocalDateTime getReservationEndTime() {
        return reservationEndTime;
    }

    public void setReservationEndTime(LocalDateTime reservationEndTime) {
        this.reservationEndTime = reservationEndTime;
    }

    public LocalDateTime getCheckinStartTime() {
        return checkinStartTime;
    }

    public void setCheckinStartTime(LocalDateTime checkinStartTime) {
        this.checkinStartTime = checkinStartTime;
    }

    public LocalDateTime getCheckinEndTime() {
        return checkinEndTime;
    }

    public void setCheckinEndTime(LocalDateTime checkinEndTime) {
        this.checkinEndTime = checkinEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(Integer participantCount) {
        this.participantCount = participantCount;
    }

    public Integer getCheckinCount() {
        return checkinCount;
    }

    public void setCheckinCount(Integer checkinCount) {
        this.checkinCount = checkinCount;
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