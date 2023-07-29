CREATE TABLE if not exists book_app.book_review (
    id serial4 NOT NULL,
    book_id int4 NOT NULL,
    "text" text NOT NULL,
    "time" timestamp NOT NULL,
    user_id int4 NOT NULL,
    CONSTRAINT book_review_pkey PRIMARY KEY (id)
);