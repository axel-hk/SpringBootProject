CREATE TABLE if not exists book_app.book_file_type (
   id serial4 NOT NULL,
   description text NULL,
   "name" varchar(255) NOT NULL,
   CONSTRAINT book_file_type_pkey PRIMARY KEY (id)
);