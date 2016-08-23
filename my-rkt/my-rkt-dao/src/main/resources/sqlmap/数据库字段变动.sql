新增templete表
DROP TABLE IF EXISTS `templete`;
CREATE TABLE `templete` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '后台名称',
  `alias` varchar(4) NOT NULL DEFAULT '别名',
  `order` int(10) DEFAULT NULL COMMENT '排序',
  `use_templete` tinyint(4) NOT NULL COMMENT '是否使用模板  1 使用 2未使用',
  `staus` int(11) NOT NULL COMMENT '1.正常状态 -1 删除状态',
  `ctime` datetime NOT NULL,
  `utime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
新增product_templete表
DROP TABLE IF EXISTS `product_templete`;
CREATE TABLE `product_templete` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `templete_id` bigint(20) NOT NULL COMMENT '模板ID',
  `pid` bigint(20) NOT NULL COMMENT '商品编号',
  `ctime` datetime NOT NULL,
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `Index_temp` (`id`,`templete_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
新增category_templete表
DROP TABLE IF EXISTS `category_templete`;
CREATE TABLE `category_templete` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cate_id` int(11) NOT NULL COMMENT '分类ID',
  `templete_id` bigint(20) NOT NULL COMMENT '模板ID',
  `order` int(4) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
新增image表
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_url` varchar(1000) NOT NULL COMMENT '图片地址',
  `cate_id` int(11) NOT NULL COMMENT '分类ID',
  `first_cate_id` int(11) NOT NULL COMMENT '一级分类ID',
  `ctime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
新增字段seller，tempType和shop进去网站商品表
ALTER TABLE `product_b2b`
ADD COLUMN `shop`  bigint(20) NOT NULL COMMENT '店铺ID' AFTER `cate_id`;
ALTER TABLE `product_b2b`
ADD COLUMN `seller`  bigint(20) NOT NULL COMMENT '卖家ID' AFTER `shop`;
ALTER TABLE `product_b2b`
ADD COLUMN `temp_type`  tinyint(4) NOT NULL COMMENT '模板类型 1表示默认使用分类  2表示自定义' AFTER `seller`;
修改数据库已有数据店铺ID和卖家ID的值
UPDATE `product_b2b` SET `shop`='1000', `seller`='1000', `temp_type`='1'

ALTER TABLE `templete`
MODIFY COLUMN `alias`  varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '别名' AFTER `name`;

ALTER TABLE `product_tp`
ADD COLUMN `unitId`  int(11) NULL COMMENT '销售单位Id' AFTER `related_pid`;

ALTER TABLE `product_pop_audit`
MODIFY COLUMN `oper`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员用户名' AFTER `pid`;
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.1.73 : Database - titan 2014-8-25 13:56:23
*********************************************************************
*/

/*Table structure for table `product_iq` */

DROP TABLE IF EXISTS `product_iq`;

CREATE TABLE `product_iq` (
  `pid` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `cate_id` int(11) NOT NULL,
  `user_defined` int(11) DEFAULT NULL,
  `producing_area` bigint(20) NOT NULL,
  `seller` bigint(20) NOT NULL,
  `seller_name` varchar(40) DEFAULT NULL,
  `shop` bigint(20) NOT NULL,
  `shop_name` varchar(50) DEFAULT NULL,
  `indate` int(11) NOT NULL DEFAULT '15',
  `publish_mode` int(11) NOT NULL COMMENT '放入仓库1，审核后立即上架2，指定上架时间3',
  `publish_settime` datetime DEFAULT NULL COMMENT '指定的上架时间',
  `publish_time` datetime DEFAULT NULL,
  `drops_time` datetime DEFAULT NULL COMMENT '每次下架操作都更新此字段',
  `status` int(11) NOT NULL COMMENT '-2=预览 -1=删除 1=待审核（新建）2=审核失败 3=在售 4=仓库中 5=待上架 6=过期下架 7=手动下架',
  `foreign_pid` varchar(50) DEFAULT NULL COMMENT '第三方(taobao,jd)商品外部关联商品id',
  `store_unit` int(11) NOT NULL COMMENT '商品库存单位ID',
  `desc` varchar(500) DEFAULT NULL,
  `prepare_time` bigint(20) NOT NULL COMMENT '商品备货时长在数据字典中的ID',
  `ctime` datetime NOT NULL,
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pid`),
  KEY `Index_product_pop` (`pid`,`name`,`cate_id`,`producing_area`,`seller`,`drops_time`,`publish_mode`,`publish_time`,`status`,`shop`,`indate`,`prepare_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `product_iq_attr` */

DROP TABLE IF EXISTS `product_iq_attr`;

CREATE TABLE `product_iq_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL,
  `attr_id` bigint(20) NOT NULL,
  `attr_type` int(11) NOT NULL,
  `ctime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Index_pro_attr` (`id`,`pid`,`attr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1757 DEFAULT CHARSET=utf8;

/*Table structure for table `product_pop_attr_value` */

DROP TABLE IF EXISTS `product_iq_attr_value`;

CREATE TABLE `product_iq_attr_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL,
  `attr_id` bigint(20) NOT NULL COMMENT '属性ID',
  `value_id` bigint(20) NOT NULL COMMENT '属性值ID，若属性为输入框类型，ID为-1',
  `value_name` varchar(200) DEFAULT NULL COMMENT '属性输入类型为输入框时，将值存在此处',
  `ctime` datetime NOT NULL,
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1591 DEFAULT CHARSET=utf8;

/*Table structure for table `product_iq_audit` */

DROP TABLE IF EXISTS `product_iq_audit`;

CREATE TABLE `product_iq_audit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL COMMENT '商品ID',
  `oper` varchar(11) DEFAULT NULL COMMENT '操作员用户名',
  `status` int(11) NOT NULL COMMENT '状态 1=通过 ，2=不通过',
  `feedback` varchar(500) DEFAULT NULL COMMENT '具体意见内容',
  `ctime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

/*Table structure for table `product_iq_delivery` */

DROP TABLE IF EXISTS `product_iq_delivery`;

CREATE TABLE `product_iq_delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `pid` bigint(20) NOT NULL COMMENT '商品ID',
  `deli_id` int(11) NOT NULL COMMENT '交割地ID',
  `deli_name` varchar(40) DEFAULT NULL COMMENT '交割地名称（此字段值可能和前台不一致，仅作参考使用）',
  `deli_type` int(11) NOT NULL DEFAULT '1',
  `ctime` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1034 DEFAULT CHARSET=utf8;

/*Table structure for table `product_iq_sku` */

DROP TABLE IF EXISTS `product_iq_sku`;

CREATE TABLE `product_iq_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sku` varchar(80) NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `store` int(11) NOT NULL,
  `store_unit` int(11) NOT NULL,
  `mini_purchase` int(11) DEFAULT NULL,
  `pid` bigint(20) NOT NULL,
  `sales` bigint(20) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL COMMENT 'SKU状态，-1删除状态，1新建状态',
  `version` int(11) NOT NULL DEFAULT '0',
  `ctime` datetime NOT NULL,
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_unique_sku_ver` (`sku`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000570 DEFAULT CHARSET=utf8;





/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.1.73  2014-8-27 11:44:43
*********************************************************************
*/
/*!40101 SET NAMES utf8 */; 

/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.1.73  2014-8-27 11:44:43
*********************************************************************
*/
/*!40101 SET NAMES utf8 */; 

/*新增三方商品价格区间表 */
create table `product_pop_price_range` (
	`id` bigint (20) NOT NULL AUTO_INCREMENT,
	`price` int (11),
	`mini_purchase` int (11) COMMENT '区间价格起批量',
	`pid` bigint (20),
	`status` int (11),
	`version` int (11),
	`ctime` datetime ,
	`utime` timestamp ,
	 PRIMARY KEY (`id`)
); 
/*新增自营商品价格区间表 */
create table `product_b2b_price_range` (
	`id` bigint (20) NOT NULL AUTO_INCREMENT,
	`price` int (11),
	`mini_purchase` int (11) COMMENT '区间价格起批量',
	`pid` bigint (20),
	`status` int (11),
	`version` int (11),
	`ctime` datetime ,
	`utime` timestamp ,
	 PRIMARY KEY (`id`)
); 
/*新增类目可搜索属性表 */
CREATE TABLE `category_search_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cate_id` int(11) NOT NULL,
  `attr_id` bigint(20) NOT NULL,
  `order` int(11) NOT NULL,
  `ctime` datetime NOT NULL,
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `Index_cate_attr` (`id`,`cate_id`,`attr_id`,`order`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*网站商品排序表(前端呈现)*/
CREATE TABLE `product_search_sort` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` bigint(11) NOT NULL COMMENT '网站商品PID',
  `search_sort` int(11) NOT NULL COMMENT '排序字段',
  `ctime` datetime DEFAULT NULL,
  `utime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)

/*类目表中新增别名字段 */
ALTER TABLE `category`
ADD COLUMN `alias`  varchar(200) NULL AFTER `name`;

/*自营商品库新增商品的报价类型 */
ALTER TABLE `product_b2b`
ADD COLUMN `quotation_type`  INT(11) NOT NULL  COMMENT '报价类型:1 表示按规格报价销售,2表示按价格区间销售' AFTER `name`;

/*自营商品库新增所在地 */
ALTER TABLE `product_b2b`
ADD COLUMN `locality_area`  bigint(20) DEFAULT NULL COMMENT '所在地' AFTER `producing_area` ;

/*自营商品库新增所在国家地*/
ALTER TABLE `product_b2b`
ADD COLUMN `country_area`  BIGINT(20) DEFAULT NULL COMMENT '所在国家地' AFTER `locality_area` ;

/*自营商品库新增国外城市名*/
ALTER TABLE `product_b2b`
ADD COLUMN `foreign_city_name`  VARCHAR(40) DEFAULT NULL COMMENT '国外城市名' AFTER `country_area` ;

/*自营商品库新增发布地市场ID*/
ALTER TABLE `product_b2b`
ADD COLUMN `published_locationID`  BIGINT(20) DEFAULT NULL COMMENT '商品发布地市场ID' AFTER `foreign_city_name` ;

/*三方商品库新增商品的报价类型 */
ALTER TABLE `product_pop`
ADD COLUMN `quotation_type`  INT(11) NOT NULL COMMENT '报价类型:1 表示按规格报价销售,2表示按价格区间销售' AFTER `name` ;

/*三方商品库新增所在地 */
ALTER TABLE `product_pop`
ADD COLUMN `locality_area`  bigint(20) DEFAULT NULL COMMENT '所在地' AFTER `producing_area` ;

/*三方商品库新增所在国家地*/
ALTER TABLE `product_pop`
ADD COLUMN `country_area`  BIGINT(20) DEFAULT NULL COMMENT '所在国家地' AFTER `locality_area` ;

/*三方商品库新增国外城市名*/
ALTER TABLE `product_pop`
ADD COLUMN `foreign_city_name`  VARCHAR(40) DEFAULT NULL COMMENT '国外城市名' AFTER `country_area` ;

/*供应商商品库新增所在地 */
ALTER TABLE `product_tp`
ADD COLUMN `locality_area`  bigint(20) DEFAULT NULL COMMENT '所在地' AFTER `producing_area` ;

/*供应商商品库新增所在国家地*/
ALTER TABLE `product_tp`
ADD COLUMN `country_area`  BIGINT(20) DEFAULT NULL COMMENT '所在国家地' AFTER `locality_area` ;

/*供应商商品库新增国外城市名*/
ALTER TABLE `product_tp`
ADD COLUMN `foreign_city_name`  VARCHAR(40) DEFAULT NULL COMMENT '国外城市名' AFTER `country_area` ;

/*供应商商品库新增发布地市场ID*/
ALTER TABLE `product_tp`
ADD COLUMN `published_locationID`  BIGINT(20) DEFAULT NULL COMMENT '商品发布地市场ID' AFTER `foreign_city_name` ;

/*修改自营商品的报价方式 */
UPDATE product_b2b SET quotation_type=1 WHERE  quotation_type!=2;
/*修改三方商品的报价方式 */
UPDATE product_pop SET quotation_type=1 WHERE  quotation_type!=2;

/*修改之前老数据(供应商商品所在地与产地一致)*/
UPDATE product_tp SET locality_area =producing_area;
/*修改之前老数据(自营商品所在地与产地一致)*/
UPDATE product_b2b SET locality_area =producing_area;
/*修改之前老数据(三方商品所在地与产地一致)*/
UPDATE product_pop SET locality_area =producing_area;