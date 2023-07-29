CREATE TABLE if not exists book_app.book_review_like (
    id serial4 NOT NULL,
    review_id int4 NOT NULL,
    "time" timestamp NOT NULL,
    user_id int4 NOT NULL,
    value int2 NOT NULL,
    CONSTRAINT book_review_like_pkey PRIMARY KEY (id)
);