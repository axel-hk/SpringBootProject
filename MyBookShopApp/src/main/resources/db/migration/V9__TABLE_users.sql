CREATE TABLE if not exists book_app.users (
    id serial4,
    balance int4,
    hash varchar(255),
    "name" varchar(255) NOT NULL,
    reg_time timestamp NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);