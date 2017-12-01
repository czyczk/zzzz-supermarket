/*
Navicat MySQL Data Transfer

Source Server         : Lab
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : zzzz_supermarket

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-12-01 12:34:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accounting_record
-- ----------------------------
DROP TABLE IF EXISTS `accounting_record`;
CREATE TABLE `accounting_record` (
  `user_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `difference` decimal(10,2) NOT NULL,
  `type` enum('PURCHASE','REFUND','STOCK') COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`,`time`),
  KEY `idx_time` (`time`),
  KEY `idx_type` (`type`),
  CONSTRAINT `fk_accounting_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of accounting_record
-- ----------------------------

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory` (
  `barcode` bigint(13) unsigned zerofill NOT NULL COMMENT 'Product barcode',
  `production_date` date NOT NULL,
  `manufacturer` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Manufacturer',
  `qty_in_stock` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT 'Quantity in stock',
  `qty_on_shelf` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT 'Quantity on shelf',
  PRIMARY KEY (`barcode`,`production_date`),
  KEY `idx_barcode_production_date` (`barcode`,`production_date`),
  KEY `idx_production_date` (`production_date`),
  KEY `idx_manufacturer` (`manufacturer`),
  KEY `barcode` (`barcode`),
  CONSTRAINT `fk_barcode` FOREIGN KEY (`barcode`) REFERENCES `product` (`barcode`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of inventory
-- ----------------------------
INSERT INTO `inventory` VALUES ('6903244675147', '2017-05-26', '恒安（芜湖）纸业有限公司', '25', '5');
INSERT INTO `inventory` VALUES ('6903244675147', '2017-05-27', '芜湖', '37', '53');
INSERT INTO `inventory` VALUES ('6911988025555', '2017-02-27', '吉林达利食品有限公司', '9', '6');
INSERT INTO `inventory` VALUES ('6911988025593', '2017-10-08', '吉林达利食品有限公司', '23', '3');
INSERT INTO `inventory` VALUES ('6920698493332', '2017-10-28', '沈阳顶益食品有限公司', '25', '10');
INSERT INTO `inventory` VALUES ('6925303771980', '2017-09-17', '济南统一企业有限公司', '50', '15');

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice` (
  `invoice_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `member_id` bigint(20) unsigned DEFAULT NULL,
  `total_price` decimal(10,2) unsigned NOT NULL,
  `discounted_price` decimal(10,2) unsigned DEFAULT NULL,
  PRIMARY KEY (`invoice_id`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_time` (`time`),
  KEY `fk_member_id` (`member_id`),
  CONSTRAINT `fk_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of invoice
-- ----------------------------
INSERT INTO `invoice` VALUES ('1', '2017-11-14 18:42:08', null, '10.00', null);

-- ----------------------------
-- Table structure for invoice-inventory
-- ----------------------------
DROP TABLE IF EXISTS `invoice-inventory`;
CREATE TABLE `invoice-inventory` (
  `invoice_id` bigint(20) unsigned NOT NULL,
  `barcode` bigint(13) unsigned zerofill NOT NULL,
  `production_date` date NOT NULL,
  `qty` smallint(6) NOT NULL,
  PRIMARY KEY (`invoice_id`,`barcode`,`production_date`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_barcode_production_date` (`barcode`,`production_date`) USING BTREE,
  CONSTRAINT `fk_invoice_inventory_barcode_production_date` FOREIGN KEY (`barcode`, `production_date`) REFERENCES `inventory` (`barcode`, `production_date`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_inventory_id` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of invoice-inventory
-- ----------------------------
INSERT INTO `invoice-inventory` VALUES ('1', '6911988025555', '2017-02-27', '1');
INSERT INTO `invoice-inventory` VALUES ('1', '6911988025593', '2017-10-08', '1');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `member_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`member_id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of member
-- ----------------------------

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `barcode` bigint(13) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT 'Globally unified barcode',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Product name',
  `price` decimal(10,2) unsigned NOT NULL COMMENT 'Unit price',
  `shelf_life` smallint(5) unsigned NOT NULL COMMENT 'Shelf life in hours',
  `is_refundable` bit(1) NOT NULL COMMENT 'Whether the product is refundable. (0 for false, 1 for true)',
  PRIMARY KEY (`barcode`),
  KEY `idx_barcode` (`barcode`),
  KEY `idx_name` (`name`),
  KEY `idx_is_refundable` (`is_refundable`)
) ENGINE=InnoDB AUTO_INCREMENT=6925303771981 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('6903244675147', '心相印茶语 丝享 390张', '3.00', '25920', '');
INSERT INTO `product` VALUES ('6911988025555', '豆本豆 纯豆奶 无糖 250mL', '5.00', '6480', '');
INSERT INTO `product` VALUES ('6911988025593', '豆本豆 有机豆奶 250mL', '5.00', '6480', '');
INSERT INTO `product` VALUES ('6920698493332', '康师傅 金汤虾球面', '5.00', '4320', '');
INSERT INTO `product` VALUES ('6925303771980', '统一 酱拌面 贵州豆豉辣酱风味', '6.00', '4320', '');

-- ----------------------------
-- Table structure for promotion
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
  `type` enum('DISCOUNT','COUPON') COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_for_member` bit(1) NOT NULL,
  `discount_reduced_rate` decimal(10,2) unsigned DEFAULT NULL,
  `coupon_threshold` decimal(10,2) unsigned DEFAULT NULL,
  `coupon_value` decimal(10,2) unsigned DEFAULT NULL,
  KEY `idx_type` (`type`),
  KEY `idx_is_for_member` (`is_for_member`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of promotion
-- ----------------------------

-- ----------------------------
-- Table structure for sales_record
-- ----------------------------
DROP TABLE IF EXISTS `sales_record`;
CREATE TABLE `sales_record` (
  `user_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `type` enum('PURCHASE','EXCHANGE','REFUND') COLLATE utf8mb4_unicode_ci NOT NULL,
  `reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `invoice_id` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`time`),
  KEY `idx_user_id_time` (`user_id`,`time`),
  KEY `idx_type` (`type`),
  KEY `idx_time` (`time`),
  KEY `fk_sales_record_invoice_id` (`invoice_id`),
  CONSTRAINT `fk_sales_record_invoice_id` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`invoice_id`) ON UPDATE CASCADE,
  CONSTRAINT `fk_sales_record_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sales_record
-- ----------------------------

-- ----------------------------
-- Table structure for sales_record-inventory
-- ----------------------------
DROP TABLE IF EXISTS `sales_record-inventory`;
CREATE TABLE `sales_record-inventory` (
  `user_id` bigint(20) unsigned NOT NULL,
  `time` datetime NOT NULL,
  `barcode` bigint(13) unsigned zerofill NOT NULL,
  `production_date` date NOT NULL,
  `qty` smallint(6) NOT NULL,
  PRIMARY KEY (`user_id`,`time`,`barcode`,`production_date`),
  KEY `fk_sr-inventory_barcode_production_date` (`barcode`,`production_date`),
  CONSTRAINT `fk_sr-inventory_barcode_production_date` FOREIGN KEY (`barcode`, `production_date`) REFERENCES `inventory` (`barcode`, `production_date`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sr-inventory_user_id_time` FOREIGN KEY (`user_id`, `time`) REFERENCES `sales_record` (`user_id`, `time`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of sales_record-inventory
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'User ID',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Username (should be unique)',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Password',
  `type` enum('ADMINISTRATOR','CLERK','CS_WORKER') COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Type (among ''ADMINISTRATOR'', ''CLERK'' and ''CS_WORKER''',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_username` (`username`) USING BTREE,
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', 'root', 'ADMINISTRATOR');
INSERT INTO `user` VALUES ('2', 'Administrator Tester 1', 'asdf', 'ADMINISTRATOR');
INSERT INTO `user` VALUES ('3', 'Clerk Tester 1', 'asdf', 'CLERK');
INSERT INTO `user` VALUES ('4', 'CS Worker Tester 1', 'asdf', 'CS_WORKER');

-- ----------------------------
-- View structure for v_inventory_with_expiration_date
-- ----------------------------
DROP VIEW IF EXISTS `v_inventory_with_expiration_date`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_inventory_with_expiration_date` AS select `inventory`.`barcode` AS `barcode`,`inventory`.`production_date` AS `production_date`,`inventory`.`manufacturer` AS `manufacturer`,`inventory`.`qty_in_stock` AS `qty_in_stock`,`inventory`.`qty_on_shelf` AS `qty_on_shelf`,(`inventory`.`production_date` + interval `product`.`shelf_life` hour) AS `expiration_date`,`product`.`shelf_life` AS `shelf_life` from (`inventory` join `product` on((`inventory`.`barcode` = `product`.`barcode`))) ;
