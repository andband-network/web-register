CREATE TABLE IF NOT EXISTS `registration_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(255) NOT NULL,
  `expiry_date` datetime NOT NULL,
  `token_string` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
