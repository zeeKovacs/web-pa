# About

GitHub repository for my PA.

TABLES:
	USERS:  id serial,
		name text not null,
		email text not null,
		role text,
		password varchar(8) not null

	PRODUCTS: id serial,
		  name text unique not null,
		  availability boolean,
		  unit text check (unit in('each', 'kg)),
		  price numeric check (price > 0)

	CARTS: id serial,
	       user_id int ref users(id) default null,
	       price numeric default 0,
	       checked_out boolean default false

	CART_ITEMS: cart_id int ref carts(id),
		    product id int ref products(id),
		    quantity numeric not null,
		    price numeric not null

	TRIGGERS: update cart price after each insert or delete on cart_items


As a guest I would like to be able to:
	browse products
	add/remove products from my cart
	checkout without having to register

As a user I would like to be able to:
	browse products
	check my previous orders
	add/remove products from my cart
	checkout an order without filling forms
	keep my cart's content across all devices
	get an email verification when my orders is ready to be picked up

As an admin I would like to be able to:
	see all orders
	add new products
	set a certain product's price
	set a certain product's availability
	notify the user via email when their order is ready to be picked up

LOGGING/TESTING