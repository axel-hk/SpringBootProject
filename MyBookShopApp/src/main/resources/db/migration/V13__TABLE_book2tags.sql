CREATE TABLE if not exists book_app.book2tags (
    id int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    book_id int8 NOT NULL,
    tag_id int8 NULL,
    CONSTRAINT book2tags_fk FOREIGN KEY (book_id) REFERENCES book_app.books(id)
);