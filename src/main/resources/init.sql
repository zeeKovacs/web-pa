DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS order_item CASCADE;
DROP TABLE IF EXISTS orders CASCADE;

CREATE TABLE users(
    id serial primary key,
    name text unique not null,
    email text unique not null,
    role text,
    password varchar(8) not null,
    phone_number varchar(15)
);

CREATE TABLE products(
    id serial PRIMARY KEY,
    name text unique not null,
    availability boolean,
    unit text check (unit in('each', 'kg')),
    price numeric check (price > 0)
);

CREATE TABLE orders(
	id serial primary key,
	user_id int references users(id),
	price numeric default 0
);

CREATE TABLE order_item(
	id serial primary key,
    order_id int references orders(id),
    product_id int references products(id),
    item_quantity numeric not null,
    item_price numeric not null
);

create or replace function total_order_price()
returns trigger as '
begin 
update orders set price = (select sum(item_price) from order_item join orders on order_item.order_id = orders.id);
return new;
end;
' language plpgsql;

create trigger total_order_price
after insert or delete on order_item for each row
execute procedure total_order_price();

insert into users(name, email, role, password, phone_number) values(
	'Kovacs Zoltan',
	'zolee95@gmail.com',
	'admin',
	'88888888',
	'+36/70-328-6384'
);

insert into products(name, availability, unit, price) values(
	'tomato',
	true,
	'kg',
	800	
);

insert into orders(user_id) values(
	1
);

insert into order_item (order_id, product_id, item_quantity, item_price) values(
	1,
	1,
	4,
	2 * (select price from products where id = 1)
);