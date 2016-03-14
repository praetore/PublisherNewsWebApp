insert into news_item (id, title, url) values (1, 'CNN', 'http://www.cnn.com/');
insert into news_item (id, title, url) values (2, 'FOX News', 'http://www.foxnews.com/');
insert into user (id, username, password, user_level) values (1, 'admin', 'D033E22AE348AEB5660FC2140AEC35850C4DA997', 1);
insert into user (id, username, password, user_level) values (2, 'publisher', 'B497A0AAD7D4C7179B4FA30CCB0B930E674048DD', 2);
insert into user_level (id, name) values (1, 'admin');
insert into user_level (id, name) values (2, 'publisher');