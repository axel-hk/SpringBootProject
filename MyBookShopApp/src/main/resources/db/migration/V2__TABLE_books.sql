CREATE TABLE if not exists book_app.books (
     id serial4 NOT NULL,
     description text NULL,
     discount int2 NOT NULL DEFAULT 0,
     image varchar(255) NULL,
     is_bestseller int2 NOT NULL,
     price int4 NOT NULL,
     pub_date date NOT NULL,
     slug varchar(255) NOT NULL,
     title varchar(255) NOT NULL,
     author_id int4 NULL,
     CONSTRAINT books_pkey PRIMARY KEY (id),
     CONSTRAINT fkfjixh2vym2cvfj3ufxj91jem7 FOREIGN KEY (author_id) REFERENCES book_app.authors(id)
);