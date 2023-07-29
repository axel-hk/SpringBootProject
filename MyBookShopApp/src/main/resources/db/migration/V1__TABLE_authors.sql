CREATE TABLE if not exists book_app.authors (
     id serial4 NOT NULL,
     description text NULL,
     name varchar(255) NULL,
     photo varchar(255) NULL,
     slug varchar(255) NULL,
     CONSTRAINT authors_pkey PRIMARY KEY (id)
);