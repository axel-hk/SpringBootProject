CREATE TABLE if not exists book_app.tags (
     id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
     "name" varchar NOT NULL
);