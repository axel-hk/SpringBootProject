CREATE TABLE if not exists book_app.users (
    id serial4 NOT NULL,
    balance int4 NOT NULL,
    hash varchar(255) NOT NULL,
    "name" varchar(255) NOT NULL,
    reg_time timestamp NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);