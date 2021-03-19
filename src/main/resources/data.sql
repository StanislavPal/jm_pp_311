INSERT INTO `roles` (id, role, created_at) VALUES (1,'ROLE_USER','2020-12-15 19:23:17');
INSERT INTO `roles` (id, role, created_at) VALUES (2,'ROLE_ADMIN','2020-12-15 19:23:17');
INSERT INTO `roles` (id, role, created_at) VALUES (4,'ROLE_QUIN','2020-12-15 19:23:17');
INSERT INTO `roles` (id, role, created_at) VALUES (7,'ROLE_TTTTT','2020-12-15 19:23:17');
INSERT INTO `roles` (id, role, created_at) VALUES (8,'ROLE_AAAA','2020-12-15 19:23:17');
INSERT INTO `roles` (id, role, created_at) VALUES (9,'FFFFF','2020-12-15 19:24:03');

INSERT INTO `users` (id, age, last_name, name, password, username) VALUES (1,12,'кккккк','rrrrrr55','$2a$10$WJT3uV.2QdX..hfmNpXvCea5H3fcKRR1BZz.Ea9XF67PmPSUJgkLC','admin');
INSERT INTO `users` (id, age, last_name, name, password, username) VALUES (2,123,'Юзеров','Юзер45','$2a$10$83Ym6tPihGk6V05BdJw2TuE9xknXMY.yOYVpfFcqG6sLr.e6m6zsm','user');
INSERT INTO `users` (id, age, last_name, name, password, username) VALUES (47,123,'aaa','aaa','$2a$10$4.scGM7G.udD5FEOZuKVxObsl3KzrKnS6rcnp6lu7mVGykTEFblRe','aaa');
INSERT INTO `users` (id, age, last_name, name, password, username) VALUES (54,321,'йцукен','йцукен','$2a$10$Iqglu9Z.sjtq90VEhmnAEesJ1oEYEBuwGmLkBzL5lhEu9KEXX6KVG','йцукен');
INSERT INTO `users` (id, age, last_name, name, password, username) VALUES (55,123,'цццццццццццццц','цццццццццц','$2a$10$z1Ga64cGJDVyZ7juJse7ee1Pw9l.ADN9TmNVkD9yg.uAcoDHAsA9e','цццццццц');
INSERT INTO `users` (id, age, last_name, name, password, username) VALUES (56,123,'яяяяяяяяяя','яяяяяяяяяя','$2a$10$SOlaNxDldOcM.PCxHpyNbupvAzwkQls4LtUWLoM2BpWaYoxxPcBu2','яяяяяя');
INSERT INTO `users` (id, age, last_name, name, password, username) VALUES (57,123,'jjjjjjjjjjj','jjjjjjjjjjjj','$2a$10$KQx9GsUz6EFH9/iO/PB4teSDM./l7YNODyYgE/dMUGhLhtAdz2rdu','jjjjj');

INSERT INTO `users_roles` (user_id, role_id) VALUES (2,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (54,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (55,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (56,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (57,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (1,2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (1,7);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2,7);
INSERT INTO `users_roles` (user_id, role_id) VALUES (55,7);
INSERT INTO `users_roles` (user_id, role_id) VALUES (56,7);
INSERT INTO `users_roles` (user_id, role_id) VALUES (47,8);
INSERT INTO `users_roles` (user_id, role_id) VALUES (54,8);
INSERT INTO `users_roles` (user_id, role_id) VALUES (55,8);

