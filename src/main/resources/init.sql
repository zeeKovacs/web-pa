DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS cart_items CASCADE;
DROP TABLE IF EXISTS carts CASCADE;

CREATE TABLE users(
    id serial primary key,
    name text unique not null,
    email text unique not null,
    role text not null,
    password varchar(8) not null,
    phone_number varchar(15) default 0
);

CREATE TABLE products(
    id serial PRIMARY KEY,
    name text unique not null,
    availability boolean,
    unit text check (unit in('each', 'kg')),
    price numeric check (price > 0)
);

CREATE TABLE carts(
	id serial primary key,
	user_id int references users(id),
	price numeric default 0
);

CREATE TABLE cart_items(
	id serial primary key,
    cart_id int references carts(id),
    product_id int references products(id),
    quantity numeric not null,
    price numeric not null
);

create or replace function total_cart_price()
returns trigger as '
begin 
update carts set price = (select sum(cart_items.price) from cart_items join carts on cart_items.cart_id = carts.id);
return new;
end;
' language plpgsql;

create trigger total_cart_price
after insert or delete on cart_items for each row
execute procedure total_cart_price();

CREATE OR REPLACE FUNCTION add_user(name text, email text, password varchar(8), phone_number varchar(15))
RETURNS void AS '
BEGIN
  INSERT INTO users(name, email, role, password, phone_number) VALUES (name, email, ''USER'', password, phone_number);
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_guest_user(id int)
RETURNS void AS '
BEGIN
  INSERT INTO users(id, name, email, role, password) VALUES (id, (select concat(''Guest'', id)), (select concat(''guest'', id, ''@dummymail.com'')), ''GUEST'', (select concat(''guest'', id)));
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_product(name text, availability boolean, unit text, price numeric)
RETURNS void AS '
BEGIN
  INSERT INTO products(name, availability, unit, price) VALUES (name, availability, unit, price);
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_cart(user_id int)
RETURNS void AS '
BEGIN
  INSERT INTO carts(user_id) VALUES (user_id);
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_all_user()
RETURNS void AS '
BEGIN
  SELECT * FROM users;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_user_by_email(emailToCheck text)
RETURNS SETOF users AS '
BEGIN
  RETURN QUERY SELECT * FROM users WHERE users.email=emailToCheck;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_cart_item(cart_id int, product_id int, quantity int)
RETURNS void AS '
BEGIN
  INSERT INTO cart_items(cart_id, product_id, quantity, price) VALUES (cart_id, product_id, quantity, quantity * (select price from products where id = product_id));
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_next_user_id()
returns int AS '
BEGIN
    SELECT max(id+1) from users;
END;
' LANGUAGE plpgsql;

select add_user('Csontos Julia', 'csontos.julia@freemail.hu', 'ADMIN', '88888888', '+36/70-506-7039');
select add_user('Kovacs Zoltan', 'zolee95@gmail.com', 'ADMIN', '88888888', '+36/70-328-6384');

select add_product('tomato', true, 'kg', 800);

select add_cart(1);

select add_cart_item(1, 1, 4);