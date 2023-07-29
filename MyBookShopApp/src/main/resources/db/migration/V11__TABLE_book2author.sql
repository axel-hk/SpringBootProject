CREATE TABLE  if not exists book_app.book2author (
       id serial4 NOT NULL,
       author_id int4 NOT NULL,
       book_id int4 NOT NULL,
       sort_index int4 NOT NULL DEFAULT 0,
       CONSTRAINT book2author_pkey PRIMARY KEY (id)
);