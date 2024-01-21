create table car(
	id bigserial primary key,
	mark text,
	model text,
	price money
)

create table person(
	id bigserial primary key,
	name text,
	age int,
	have_driver_license boolean,
	car_id int,
	foreign key (car_id) references car(id)
)