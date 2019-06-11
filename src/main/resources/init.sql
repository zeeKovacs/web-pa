DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS cart_items CASCADE;
DROP TABLE IF EXISTS carts CASCADE;
DROP TABLE IF EXISTS orders CASCADE;

CREATE TABLE users(
    id serial primary key,
    name text unique not null,
    email text unique not null,
    role text not null,
    password varchar(8) not null
);

CREATE TABLE products(
    id serial PRIMARY KEY,
    name text unique not null,
    availability boolean,
    unit text check (unit in('each', 'kg')),
    picture text not null,
    price numeric check (price > 0)
);

CREATE TABLE carts(
	id serial primary key,
	user_id int references users(id) default null,
	price numeric default 0,
	checked_out boolean default false
);

CREATE TABLE cart_items(
    id serial primary key,
    cart_id int references carts(id),
    product_id int references products(id),
    quantity numeric not null,
    price numeric not null
);

CREATE TABLE orders(
    id serial primary key,
    user_id int references users(id) default null,
    cart_id int references carts(id),
    name text,
    email text,
    confirmed boolean default false,
    complete boolean default false
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

CREATE OR REPLACE FUNCTION add_user(name text, email text, role text, password varchar(8))
RETURNS int AS '
DECLARE id int;
BEGIN
  INSERT INTO users(name, email, role, password) VALUES (name, email, role, password) RETURNING users.id into id;
  RETURN id;
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

CREATE OR REPLACE FUNCTION assign_cart_to_user(whereToUpdate int, idToUpdate int)
RETURNS int AS '
DECLARE idToReturn int;
BEGIN
  UPDATE carts SET user_id = idToUpdate WHERE carts.id=whereToUpdate RETURNING id into idToReturn;
  RETURN idToReturn;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_product(name text, availability boolean, unit text, picture text, price numeric)
RETURNS void AS '
BEGIN
  INSERT INTO products(name, availability, unit, picture, price) VALUES (name, availability, unit, picture, price);
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_cart(user_id int)
RETURNS int AS '
DECLARE idToReturn int;
BEGIN
  INSERT INTO carts(user_id) VALUES (user_id) returning id into idToReturn;
  RETURN idToReturn;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_guest_cart()
RETURNS int AS '
DECLARE idToReturn int;
BEGIN
  INSERT INTO carts default values returning id into idToReturn;
  RETURN idToReturn;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_user_order(cart_id int, user_id int)
RETURNS int AS '
DECLARE idToReturn int;
BEGIN
  INSERT INTO orders(cart_id, user_id, name, email) values (cart_id, user_id, (SELECT name from users where id=user_id), (SELECT email from users where id=user_id)) returning id into idToReturn;
  UPDATE carts SET checked_out = true where id=cart_id;
  RETURN idToReturn;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_guest_order(cart_id int, name text, email text)
RETURNS int AS '
DECLARE idToReturn int;
BEGIN
  INSERT INTO orders(cart_id, name, email) values (cart_id, name, email) returning id into idToReturn;
  UPDATE carts SET checked_out = true where id=cart_id;
  RETURN idToReturn;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_all_user()
RETURNS SETOF users AS '
BEGIN
  RETURN QUERY SELECT * FROM users;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_all_orders()
RETURNS SETOF orders AS '
BEGIN
  RETURN QUERY SELECT * FROM orders;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_all_products()
RETURNS SETOF products AS '
BEGIN
  RETURN QUERY SELECT * FROM products;
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
  RETURN QUERY SELECT * FROM carts WHERE id=(SELECT max(id) from carts where user_id=idToFind) and user_id=idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_items_by_cart_id(idToFind int)
RETURNS TABLE (id int, quantity numeric, unit text, price numeric, name text, picture text) AS '
BEGIN
  RETURN QUERY select cart_items.id, cart_items.quantity, products.unit,  cart_items.price, products.name, products.picture from cart_items left join products on cart_items.product_id=products.id where cart_items.cart_id = idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_user_by_id(idToFind int)
RETURNS SETOF users AS '
BEGIN
  RETURN QUERY SELECT * FROM users WHERE id=idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_order_by_id(idToFind int)
RETURNS SETOF orders AS '
BEGIN
  RETURN QUERY SELECT * FROM orders WHERE id=idToFind;
END;
' LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION find_cart_item_by_id(idToFind int)
RETURNS TABLE (id int, quantity numeric, unit text, price numeric, name text, picture text) AS '
BEGIN
  RETURN QUERY select cart_items.id, cart_items.quantity, products.unit,  cart_items.price, products.name, products.picture from cart_items left join products on cart_items.id=products.id where cart_items.cart_id = idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_cart_by_id(idToFind int)
RETURNS SETOF carts AS '
BEGIN
  RETURN QUERY SELECT * FROM carts WHERE id=idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION find_product_by_id(idToFind int)
RETURNS SETOF products AS '
BEGIN
  RETURN QUERY SELECT * FROM products WHERE id=idToFind;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION add_cart_item(cart_id int, product_id int, quantity numeric)
RETURNS int AS '
DECLARE idToReturn int;
BEGIN
  INSERT INTO cart_items(cart_id, product_id, quantity, price) VALUES (cart_id, product_id, quantity, quantity * (select price from products where id = product_id)) returning id into idToReturn;
  RETURN idToReturn;
END;
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION remove_cart_item(idToRemove int)
RETURNS void AS '
BEGIN
    DELETE from cart_items where id=idToRemove;
END;
' LANGUAGE plpgsql;

select add_user('Csontos Julia', 'csontos.julia@freemail.hu', 'ADMIN', '88888888');
select add_user('Kovacs Zoltan', 'zolee95@gmail.com', 'ADMIN', '88888888');
select add_user('Marika', 'marika@gmail.com', 'USER', '88888888');

select add_product('tomato', true, 'kg', 'images/tomato.jpg', 800);
select add_product('potato', true, 'kg', 'images/potato.png', 130);
select add_product('apple', true, 'kg', 'images/apple.jpeg', 350);
select add_product('strawberry', true, 'kg', 'images/strawberry.jpg', 1000);
select add_product('jalapeno', true, 'each', 'images/jalapeno.jpg', 120);
select add_product('banana', true, 'kg', 'images/banana.jpeg', 470);

select add_cart(3);

select add_cart_item(1, 1, 4);
select add_cart_item(1, 2, 3);
select add_cart_item(1, 3, 2);