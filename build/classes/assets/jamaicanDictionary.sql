DROP DATABASE IF EXISTS jamaicanDictionary;
create database jamaicanDictionary;
use jamaicanDictionary;

create table lesson
(
	id int auto_increment primary key ,
	lesson_name varchar(30),
	lesson_progress int,
	lesson_detail varchar(20)
);

create table word(
  word_id int auto_increment primary key,
  word varchar(300),
  wordOrPhrase bit,
  translation varchar(20),
  audiofile varchar(250),
  category varchar(20)
);


create table pictures(
	id int auto_increment primary key,
	picture_location varchar(300),
	picture_name varchar(30),
	picture_translation varchar(30),
	category varchar(30)
);

create table users(
	id int auto_increment primary key,
	userName varchar(30),
	password char(40)
);

create table lessons_word
(
	word_id int,
	lesson_id int,
	picture_id int,
	completed int,
	foreign key(lesson_id) references lesson(id),
    foreign key(picture_id) references pictures(id),
	foreign key(word_id) references word(word_id)
);


create table user_lesson(
	user_id int,
	lesson_id int,
	completed int,
	foreign key(user_id) references users(id),
	foreign key(lesson_id) references lesson(id)
);

create table markers
(
	map_id int auto_increment primary key,
	address varchar(80),
	longitude float,
	latitude float,
	Name varchar(200)
);
