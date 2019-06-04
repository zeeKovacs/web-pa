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

CREATE OR REPLACE FUNCTION add_user(name text, email text, role text, password varchar(8), phone_number varchar(15))
RETURNS int AS '
DECLARE id int;
BEGIN
  INSERT INTO users(name, email, role, password, phone_number) VALUES (name, email, role, password, phone_number) RETURNING users.id into id;
  RETURN id;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_guest_user()
RETURNS int AS '
DECLARE latest_id int := (SELECT get_next_user_id());
BEGIN
  INSERT INTO users(name, email, role, password) VALUES ((select concat(''Guest'', latest_id)), (select concat(''guest'', latest_id, ''@dummymail.com'')), ''GUEST'', (select concat(''guest'', latest_id))) RETURNING ID into latest_id;
  RETURN latest_id;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_user(idToUpdate int, newName text, newEmail text, newRole text, newPassword varchar(8), new_phone_number varchar(15))
RETURNS int AS '
DECLARE idToReturn int;
BEGIN
  UPDATE users SET name=newName, email=newEmail, role=''USER'', password=newPassword, phone_number=new_phone_number WHERE users.id=idToUpdate RETURNING id into idToReturn;
  RETURN idToReturn;
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

CREATE OR REPLACE FUNCTION find_user_by_email(emailToFind text)
RETURNS SETOF users AS '
BEGIN
  RETURN QUERY SELECT * FROM users WHERE email=emailToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_cart_by_user_id(idToFind int)
RETURNS SETOF carts AS '
BEGIN
  RETURN QUERY SELECT * FROM carts WHERE user_id=idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_items_by_cart_id(idToFind int)
RETURNS SETOF cart_items AS '
BEGIN
  RETURN QUERY SELECT * FROM cart_items WHERE cart_id=idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_user_by_id(idToFind int)
RETURNS SETOF users AS '
BEGIN
  RETURN QUERY SELECT * FROM users WHERE id=idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_product_by_id(idToFind int)
RETURNS SETOF products AS '
BEGIN
  RETURN QUERY SELECT * FROM products WHERE id=idToFind;
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
    RETURN (SELECT max(id+1) from users);
END;
' LANGUAGE plpgsql;

select add_user('Csontos Julia', 'csontos.julia@freemail.hu', 'ADMIN', '88888888', '+36/70-506-7039');
select add_user('Kovacs Zoltan', 'zolee95@gmail.com', 'ADMIN', '88888888', '+36/70-328-6384');

select add_product('tomato', true, 'kg', 800);

select add_cart(1);

select add_cart_item(1, 1, 4);