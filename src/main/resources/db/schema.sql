-- 游戏打卡小程序数据库初始化脚本
-- 创建时间：2023年04月14日

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 1. 客户表
-- ----------------------------
DROP TABLE IF EXISTS `tb_customer`;
CREATE TABLE `tb_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '客户ID，自增主键',
  `open_id` varchar(64) NOT NULL COMMENT '微信OpenID，唯一',
  `nickname` varchar(50) DEFAULT NULL COMMENT '客户昵称',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '客户头像URL',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别(0未知,1男,2女)',
  `balance` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '押金余额',
  `frozen_balance` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '冻结押金',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_open_id` (`open_id`) COMMENT '微信OpenID唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

-- ----------------------------
-- 2. 活动表
-- ----------------------------
DROP TABLE IF EXISTS `tb_activity`;
CREATE TABLE `tb_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '活动ID，自增主键',
  `creator_id` bigint(20) NOT NULL COMMENT '创建者客户ID',
  `title` varchar(100) NOT NULL COMMENT '活动名称',
  `description` text DEFAULT NULL COMMENT '活动描述',
  `activity_time` datetime NOT NULL COMMENT '活动时间',
  `activity_location` varchar(255) NOT NULL COMMENT '活动地点',
  `need_face_recognition` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否需要人脸识别(0否,1是)',
  `need_photo` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否需要拍照(0否,1是)',
  `need_deposit` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否需要保证金(0否,1是)',
  `deposit_amount` decimal(10, 2) DEFAULT NULL COMMENT '保证金金额',
  `reservation_start_time` datetime DEFAULT NULL COMMENT '预约开始时间',
  `reservation_end_time` datetime DEFAULT NULL COMMENT '预约结束时间',
  `checkin_start_time` datetime DEFAULT NULL COMMENT '打卡开始时间',
  `checkin_end_time` datetime DEFAULT NULL COMMENT '打卡结束时间',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '活动状态(1预约中,2待打卡,3已结束)',
  `participant_count` int(11) NOT NULL DEFAULT 0 COMMENT '参与人数',
  `checkin_count` int(11) NOT NULL DEFAULT 0 COMMENT '已打卡人数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_creator_id` (`creator_id`) COMMENT '创建者索引',
  KEY `idx_status` (`status`) COMMENT '状态索引',
  KEY `idx_activity_time` (`activity_time`) COMMENT '活动时间索引',
  CONSTRAINT `fk_activity_creator` FOREIGN KEY (`creator_id`) REFERENCES `tb_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

-- ----------------------------
-- 3. 活动参与者表
-- ----------------------------
DROP TABLE IF EXISTS `tb_participant`;
CREATE TABLE `tb_participant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `latitude` decimal(10, 6) DEFAULT NULL COMMENT '打卡地点纬度',
  `longitude` decimal(10, 6) DEFAULT NULL COMMENT '打卡地点经度',
  `check_radius` int(11) DEFAULT 100 COMMENT '打卡半径(米)',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态(0未签到,1已签到,2迟到,3旷到)',
  `deposit_paid` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已缴纳押金(0否,1是)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_activity_customer` (`activity_id`, `customer_id`) COMMENT '活动用户唯一索引',
  KEY `idx_activity_id` (`activity_id`) COMMENT '活动索引',
  KEY `idx_customer_id` (`customer_id`) COMMENT '客户索引',
  CONSTRAINT `fk_participant_activity` FOREIGN KEY (`activity_id`) REFERENCES `tb_activity` (`id`),
  CONSTRAINT `fk_participant_customer` FOREIGN KEY (`customer_id`) REFERENCES `tb_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动参与者表';

-- ----------------------------
-- 4. 签到记录表
-- ----------------------------
DROP TABLE IF EXISTS `tb_checkin`;
CREATE TABLE `tb_checkin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '签到ID，自增主键',
  `activity_id` bigint(20) NOT NULL COMMENT '活动ID',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `checkin_time` datetime NOT NULL COMMENT '签到时间',
  `checkin_latitude` decimal(10, 6) DEFAULT NULL COMMENT '签到纬度',
  `checkin_longitude` decimal(10, 6) DEFAULT NULL COMMENT '签到经度',
  `checkin_photo` varchar(255) DEFAULT NULL COMMENT '签到照片URL',
  `face_recognition` tinyint(1) DEFAULT 0 COMMENT '是否通过人脸识别(0否,1是)',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态(1正常,2异常)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_activity_customer` (`activity_id`, `customer_id`) COMMENT '活动用户唯一索引',
  KEY `idx_activity_id` (`activity_id`) COMMENT '活动索引',
  KEY `idx_customer_id` (`customer_id`) COMMENT '客户索引',
  CONSTRAINT `fk_checkin_activity` FOREIGN KEY (`activity_id`) REFERENCES `tb_activity` (`id`),
  CONSTRAINT `fk_checkin_customer` FOREIGN KEY (`customer_id`) REFERENCES `tb_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到记录表';

-- ----------------------------
-- 5. 押金记录表
-- ----------------------------
DROP TABLE IF EXISTS `tb_deposit_record`;
CREATE TABLE `tb_deposit_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID，自增主键',
  `customer_id` bigint(20) NOT NULL COMMENT '客户ID',
  `activity_id` bigint(20) DEFAULT NULL COMMENT '活动ID，非活动相关可为空',
  `type` tinyint(1) NOT NULL COMMENT '类型(1充值,2冻结,3解冻,4扣除,5获得)',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `transaction_id` varchar(64) DEFAULT NULL COMMENT '交易ID，充值时记录',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_customer_id` (`customer_id`) COMMENT '客户索引',
  KEY `idx_activity_id` (`activity_id`) COMMENT '活动索引',
  KEY `idx_type` (`type`) COMMENT '类型索引',
  CONSTRAINT `fk_deposit_customer` FOREIGN KEY (`customer_id`) REFERENCES `tb_customer` (`id`),
  CONSTRAINT `fk_deposit_activity` FOREIGN KEY (`activity_id`) REFERENCES `tb_activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='押金记录表';

-- ----------------------------
-- 6. 通知消息表
-- ----------------------------
DROP TABLE IF EXISTS `tb_notification`;
CREATE TABLE `tb_notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '通知ID，自增主键',
  `customer_id` bigint(20) NOT NULL COMMENT '接收客户ID',
  `activity_id` bigint(20) DEFAULT NULL COMMENT '相关活动ID，可为空',
  `type` tinyint(1) NOT NULL COMMENT '类型(1活动提醒,2签到提醒,3押金变动,4活动结果,5系统通知)',
  `title` varchar(100) NOT NULL COMMENT '通知标题',
  `content` text NOT NULL COMMENT '通知内容',
  `is_read` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否已读(0未读,1已读)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_customer_id` (`customer_id`) COMMENT '客户索引',
  KEY `idx_activity_id` (`activity_id`) COMMENT '活动索引',
  KEY `idx_is_read` (`is_read`) COMMENT '已读索引',
  CONSTRAINT `fk_notification_customer` FOREIGN KEY (`customer_id`) REFERENCES `tb_customer` (`id`),
  CONSTRAINT `fk_notification_activity` FOREIGN KEY (`activity_id`) REFERENCES `tb_activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知消息表';

-- ----------------------------
-- 7. 系统配置表
-- ----------------------------
DROP TABLE IF EXISTS `tb_config`;
CREATE TABLE `tb_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID，自增主键',
  `config_key` varchar(50) NOT NULL COMMENT '配置键',
  `config_value` varchar(255) NOT NULL COMMENT '配置值',
  `description` varchar(255) DEFAULT NULL COMMENT '配置描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`) COMMENT '配置键唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- ----------------------------
-- 初始数据
-- ----------------------------

-- 添加系统配置数据
INSERT INTO `tb_config` (`config_key`, `config_value`, `description`) VALUES 
('activity_reminder_time', '60', '活动开始前多少分钟发送提醒(分钟)'),
('checkin_reminder_time', '30', '打卡截止前多少分钟对未打卡用户发送提醒(分钟)'),
('default_check_radius', '100', '默认打卡半径(米)'),
('system_name', '游戏打卡小程序', '系统名称'),
('system_version', '1.0.0', '系统版本');

SET FOREIGN_KEY_CHECKS = 1; 