CREATE DATABASE IF NOT EXISTS `keycloak` character set UTF8 collate utf8_bin;
CREATE DATABASE IF NOT EXISTS `infinispan` character set UTF8 collate utf8_bin;

-- CREATE USER 'root'@'localhost' IDENTIFIED BY 'local';
GRANT ALL ON *.* TO 'keycloak'@'%';
