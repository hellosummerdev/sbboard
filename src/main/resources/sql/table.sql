-- create user table
CREATE table user (
	seq int PRIMARY KEY auto_increment, -- sequencial
	user_id varchar(100) NOT NULL,
	user_name varchar(100) NOT NULL,
	user_pwd varchar(100) NOT NULL,
	use_yn TINYINT DEFAULT 1 NOT NULL,
	created_at datetime default current_timestamp NOT NULL ,
	modified_at datetime default current_timestamp ON UPDATE current_timestamp NOT NULL,
	KEY user_id(user_id)
);


-- create board table
CREATE table board (
	seq int PRIMARY KEY auto_increment, -- sequencial
	board_title varchar(100) NOT NULL,
	board_content text NOT NULL,
	user_id varchar(100) NOT NULL,
	use_yn TINYINT DEFAULT 1 NOT NULL,
	created_at datetime default current_timestamp NOT NULL ,
	modified_at datetime default current_timestamp ON UPDATE current_timestamp NOT NULL,
	FOREIGN KEY board(user_id) REFERENCES user(user_id)
);


-- create comment table
create table comment (
	seq int auto_increment primary key,
	board_seq int, -- FOREIGN KEY
	user_id varchar(100) NOT NULL,
	use_yn TINYINT DEFAULT 1 NOT NULL,
	created_at datetime default current_timestamp NOT NULL,
	modified_at datetime default current_timestamp ON UPDATE current_timestamp NOT NULL,
	FOREIGN KEY comment(user_id) REFERENCES user(user_id)
);

-- add foreign key on comment table
ALTER TABLE comment
ADD FOREIGN KEY (board_seq) REFERENCES board(seq);