CREATE TABLE if not exists book_app.rate_book (
       id serial4 NOT NULL,
       user_id int4,
       book_id int4 NOT NULL,
       rating int4 NOT NULL,
       CONSTRAINT rate_book_pkey PRIMARY KEY (id)
);