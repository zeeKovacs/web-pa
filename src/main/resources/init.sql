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

CREATE OR REPLACE FUNCTION add_user(name text, email text, role text, password varchar(8), phone_number varchar(15))
RETURNS void AS '
BEGIN
  INSERT INTO users(name, email, role, password, phone_number) VALUES (name, email, role, password, phone_number);
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_product(name text, availability boolean, unit text, price numeric)
RETURNS void AS '
BEGIN
  INSERT INTO products(name, availability, unit, price) VALUES (name, availability, unit, price);
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_order(user_id int)
RETURNS void AS '
BEGIN
  INSERT INTO orders(user_id) VALUES (user_id);
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_order_item(order_id int, product_id int, item_quantity int)
RETURNS void AS '
BEGIN
  INSERT INTO order_item(order_id, product_id, item_quantity, item_price) VALUES (order_id, product_id, item_quantity, 2 * (select price from products where id = product_id));
END;
' LANGUAGE plpgsql;

select add_user('Csontos Julia', 'csontos.julia@freemail.hu', 'admin', '88888888', '+36/70-506-7039');
select add_user('Kovacs Zoltan', 'zolee95@gmail.com', 'admin', '88888888', '+36/70-328-6384');

select add_product('tomato', true, 'kg', 800);

select add_order(1);

select add_order_item(1, 1, 4);