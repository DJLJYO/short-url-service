# Fast Url

## short-url-sevice

### database:short_url

#### 主表：short_definitional

定义 id,short关键词,源地址,创建时间

```mysql
CREATE TABLE `short_url`.`short_definitional`
(
    `id`             int           NOT NULL AUTO_INCREMENT,
    `short_key`      varchar(128)  NOT NULL UNIQUE KEY COMMENT 'short url关键词',
    `md5_composite`  int           NOT NULL DEFAULT 0 COMMENT 'short_key是否加盐,1:加盐 0:常规',
    `origin_url_md5` varchar(128)  NOT NULL UNIQUE KEY COMMENT '源地址32位MD5',
    `origin_url`     varchar(1000) NOT NULL COMMENT '源地址',
    `domain_id`      int           NOT NULL COMMENT '域名_ID',
    `status`         int           NOT NULL DEFAULT 1 COMMENT '状态，1：有效  0：失效',
    `expire_date`    datetime      NOT NULL COMMENT '有效日期',
    `create_time`    datetime      NOT NULL DEFAULT now() COMMENT '创建时间',
    PRIMARY KEY (`id`)
);
```

#### 表：domain

```mysql
CREATE TABLE `short_url`.`domain`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `domain`      varchar(255) NOT NULL COMMENT '域名',
    `status`      int          NOT NULL COMMENT '状态， 1：有效  0：无效',
    `create_time` datetime(0)  NOT NULL DEFAULT now() COMMENT '创建时间',
    PRIMARY KEY (`id`)
)
```