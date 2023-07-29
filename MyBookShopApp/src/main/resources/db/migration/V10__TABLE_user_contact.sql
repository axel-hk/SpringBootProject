CREATE TABLE if not exists book_app.user_contact (
    id serial4 NOT NULL,
    approved int2 NOT NULL,
    code varchar(255) NOT NULL,
    code_time timestamp NULL,
    code_trails int4 NULL,
    contact varchar(255) NOT NULL,
    "type" int2 NULL,
    user_id int4 NOT NULL,
    CONSTRAINT user_contact_pkey PRIMARY KEY (id)
);