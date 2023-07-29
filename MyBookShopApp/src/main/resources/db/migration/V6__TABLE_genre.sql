CREATE TABLE if not exists book_app.genre (
   id serial4 NOT NULL,
   "name" varchar(255) NOT NULL,
   parent_id int4 NULL,
   slug varchar(255) NOT NULL,
   CONSTRAINT genre_pkey PRIMARY KEY (id)
);