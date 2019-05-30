create table user_account(
  email varchar(50),
  username varchar(50) PRIMARY KEY
);

create type animal_category as ENUM ('dogs', 'cats', 'other');

create table product (
  id integer PRIMARY KEY,
  name varchar(50),
  animalCategory animal_category,
  price numeric,
  description text,
  gallery text[]
);

create table orders (
  id integer PRIMARY KEY,
  totalPrice numeric,
  time timestamp
);

