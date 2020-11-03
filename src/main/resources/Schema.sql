
create table IF NOT EXISTS customers (
    id integer not null,
    name varchar(255) not null
);

insert into customers(id, name) values(1, 'carlos');
insert into customers(id, name) values(2, 'raul');
insert into customers(id, name) values(3, 'Esperanza');