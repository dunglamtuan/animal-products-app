create type order_status as ENUM ('RECEIVED', 'CANCELED', 'PROCESSING', 'DONE');

create table if not exists orders (
  id serial primary key,
  total_price numeric not null,
  created_at timestamp not null,
  user_id integer references user_account(id),
  current_order_status order_status not null
);

create table if not exists order_product_detail (
  id serial primary key,
  order_id integer references orders(id),
  product_id integer references product(id),
  amount integer not null,
  actual_price numeric not null
);

create sequence order_product_detail_sequence start 1;