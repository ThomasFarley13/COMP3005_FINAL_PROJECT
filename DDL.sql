drop table holds;
drop table orders;
drop table basket;
drop table author_book;
drop table author;
drop table publisher_numbers;
drop table billing_info;
drop table shipping_info;
drop table book;
drop table users;
drop table publisher;

create table author (
	ID			    SERIAL,
	first_name		varchar(50),
	last_name		varchar(50),
	primary key(id)
);

create table publisher (
	publisher_name			    varchar(50),
	address     	            varchar(50),
	email      		            varchar(255),
	bank_account                numeric(20,0),
	primary key(publisher_name)
);

create table publisher_numbers
(
  publisher_name         		varchar(50), 
  phone_number                  numeric(11,0), 
  foreign key (publisher_name) references publisher(publisher_name) on delete cascade on update cascade
);

create table book (
	ISBN	 				    numeric(13,0),
	title     	          		varchar(100),
	genre                		varchar(100),
	page_length					numeric(5,0),
	purchase_cost				numeric(5,2),
	publisher_name				varchar(50),
	publisher_cut				numeric(5,2),
	stock						numeric(3,0),
	stock_cost					numeric(5,2),
	order_more					boolean default false,
	sale						numeric(5,2),
	primary key(ISBN),
	foreign key(publisher_name) references publisher(publisher_name) on update cascade
);

create table author_book (
	ID							int,
	ISBN						numeric(13,0),
	primary key(ID, ISBN),
	foreign key(ID) references author(ID) on update cascade,
	foreign key(ISBN) references book(ISBN) on delete cascade on update cascade
);

create table users (
	username	 				varchar(50),
	passwd  	          		varchar(50),
	first_name                	varchar(50),
	last_name					varchar(50),
	email						varchar(255),
	phone_number				numeric(11,0),
	user_role					varchar(20),
	primary key(username)
);

create table billing_info (
	username	 				varchar(50),
	credit_card_number  	    numeric(16,0),
	CCV         		       	numeric(3,0),
	expiration_date				varchar(50),
	first_name					varchar(50),
	last_name					varchar(50),
	postal_code					varchar(6),
	primary key(username),
	foreign key(username) references users(username) on delete cascade
);

create table shipping_info (
	username	 				varchar(50),
	country						varchar(50),
	state_province				varchar(50),
	city						varchar(50),
	address						varchar(50),
	postal_code					varchar(6),
	primary key(username),
	foreign key(username) references users(username) on delete cascade
);

create table basket (
	basket_id					SERIAL,
	username	 				varchar(50),
	primary key(basket_id),
	foreign key(username) references users(username) on delete cascade
);

create table orders (
	order_number				SERIAL,
	basket_id					integer,
	shipping_status				varchar(50),
	primary key(order_number),
	foreign key(basket_id) references basket
);

create table holds (
	basket_id	integer,
	ISBN		numeric(13,0),
	quantity	numeric(2,0),
	foreign key(basket_id) references basket(basket_id),
	foreign key(ISBN) references book(ISBN) on delete cascade

);

CREATE OR REPLACE FUNCTION buy_books() returns trigger as
	$$
	begin
		UPDATE book 
		SET order_more = TRUE
		WHERE new.stock < 10;
	RETURN new;
	end;
	$$
language 'plpgsql';	

CREATE TRIGGER book_order
	AFTER UPDATE OF stock ON book
	FOR EACH ROW
	WHEN (new.stock < 10)
	EXECUTE PROCEDURE buy_books();
	


INSERT INTO author
VALUES (DEFAULT, 'James', 'France');
INSERT INTO author
VALUES (DEFAULT, 'James', 'Franco');
INSERT INTO author
VALUES (DEFAULT, 'Billy', 'Mortis');
INSERT INTO author
VALUES (DEFAULT, 'Billy', 'Sorkin');
INSERT INTO author
VALUES (DEFAULT, 'Davis', 'Mayview');
INSERT INTO author
VALUES (DEFAULT, 'Brandy', 'Melville');
INSERT INTO author
VALUES (DEFAULT, 'Jordan', 'Melville');
INSERT INTO author
VALUES (DEFAULT, 'Bill', 'Boomer');
INSERT INTO author
VALUES (DEFAULT, 'James', 'Goober');
INSERT INTO author
VALUES (DEFAULT, 'Cool', 'McGee');
INSERT INTO author
VALUES (DEFAULT, 'Louis', 'Slugger');
INSERT INTO author
VALUES (DEFAULT, 'James', 'Cordon');
	
INSERT INTO publisher
VALUES ('Dorrisville Books', '123 fake road', 'dorris@gmail.com', 1232123412741234);
INSERT INTO publisher
VALUES ('Norrismorris Print', '15 phony road', 'norismorris@gmail.com', 2232123412741234);
INSERT INTO publisher
VALUES ('Penguin Classics', '125 fake street', 'penguinbooks@gmail.com', 3232123412741234);
INSERT INTO publisher
VALUES ('Romy Romcoms', '223 fake road', 'rombus@gmail.com', 1242123412741234);
INSERT INTO publisher
VALUES ('Silvia Smiley Books', '723 fake road', 'smiley@gmail.com', 1232123412541234);
INSERT INTO publisher
VALUES ('Steve Scarsing Products', '103 fake road', 'SteveScarsing@gmail.com', 1232123416641234);
INSERT INTO publisher
VALUES ('Tagora', '100 fake road', 'tagora@gmail.com', 1232123412740004);

INSERT INTO publisher_numbers
VALUES ('Steve Scarsing Products', 1231231234);
INSERT INTO publisher_numbers
VALUES ('Steve Scarsing Products', 1231231235);
INSERT INTO publisher_numbers
VALUES ('Dorrisville Books', 1231231236);
INSERT INTO publisher_numbers
VALUES ('Norrismorris Print', 1231231237);
INSERT INTO publisher_numbers
VALUES ('Norrismorris Print', 1231231238);
INSERT INTO publisher_numbers
VALUES ('Penguin Classics', 1231231239);
INSERT INTO publisher_numbers
VALUES ('Romy Romcoms', 1231231230);
INSERT INTO publisher_numbers
VALUES ('Silvia Smiley Books', 1231231231);
INSERT INTO publisher_numbers
VALUES ('Steve Scarsing Products', 1231231232);
INSERT INTO publisher_numbers
VALUES ('Tagora', 1231231233);

INSERT INTO book
VALUES (1, 'A spooky book', 'Horror', 212, 20.45, 'Steve Scarsing Products', 10.00, 29, 8.40, false, 0.00);
INSERT INTO book
VALUES (2, 'A lovey book', 'Romance', 200, 12.45, 'Romy Romcoms', 13.00, 9, 5.00, true, 0.00);
INSERT INTO book
VALUES (3, 'A valentines book', 'Romance', 180, 13.78, 'Romy Romcoms', 12.00, 20, 5.20, false, 15.00);
INSERT INTO book
VALUES (4, 'An old book', 'Classic', 750, 18.00, 'Penguin Classics', 1.70, 20, 10.00, false, 0.00);
INSERT INTO book
VALUES (5, 'A boring book', 'Classic', 1200, 17.98, 'Penguin Classics', 8.50, 13, 11.11, false, 0.00);
INSERT INTO book
VALUES (6, 'A whimsical book', 'Fantasy', 600, 15.98, 'Norrismorris Print', 11.20, 17, 9.30, false, 0.00);
INSERT INTO book
VALUES (7, 'A baby book', 'Children', 23, 9.45, 'Norrismorris Print', 25.00, 21, 5.40, false, 0.00);
INSERT INTO book
VALUES (8, 'An educational book', 'Educational', 432, 130.23, 'Tagora', 50.00, 11, 30.20, false, 0.00);
INSERT INTO book
VALUES (9, 'A chill story', 'Drama', 193, 23.21, 'Dorrisville Books', 1.20, 10, 4.40, false, 30.00);
INSERT INTO book
VALUES (10, 'A funny book', 'Comedy', 145, 22.22, 'Silvia Smiley Books', 17.00, 12, 4.40, false, 0.00);
INSERT INTO book
VALUES (11, 'A busy book', 'Lifestyle', 98, 30.34, 'Dorrisville Books', 22.00, 26, 2.40, false, 0.00);

INSERT INTO author_book
VALUES (1,1);
INSERT INTO author_book
VALUES (1,2);
INSERT INTO author_book
VALUES (2,3);
INSERT INTO author_book
VALUES (3,4);
INSERT INTO author_book
VALUES (4,5);
INSERT INTO author_book
VALUES (4,6);
INSERT INTO author_book
VALUES (5,7);
INSERT INTO author_book
VALUES (6,8);
INSERT INTO author_book
VALUES (8,9);
INSERT INTO author_book
VALUES (9,10);
INSERT INTO author_book
VALUES (10,11);

INSERT INTO users
VALUES ('admin', 'admin', 'Thomas', 'Farley', 'ThomasFarley3@cmail.carleton.ca', 1234567890, 'ADMIN');
INSERT INTO users
VALUES ('user', 'user', 'George', 'Swanson', 'georgey@gmail.com', 1234567891, 'CLIENT');
INSERT INTO users
VALUES ('william', 'admin', 'Will', 'Warson', 'willy@hotmail.com', 1234567892, 'ADMIN');
INSERT INTO users
VALUES ('lindsey', 'user', 'Lindsey', 'Sterling', 'lindsayyyyyy@gmail.com', 1234567893, 'CLIENT');

INSERT INTO basket
VALUES (DEFAULT,'user');
INSERT INTO basket
VALUES (DEFAULT,'user');
INSERT INTO basket
VALUES (DEFAULT,'user');
INSERT INTO basket
VALUES (DEFAULT,'user');
INSERT INTO basket
VALUES (DEFAULT,'user');

INSERT INTO holds
VALUES (1, 7, 1);
INSERT INTO holds
VALUES (1, 3, 4);
INSERT INTO holds
VALUES (2, 2, 1);
INSERT INTO holds
VALUES (3, 1, 6);
INSERT INTO holds
VALUES (4, 1, 3);
INSERT INTO holds
VALUES (4, 2, 2);
INSERT INTO holds
VALUES (4, 3, 1);
INSERT INTO holds
VALUES (5, 7, 1);
INSERT INTO holds
VALUES (5, 8, 1);
INSERT INTO holds
VALUES (5, 9, 1);
INSERT INTO holds
VALUES (5, 10, 1);

INSERT INTO orders
VALUES(DEFAULT, 1, 'Not yet shipped');
INSERT INTO orders
VALUES(DEFAULT, 2, 'On its way');
INSERT INTO orders
VALUES(DEFAULT, 3, 'On its way');
INSERT INTO orders
VALUES(DEFAULT, 4, 'Arrived');
INSERT INTO orders
VALUES(DEFAULT, 5, 'Not yet shipped');

INSERT INTO billing_info
VALUES ('user',1234432112344321, 111, 2021-12, 'Billy', 'Bob', 'K1J5L3');

INSERT INTO shipping_info
VALUES ('user','Canada','Ontario','Ottawa','12 real avenue', 'K1J6L2');







