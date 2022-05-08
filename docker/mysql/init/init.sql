# create root user and grant rights
CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';
GRANT ALL PRIVILEGES ON *.* TO 'test'@'%';
FLUSH PRIVILEGES;

# create databases
CREATE DATABASE IF NOT EXISTS `trendyol_db`;
CREATE DATABASE IF NOT EXISTS `trendyol_db_test`;

USE `trendyol_db_test`;

CREATE TABLE IF NOT EXISTS `links_storage` (
                                 `id` bigint(20) NOT NULL,
                                 `link` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `link_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `link_type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                 `related_link_id` bigint(20) DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `UK_iaefmcc362kv1kdncx9ukhqom` (`link_hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `hibernate_sequence` (
    `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `hibernate_sequence` (next_val) VALUES(1);



