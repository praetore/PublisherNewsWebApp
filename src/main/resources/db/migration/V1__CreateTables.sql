create table user
(
  id integer primary key,
  username varchar(255) unique,
  password varchar(255),
  user_level integer not null
);
create table user_level
(
  id int primary key,
  name varchar(20) not null
);
create table news_item
(
   id integer primary key,
   title text not null,
   url text not null
);