
-- CREATE DATABASE `todogame` DEFAULT CHARACTER SET utf8 COLLATE utf8_czech_ci;

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

CREATE TABLE IF NOT EXISTS `achievements` (
  `user_id` int(10) unsigned NOT NULL,
  `badge_id` int(10) unsigned NOT NULL,
  `quantity` int(10) unsigned NOT NULL COMMENT 'system allows multiple badges of the same type (= incrementing this number)',
  PRIMARY KEY (`user_id`,`badge_id`),
  KEY `badge_id` (`badge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `activity_types` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_czech_ci NOT NULL COMMENT 'human readable name',
  `identifier` varchar(100) COLLATE utf8_czech_ci NOT NULL COMMENT 'machine readable identifier',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identifier` (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `badges` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `identifier` varchar(100) COLLATE utf8_czech_ci NOT NULL COMMENT 'machine readable identifier',
  `name` varchar(100) COLLATE utf8_czech_ci NOT NULL COMMENT 'simple name',
  `description` varchar(250) COLLATE utf8_czech_ci NOT NULL COMMENT 'further description, but still short',
  PRIMARY KEY (`id`),
  UNIQUE KEY `identifier` (`identifier`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `name` varchar(100) COLLATE utf8_czech_ci NOT NULL COMMENT 'custom name',
  `priority` int(10) unsigned NOT NULL COMMENT '= order within user''s categories',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `friendships` (
  `user1_id` int(10) unsigned NOT NULL,
  `user2_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user1_id`,`user2_id`),
  KEY `user2_id` (`user2_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `likes` (
  `id_user` int(10) unsigned NOT NULL,
  `id_task` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id_user`,`id_task`),
  KEY `id_task` (`id_task`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `logs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT 'user performing the activity',
  `activity_type_id` int(10) unsigned NOT NULL COMMENT 'type of activity',
  `task_id` int(10) unsigned NOT NULL COMMENT 'affected task',
  `happened_at` datetime NOT NULL COMMENT 'when it happened',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`,`activity_type_id`,`task_id`),
  KEY `activity_type_id` (`activity_type_id`),
  KEY `task_id` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `priorities` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_czech_ci NOT NULL COMMENT 'human readable name',
  `value` int(10) unsigned NOT NULL COMMENT 'numeric value representing the priority',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `ranks` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `tasks` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) COLLATE utf8_czech_ci NOT NULL,
  `category_id` int(10) unsigned NOT NULL,
  `priority_id` int(10) unsigned NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `current_reward_cache` int(11) NOT NULL DEFAULT '0' COMMENT 'current reward (last computed, cache)',
  `is_done` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`,`priority_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8_czech_ci NOT NULL COMMENT 'username',
  `password` varchar(40) COLLATE utf8_czech_ci NOT NULL COMMENT 'sha1',
  `real_name` varchar(100) COLLATE utf8_czech_ci NOT NULL,
  `rank_id` int(10) unsigned NOT NULL DEFAULT '1',
  `current_score_cache` int(11) unsigned NOT NULL COMMENT 'current score (last computed, cache)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `rank_id` (`rank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_czech_ci;



ALTER TABLE `achievements`
  ADD CONSTRAINT `achievements_ibfk_2` FOREIGN KEY (`badge_id`) REFERENCES `badges` (`id`),
  ADD CONSTRAINT `achievements_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `categories`
  ADD CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `friendships`
  ADD CONSTRAINT `friendships_ibfk_2` FOREIGN KEY (`user2_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `friendships_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `likes`
  ADD CONSTRAINT `likes_ibfk_2` FOREIGN KEY (`id_task`) REFERENCES `tasks` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `likes_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `logs`
  ADD CONSTRAINT `logs_ibfk_3` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `logs_ibfk_2` FOREIGN KEY (`activity_type_id`) REFERENCES `activity_types` (`id`);

ALTER TABLE `tasks`
  ADD CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`rank_id`) REFERENCES `ranks` (`id`);

