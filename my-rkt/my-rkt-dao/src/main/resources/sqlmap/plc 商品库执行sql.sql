
/*==============================================================*/
/* Table: attribute                                             */
/*==============================================================*/
CREATE TABLE attribute
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   `name`                 VARCHAR(40) NOT NULL COMMENT '属性后台名称',
   alias                VARCHAR(40) NOT NULL COMMENT '属性前台别名',
   inputtype            INT(11) NOT NULL COMMENT '属性输入类型:(1单选框、2复选框、3多行输入框)',
   `type`                 INT(11) NOT NULL COMMENT '属性类型(1描述属性、2销售属性)',
   required             TINYINT(1) NOT NULL COMMENT '属性是否为必填项(0非必填 、1必填)',
   `status`               INT(11) COMMENT '属性状态( -1删除、1正常、10锁定)',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   KEY Index_uniqe_attr (TYPE, NAME)
);

ALTER TABLE attribute COMMENT '属性表';

/*==============================================================*/
/* Index: Index_attr                                            */
/*==============================================================*/
CREATE INDEX Index_attr ON attribute
(
   id
);

/*==============================================================*/
/* Table: attribute_value                                       */
/*==============================================================*/
CREATE TABLE attribute_value
(
   id                   BIGINT(20) NOT NULL,
   attr_id              BIGINT(20) NOT NULL COMMENT '属性id',
   `value`                VARCHAR(200) NOT NULL COMMENT '属性值',
   `order`              INT(11) NOT NULL COMMENT '属性的属性值排序',
   `show`                 TINYINT(1) NOT NULL COMMENT '属性值是否呈现（0隐藏、1显示）',
   `status`               INT(11) NOT NULL COMMENT '属性值状态( -1删除、1正常)',
   ctime                DATETIME DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   KEY index_value_unique (VALUE, attr_id)
);

ALTER TABLE attribute_value COMMENT '属性值表';

/*==============================================================*/
/* Index: index_attr_value                                      */
/*==============================================================*/
CREATE INDEX index_attr_value ON attribute_value
(
   id,attr_id
);

/*==============================================================*/
/* Table: category                                              */
/*==============================================================*/
CREATE TABLE category
(
   id                   INT(11) NOT NULL,
   `name`                 VARCHAR(80) NOT NULL COMMENT '后台名称',
   alias                VARCHAR(200) COMMENT '前台别名',
   parent               INT(11) NOT NULL COMMENT '父级id(0 顶级目录)',
   `order`              INT(11) NOT NULL COMMENT '类目排序字段',
   `status`               INT(11) NOT NULL COMMENT '类目状态( -1 删除、1正常)',
   activate             TINYINT(1) NOT NULL COMMENT '类目激活状态( 0 未激活 、1激活)',
   ctime                DATETIME DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   KEY Index_unique_cate (parent, NAME)
);

/*==============================================================*/
/* Index: Index_cate                                            */
/*==============================================================*/
CREATE INDEX Index_cate ON category
(
   id,parent
);

/*==============================================================*/
/* Table: category_attr                                         */
/*==============================================================*/
CREATE TABLE category_attr
(
   id                   BIGINT(20) NOT NULL,
   cate_id              INT(11) NOT NULL COMMENT '类目id',
   attr_id              BIGINT(20) NOT NULL COMMENT '属性id',
   searchable           INT(1),
   `order`              INT(11) NOT NULL COMMENT '类目下所挂属性的排序',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE category_attr COMMENT '类目属性表';

/*==============================================================*/
/* Index: Index_cate_attr                                       */
/*==============================================================*/
CREATE INDEX Index_cate_attr ON category_attr
(
   cate_id,attr_id
);

/*==============================================================*/
/* Table: category_search_attr                                  */
/*==============================================================*/
CREATE TABLE category_search_attr
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   cate_id              INT(11) NOT NULL COMMENT '类目id',
   attr_id              BIGINT(20) NOT NULL COMMENT '属性id',
   `order`              INT(11) NOT NULL COMMENT '可搜索排序字段',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE category_search_attr COMMENT '可搜索属性表';

/*==============================================================*/
/* Index: Index_cate_attr                                       */
/*==============================================================*/
CREATE INDEX Index_cate_attr ON category_search_attr
(
    cate_id,attr_id
);

/*==============================================================*/
/* Table: category_seo                                          */
/*==============================================================*/
CREATE TABLE category_seo
(
   id                   BIGINT(20) NOT NULL,
   cate_id              INT(11) NOT NULL COMMENT '类目id',
   seo_title            VARCHAR(500) NOT NULL COMMENT 'seo标题',
   seo_keyword          VARCHAR(500) NOT NULL COMMENT '关键字',
   seo_desc             VARCHAR(500) NOT NULL COMMENT '说明',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE category_seo COMMENT '类目seo表';

/*==============================================================*/
/* Table: category_templete                                     */
/*==============================================================*/
CREATE TABLE category_templete
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   cate_id              INT(11) NOT NULL COMMENT '分类ID',
   templete_id          BIGINT(20) NOT NULL COMMENT '模板ID',
   `order`              INT(4) NOT NULL COMMENT '排序',
   PRIMARY KEY (id)
);

ALTER TABLE category_templete COMMENT '类目所挂模板表';

/*==============================================================*/
/* Table: image                                                 */
/*==============================================================*/
CREATE TABLE image
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   image_url            VARCHAR(1000) NOT NULL COMMENT '图片地址',
   cate_id              INT(11) NOT NULL COMMENT '分类ID',
   ctime                DATETIME DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE image COMMENT '我的图片库表';

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
CREATE TABLE product
(
   pid                  BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
   base_pid             BIGINT(20) NOT NULL COMMENT '基础商品库id',
   market_id            BIGINT COMMENT '市场id',
   unit_id              INT COMMENT '销售单位id',
   cate_id              INT(11) NOT NULL COMMENT '类目',
   producing_area       BIGINT(20) COMMENT '产地',
   `status`               INT(11) NOT NULL COMMENT '-2=预览 -1=删除  3=在售 4=仓库中 5=待上架 6=过期下架 7=手动下架',
   publish_time         DATETIME COMMENT '上架时间',
   drops_time           DATETIME COMMENT '下架时间',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (pid)
);

/*==============================================================*/
/* Index: Index_product_b2b                                     */
/*==============================================================*/
CREATE INDEX Index_product_b2b ON product
(
   pid,base_pid,market_id,cate_id
);

/*==============================================================*/
/* Table: product_attr                                          */
/*==============================================================*/
CREATE TABLE product_attr
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   pro_pid              BIGINT(20) NOT NULL COMMENT '网站商品自增的pid',
   attr_id              BIGINT(20) NOT NULL COMMENT '属性id',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

ALTER TABLE product_attr COMMENT '网站商品销售属性';

/*==============================================================*/
/* Index: Index_pro_attr                                        */
/*==============================================================*/
CREATE INDEX Index_pro_attr ON product_attr
(
   id,pro_pid,attr_id
);

/*==============================================================*/
/* Table: product_base                                          */
/*==============================================================*/
CREATE TABLE product_base
(
   pid                  BIGINT(20) NOT NULL,
   `name`                 VARCHAR(100) NOT NULL COMMENT '商品名称',
   cate_id              INT(11) NOT NULL COMMENT '类目',
   temp_type            TINYINT,
   unit_Id              INT(11) COMMENT '销售单位Id',
   `status`               INT(11) NOT NULL COMMENT ' -2=预览 -1=删除 1=在售 2=停售   3待入库',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (pid)
);

ALTER TABLE product_base COMMENT '基础商品库表';

/*==============================================================*/
/* Index: Index_product_tp                                      */
/*==============================================================*/
CREATE INDEX Index_product_tp ON product_base
(
    pid,cate_id
);

/*==============================================================*/
/* Table: product_base_addi_cate                                */
/*==============================================================*/
CREATE TABLE product_base_addi_cate
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   pid                  BIGINT(20) NOT NULL,
   cate_id              INT(11) NOT NULL,
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   KEY Index_unique_cate (cate_id, pid)
);

/*==============================================================*/
/* Index: Index_cate                                            */
/*==============================================================*/
CREATE INDEX Index_cate ON product_base_addi_cate
(
   pid,cate_id
);

/*==============================================================*/
/* Table: product_base_attr                                     */
/*==============================================================*/
CREATE TABLE product_base_attr
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   pid                  BIGINT(20) NOT NULL,
   attr_id              BIGINT(20) NOT NULL,
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Index: Index_pro_attr                                        */
/*==============================================================*/
CREATE INDEX Index_pro_attr ON product_base_attr
(
   pid,attr_id
);

/*==============================================================*/
/* Table: product_base_attr_value                               */
/*==============================================================*/
CREATE TABLE product_base_attr_value
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   pid                  BIGINT(20) NOT NULL,
   attr_id              BIGINT(20) NOT NULL,
   value_id             BIGINT(20) NOT NULL,
   value_name           VARCHAR(200),
   ctime                DATETIME NOT NULL,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: product_img                                           */
/*==============================================================*/
CREATE TABLE product_img
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   pid                  BIGINT(20) NOT NULL,
   img_url              VARCHAR(1000) NOT NULL,
   `order`              BIGINT(20) NOT NULL,
   `desc`               VARCHAR(200),
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Index: Index_img                                             */
/*==============================================================*/
CREATE INDEX Index_img ON product_img
(
     id,pid
);

/*==============================================================*/
/* Table: product_sku                                           */
/*==============================================================*/
CREATE TABLE product_sku
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   pid                  BIGINT(20),
   sku                  VARCHAR(200) NOT NULL,
   price                BIGINT NOT NULL,
   store                INT(11) NOT NULL,
   unit_id              INT(11) NOT NULL COMMENT '销售单位id',
   supply_price         BIGINT NOT NULL COMMENT '供应价',
   market_id            BIGINT COMMENT '市场id',
   `status`               INT(11) NOT NULL,
   `version`              INT(11) NOT NULL,
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: product_templete                                      */
/*==============================================================*/
CREATE TABLE product_templete
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   templete_id          BIGINT(20) NOT NULL COMMENT '模板ID',
   pid                  BIGINT(20) NOT NULL COMMENT '商品编号',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Index: Index_temp                                            */
/*==============================================================*/
CREATE INDEX Index_temp ON product_templete
(
    id,pid,templete_id
);

/*==============================================================*/
/* Table: stock_operate                                         */
/*==============================================================*/
CREATE TABLE stock_operate
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   sn                   BIGINT(20) NOT NULL,
   pid                  BIGINT(20),
   sku                  VARCHAR(100) NOT NULL,
   operate              INT(11),
   `count`                INT(11) NOT NULL,
   STATUS               INT(11) NOT NULL COMMENT '1=已锁定，2=已退还',
   service              INT(11) NOT NULL,
   service_no           VARCHAR(80),
   `desc`               VARCHAR(200),
   ctime                DATETIME NOT NULL,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   KEY index_unique_sn_sku (sku, sn)
);

/*==============================================================*/
/* Index: index_inventory                                       */
/*==============================================================*/
CREATE INDEX index_inventory ON stock_operate
(
   id,pid
);

/*==============================================================*/
/* Table: templete                                              */
/*==============================================================*/
CREATE TABLE templete
(
   id                   BIGINT(20) NOT NULL AUTO_INCREMENT,
   `name`                 VARCHAR(200) NOT NULL,
   alias                VARCHAR(15) NOT NULL,
   `order`              INT(10) COMMENT '排序',
   use_templete         TINYINT(4) NOT NULL COMMENT '是否使用模板  1 使用 2未使用',
   staus                INT(11) NOT NULL COMMENT '1.正常状态 -1 删除状态',
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
);

/*==============================================================*/
/* Table: titan_sequence                                        */
/*==============================================================*/
CREATE TABLE titan_sequence
(
   id                   INT(11) NOT NULL AUTO_INCREMENT,
   sqname               VARCHAR(32) NOT NULL,
   sqvalue              BIGINT(11) NOT NULL,
   sqmax                BIGINT(11),
   ctime                DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   utime                DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (id),
   KEY index_sqname (sqname)
);

CREATE TABLE `system_configure` (
	`id` BIGINT (20),
	`front_name` VARCHAR (765),
	`name` VARCHAR (765),
	`attr_key` VARCHAR (765),
	`value` INT (11),
	`module` VARCHAR (765),
	`ctime` DATETIME ,
	`utime` DATETIME 
); 
INSERT INTO `system_configure` (`id`, `front_name`, `name`, `attr_key`, `value`, `module`, `ctime`, `utime`) VALUES('1','描述属性','attr_type','attr_desc','1','attribute','2014-06-27 11:22:29',NULL);
INSERT INTO `system_configure` (`id`, `front_name`, `name`, `attr_key`, `value`, `module`, `ctime`, `utime`) VALUES('2','销售属性','attr_type','attr_sale','2','attribute','2014-06-27 11:22:29',NULL);
INSERT INTO `system_configure` (`id`, `front_name`, `name`, `attr_key`, `value`, `module`, `ctime`, `utime`) VALUES('3','必填','attr_required','attr_required','1','attribute','2014-06-27 11:22:29',NULL);
INSERT INTO `system_configure` (`id`, `front_name`, `name`, `attr_key`, `value`, `module`, `ctime`, `utime`) VALUES('4','单选项','input_type','input_type_radio','1','attribute','2014-06-27 11:22:29',NULL);
INSERT INTO `system_configure` (`id`, `front_name`, `name`, `attr_key`, `value`, `module`, `ctime`, `utime`) VALUES('5','复选框','input_type','input_type_check_box','2','attribute','2014-06-27 11:22:29',NULL);
INSERT INTO `system_configure` (`id`, `front_name`, `name`, `attr_key`, `value`, `module`, `ctime`, `utime`) VALUES('6','多行输入框','input_type','input_type_text_area','3','attribute','2014-06-27 11:22:29',NULL);


/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.1.73 : Database - titan
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`titan` /*!40100 DEFAULT CHARACTER SET utf8 */;


/* Procedure structure for procedure `pr_batch_sequence` */

/*!50003 DROP PROCEDURE IF EXISTS  `pr_batch_sequence` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`%` PROCEDURE `pr_batch_sequence`(
     IN  _name   VARCHAR(32),
     IN  _min  BIGINT,
     IN  _max  BIGINT,    
     IN  _step INT,
     OUT _out_min BIGINT,
     OUT _out_max BIGINT
)
BEGIN
    DECLARE countnum INT;
    DECLARE value_temp BIGINT;
    SELECT COUNT(*) INTO countnum FROM titan_sequence WHERE sqname=_name;
	
    IF countnum=0 THEN
        INSERT INTO titan_sequence(sqname,sqvalue,ctime) VALUES(_name,_min+_step-1,NOW());
        COMMIT;
        SET _out_min=_min;   
        SET _out_max=_min+_step-1;  
    ELSE
        SELECT sqvalue INTO value_temp FROM titan_sequence WHERE sqname=_name;
        IF value_temp+_step<=_max THEN
            UPDATE titan_sequence SET sqvalue=sqvalue+_step,utime=NOW() WHERE sqname=_name;
            COMMIT;
            SET _out_min=value_temp+1;
            SET _out_max=value_temp+_step; 
        ELSE
            UPDATE titan_sequence SET sqvalue=_min+_step,utime=NOW() WHERE sqname=_name;
            COMMIT;
            SET _out_min=_min;  
            SET _out_max=_min+_step-1;
        END IF;
    END IF;
    SELECT _out_min,_out_max;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;




/*存储过程范例*/
DELIMITER $$

USE `test`$$

DROP PROCEDURE IF EXISTS `pr_batch_sequence`$$

CREATE DEFINER=`root`@`%` PROCEDURE `pr_batch_sequence`(
     IN  _name   VARCHAR(32),
     IN  _min  BIGINT,
     IN  _max  BIGINT,    
     IN  _step INT,
     OUT _out_min BIGINT,
     OUT _out_max BIGINT
)
BEGIN
    DECLARE countnum INT;
    DECLARE value_temp BIGINT;
    SELECT COUNT(*) INTO countnum FROM titan_sequence WHERE sqname=_name;
	
    IF countnum=0 THEN
        INSERT INTO titan_sequence(sqname,sqvalue,ctime) VALUES(_name,_min+_step-1,NOW());
        COMMIT;
        SET _out_min=_min;   
        SET _out_max=_min+_step-1;  
    ELSE
        SELECT sqvalue INTO value_temp FROM titan_sequence WHERE sqname=_name;
        IF value_temp+_step<=_max THEN
            UPDATE titan_sequence SET sqvalue=sqvalue+_step,utime=NOW() WHERE sqname=_name;
            COMMIT;
            SET _out_min=value_temp+1;
            SET _out_max=value_temp+_step; 
        ELSE
            UPDATE titan_sequence SET sqvalue=_min+_step,utime=NOW() WHERE sqname=_name;
            COMMIT;
            SET _out_min=_min;  
            SET _out_max=_min+_step-1;
        END IF;
    END IF;
    SELECT _out_min,_out_max;
END$$

DELIMITER ;