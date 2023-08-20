CREATE TABLE if not exists book_app.book_file (
    id serial4 NOT NULL,
    hash varchar(255) NOT NULL,
    "path" varchar(255) NOT NULL,
    type_id int4 NOT NULL,
    book_id int4 NOT NULL,
    CONSTRAINT book_file_pkey PRIMARY KEY (id)
);