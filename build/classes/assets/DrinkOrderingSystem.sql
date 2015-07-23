DROP DATABASE IF EXISTS DrinkOrdering;
create database DrinkOrdering;
use DrinkOrdering;

create table Employee (
    EmployeeId int unsigned Auto_increment primary key,
    fname varchar(30),
    lname varchar(30),
    user_password varchar(128),
    privilege varchar(1),
    loggedin bool
);

create table drink (
    DrinkId int unsigned Auto_increment primary key,
    Drinkname varchar(30),
    drinktype varchar(2),
    description varchar(250),
    Quanity int,
    price float
);

create table band (
    Rfid int primary key,
    description varchar(30),
    color varchar(7)
);

create table guest (
    trn varchar(10) primary key,
    fname varchar(30),
    lname varchar(30),
    inital varchar(2)
);

create table guest_band (
    trn varchar(10),
    Rfid int,
    checkin bool,
    dateCheckin date,
    dateCheckOut date,
    foreign key (trn)
        references guest (trn)
        ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key (Rfid)
        references band (Rfid)
        ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (trn , Rfid , dateCheckin , dateCheckOut)
);

create table Guest_Order (
    Guest_order_id int unsigned Auto_increment primary key,
    Guest_trn varchar(10),
    emp_id int unsigned,
    date_order date,
    served bool,
    foreign key (Guest_trn)
        references guest (trn)
        ON DELETE CASCADE ON UPDATE CASCADE,
    foreign key (emp_id)
        references Employee (EmployeeId)
        ON DELETE CASCADE ON UPDATE CASCADE
);

create table Guest_order_drink (
    Guest_order_id int unsigned,
    drink_Id int unsigned,
    drinkRating int,
    foreign key (drink_Id)
        references drink (DrinkId),
    foreign key (Guest_order_id)
        references Guest_order (Guest_order_id)
);

create unique index employeeId_index on Employee (EmployeeId);
create unique index DrinkId_index on drink (DrinkId);
create unique index guest_index on guest  (trn);
create unique index band_index on band  (rfid);
