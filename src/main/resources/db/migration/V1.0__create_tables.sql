create table if not exists user_account(
  id serial PRIMARY KEY,
  email varchar(50),
  username varchar(50) unique
);

create type animal_category as ENUM ('DOGS', 'CATS', 'OTHER');

create table if not exists product (
  id serial PRIMARY KEY,
  name varchar(50),
  animalCategory animal_category,
  price numeric,
  description text,
  gallery text[]
);

