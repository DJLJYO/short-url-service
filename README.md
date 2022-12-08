# Fast Url

## short-url-sevice

### database:short_url

#### 主表：short_definitional
定义
id,short关键词,源地址,创建时间

```mysql
CREATE TABLE `short_url`.`short_definitional`
(
    `id`          int           NOT NULL AUTO_INCREMENT,
    `short_key`   varchar(128)  NOT NULL UNIQUE KEY COMMENT 'short url关键词',
    `origin_url`  varchar(1000) NOT NULL COMMENT '源地址',
    `create_time` datetime      NOT NULL DEFAULT now() COMMENT '创建时间',
    PRIMARY KEY (`id`)
);
```