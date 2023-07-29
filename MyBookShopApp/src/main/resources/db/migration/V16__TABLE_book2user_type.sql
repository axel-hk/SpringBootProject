CREATE TABLE if not exists book_app.book2user_type (
   id serial4 NOT NULL,
   code varchar(255) NOT NULL,
   "name" varchar(255) NOT NULL,
   CONSTRAINT book2user_type_pkey PRIMARY KEY (id)
);