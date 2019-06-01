create table user_account(
  id serial PRIMARY KEY,
  email varchar(50),
  username varchar(50)
);

create type animal_category as ENUM ('DOGS', 'CATS', 'OTHER');

create table product (
  id serial PRIMARY KEY,
  name varchar(50),
  animalCategory animal_category,
  price numeric,
  description text,
  gallery text[]
);

create table orders (
  id serial PRIMARY KEY,
  totalPrice numeric,
  time timestamp
);

