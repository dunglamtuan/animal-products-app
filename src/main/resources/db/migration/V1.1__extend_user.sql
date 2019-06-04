alter table user_account add column password varchar(100) not null;
alter table user_account add column isAdmin BOOLEAN not null default false;