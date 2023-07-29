CREATE TABLE  book_app.balance_transaction (
     id serial4 NOT NULL,
     book_id int4 NOT NULL,
     description text NOT NULL,
     "time" timestamp NOT NULL,
     user_id int4 NOT NULL,
     value int4 NOT NULL DEFAULT 0,
     CONSTRAINT balance_transaction_pkey PRIMARY KEY (id)
);