CREATE TABLE `order_refund`
(
    `id`         bigint(20)   NOT NULL AUTO_INCREMENT,
    `price`  varchar(64)  NOT NULL,
    `order_id`   bigint(20) NOT NULL,
    `status`  varchar(32)  NOT NULL,
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_order_refund_status` (`status`)
);