create table if not exists public.address
(
    id           integer not null
        primary key,
    house_number varchar(255),
    street_name  varchar(255),
    zip_code     varchar(255)
);

alter table public.address
    owner to app_data;

create table if not exists public.department
(
    id   integer not null
        primary key,
    name varchar(255)
);

alter table public.department
    owner to app_data;

create table if not exists public.employee
(
    id            integer      not null
        primary key,
    birthdate     date         not null,
    email         varchar(255) not null
        constraint uk_fopic1oh5oln2khj8eat6ino0
            unique,
    firstname     varchar(255) not null,
    identifier    varchar(255) not null
        constraint uk_5rh0wqupnwpehb050wgokpc4n
            unique,
    lastname      varchar(255) not null,
    role          varchar(255) not null,
    address_id    integer
        constraint fkga73hdtpb67twlr9c1i337tyt
            references public.address,
    department_id integer
        constraint fkbejtwvg9bxus2mffsm3swj3u9
            references public.department
);

alter table public.employee
    owner to app_data;

create table if not exists public.mission
(
    id       integer not null
        primary key,
    duration integer not null,
    name     varchar(255)
);

alter table public.mission
    owner to app_data;

create table if not exists public.employee_mission
(
    employee_id integer not null
        constraint fkn6kayjl3tuml7x7x79tgd5uiq
            references public.employee,
    mission_id  integer not null
        constraint fkfgq6xw9slvmn8axdlck1asln7
            references public.mission
);

alter table public.employee_mission
    owner to app_data;

-- auto-generated definition
create sequence address_seq
    increment by 50;

alter sequence address_seq owner to app_data;

-- auto-generated definition
create sequence department_seq
    increment by 50;

alter sequence department_seq owner to app_data;

-- auto-generated definition
create sequence employee_seq
    increment by 50;

alter sequence employee_seq owner to app_data;

-- auto-generated definition
create sequence mission_seq
    increment by 50;

alter sequence mission_seq owner to app_data;