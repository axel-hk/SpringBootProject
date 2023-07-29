CREATE TABLE if not exists book_app.book2genre (
     id serial4 NOT NULL,
     book_id int4 NOT NULL,
     genre_id int4 NOT NULL,
     CONSTRAINT book2genre_pkey PRIMARY KEY (id)
);