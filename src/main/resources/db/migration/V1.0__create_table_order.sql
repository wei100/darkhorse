CREATE TABLE `food_order`
(
    `id`         bigint(20)   NOT NULL AUTO_INCREMENT,
    `price`  varchar(64)  NOT NULL,
    `address_id`   bigint(20) NOT NULL,
    `goods_id`   bigint(20) NOT NULL,
    `pay_type`  varchar(32)  NOT NULL,
    `status`  varchar(32)  NOT NULL,
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_order_status` (`status`)
);