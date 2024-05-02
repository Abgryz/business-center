create table if not exists client (
    id serial primary key,
    name varchar(255) not null,
    username varchar(32) unique not null,
    password varchar not null
);

create table if not exists employee (
    id serial primary key,
    name varchar(255) not null,
    username varchar(32) unique not null,
    password varchar not null,
    job_title varchar(255) not null,
    subdivision varchar(255)
);

create table if not exists office (
    id serial primary key,
    name varchar(255) not null,
    address varchar(255) not null,
    square numeric check (square > 0) not null,
    price numeric check (price > 0) not null,
    is_empty boolean default true
);

create table if not exists rent (
    id serial primary key,
    client_id int references client(id) not null,
    employee_id int references employee(id),
    office_id int references office(id) not null,
    start_date date default current_date,
    end_date date check (end_date > start_date) not null
);

create table if not exists service (
    id serial primary key,
    name varchar(255) not null,
    description text,
    price numeric check (price > 0) not null
);

create table if not exists service_order (
    id serial primary key,
    client_id int references client(id) not null,
    employee_id int references employee(id),
    service_id int references service(id) not null,
    date date default current_date
);