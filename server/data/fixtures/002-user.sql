
INSERT IGNORE INTO `users` (`id` ,`email` ,`password` ,`real_name` ,`rank_id` ,`current_score_cache`)
VALUES
    (NULL , 'honza@javorek.net', SHA1('honza'), 'Honza Javorek', '1', '0'),
    (NULL , 'kontakt@michalsvec.cz', SHA1('misa'), 'Míša Švec', '1', '0');

