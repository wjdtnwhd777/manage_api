drop table if exists customer CASCADE; 
drop sequence if exists customer_seq;
create sequence customer_seq start with 1 increment by 1;

create table customer (
    uid bigint not null,
    email varchar(100) not null,
    passwd varchar(255) not null,
    reg_date TIMESTAMP,
    primary key (uid)
);

alter table customer add constraint UK_EMAIL_CUSTOMER unique (email);

drop table if exists goods CASCADE;
drop sequence if exists goods_seq;
create sequence goods_seq start with 1 increment by 1;

create table goods (
    uid bigint not null,
    goods_name varchar(100) not null,
    goods_price integer,
    reg_date TIMESTAMP,
    primary key (uid)
);

drop table if exists orders CASCADE;
drop sequence if exists orders_seq;
create sequence orders_seq start with 1 increment by 1;

create table orders (
    uid bigint not null,
    reg_date TIMESTAMP,
    customer_uid bigint,
    goods_uid bigint,
    primary key (uid)
);
alter table orders add constraint FK_CUSTOMER_UID foreign key (customer_uid) references customer;
alter table orders add constraint FK_GOODS_UID foreign key (goods_uid) references goods;