CREATE TABLE book_app.user_contact (
                                       id serial4 NOT NULL,
                                       approved int2,
                                       code varchar(255),
                                       code_time timestamp NULL,
                                       code_trails int4 NULL DEFAULT 0,
                                       email varchar(255) NOT NULL,
                                       user_id int4 NOT NULL,
                                       phone varchar NULL,
                                       CONSTRAINT user_contact_pkey PRIMARY KEY (id)
);