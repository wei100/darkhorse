#!/usr/bin/env bash
cd `dirname $0`
docker run \
    -d \
    -p 3306:3306 \
    --name exam \
    -e MYSQL_ROOT_PASSWORD=root\
    -e MYSQL_DATABASE=exam\
    -e character-set-server=utf8mb4\
    -e collation-server=utf8mb4_unicode_ci\
    --mount type=bind,source=`pwd`/conf/mysqld.cnf,target=/etc/mysql/mysql.conf.d/mysqld.cnf \
    mysql:latest
