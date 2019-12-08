/*
 Navicat Premium Data Transfer

 Source Server         : 数字化园区-测试环境(10.0.8.8)
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 10.0.8.8:3306
 Source Schema         : datax

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 12/12/2018 12:57:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS datax;
CREATE DATABASE datax;

USE datax;

-- ----------------------------
-- Table structure for dx_dts
-- ----------------------------
DROP TABLE IF EXISTS `dx_dts`;
CREATE TABLE `dx_dts`  (
  `ds_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源ID',
  `ds_desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源描述',
  `ds_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库用户名',
  `ds_pass` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库密码（加密）',
  `ds_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库类型：mysql/oracle',
  `ds_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'JDBC URL',
  `ds_status` int(2) NULL DEFAULT NULL COMMENT '状态 1：有效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `ds_schema` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Schema'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dx_node
-- ----------------------------
DROP TABLE IF EXISTS `dx_node`;
CREATE TABLE `dx_node`  (
  `node_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点ID',
  `node_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点IP',
  `node_port` int(11) NULL DEFAULT NULL COMMENT '节点端口',
  `node_type` int(4) NULL DEFAULT NULL COMMENT '节点类型 1:sftp  2:ftp',
  `node_status` int(2) NULL DEFAULT NULL COMMENT '节点状态',
  `node_login_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录用户名',
  `node_login_pass` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `node_default_path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '/' COMMENT '默认job文件存储路径',
  `node_datax_path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Datax安装目录',
  PRIMARY KEY (`node_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dx_task
-- ----------------------------
DROP TABLE IF EXISTS `dx_task`;
CREATE TABLE `dx_task`  (
  `task_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务ID',
  `task_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `task_desc` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `reader_ds_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '源数据源ID',
  `writer_ds_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标数据源ID',
  `task_content` json NULL COMMENT '任务详情',
  `status` int(2) NULL DEFAULT NULL COMMENT '任务状态：1:有效 -2逻辑删除 -1无效',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '上一次修改时间',
  `task_save_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '任务保存文件名称',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
