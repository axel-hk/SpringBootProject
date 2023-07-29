CREATE TABLE if not exists book_app.message (
    id serial4 NOT NULL,
    email varchar(255) NULL,
    "name" varchar(255) NULL,
    subject varchar(255) NOT NULL,
    "text" text NOT NULL,
    "time" timestamp NOT NULL,
    user_id int4 NULL,
    CONSTRAINT message_pkey PRIMARY KEY (id)
);