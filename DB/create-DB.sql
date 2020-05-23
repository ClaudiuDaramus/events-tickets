CREATE TABLE event_location(
    id number(10) NOT NULL,
    address varchar2(50) NOT NUll,
    name varchar2(50) NOT NUll,
    country varchar2(50) NOT NUll,
    city varchar2(50) NOT NUll,

    CONSTRAINT event_location_pk PRIMARY KEY (id)
);

INSERT INTO event_location
VALUES (1, 'somewhere', 'Arena Nationala', 'Romania' , 'Bucuresti');
INSERT INTO event_location
VALUES (2, 'nowhere', 'Romexpo', 'Romania' , 'Bucuresti');


CREATE TABLE event_type(
    id number(10) NOT NULL,
    name varchar2(50) NOT NUll,

    CONSTRAINT event_type_pk PRIMARY KEY (id)
);

INSERT INTO event_type
VALUES (1, 'Art Exposition');
INSERT INTO event_type
VALUES (0, 'Concert');


CREATE TABLE users(
    id number(10) NOT NULL,
    username varchar2(50) NOT NULL,
    firstName varchar2(50) NOT NULL,
    lastName varchar2(50) NOT NULL,
    emailAddress varchar2(50) NOT NULL,
    dateCreated DATE NOT NULL,
    phoneNumber number(10) NOT NULL,

    CONSTRAINT users_pk PRIMARY KEY (id)
);

INSERT INTO users
VALUES (1, 'jackJones', 'Jack', 'Jones', 'jack@jones.com',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 33342);
INSERT INTO users
VALUES (2, 'mikeApple', 'Mike', 'Apple', 'mike@apple.com',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 83341);
INSERT INTO users
VALUES (3, 'tomGarron', 'Tom', 'Garron', 'tom@garron.com',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 93222);
INSERT INTO users
VALUES (4, 'juliaTomato', 'Julia', 'Tomato', 'julia@tomato.com',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'), 77342);


CREATE TABLE event(
    id number(10) NOT NULL,
    event_location_id number(10) NOT NULL,
    name varchar2(50) NOT NULL,
    eventDate date NOT NULL,

    CONSTRAINT event_pk PRIMARY KEY (id),
    CONSTRAINT fk_event_event_location
        FOREIGN KEY (event_location_id)
        REFERENCES event_location(id)
);

INSERT INTO event
VALUES (1, 1, 'Burning Skies Tour',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO event
VALUES (2, 2, 'Blue Wave Exp',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));


CREATE TABLE ticket(
    id number(10) NOT NULL,
    type number(10) NOT NULL,
    event_id number(10) NOT NULL,
    price float (10) NOT NULL,
    event_bonus varchar2(200),

    CONSTRAINT ticket_pk PRIMARY KEY (id),
    CONSTRAINT fk_ticket_event
        FOREIGN KEY (event_id)
        REFERENCES event(id)
);

INSERT INTO ticket
(id, type, event_id, price)
VALUES (1, 0, 1, 49.99);
INSERT INTO ticket
(id, type, event_id, price)
VALUES (2, 0, 2, 99.99);
INSERT INTO ticket
VALUES (3, 1, 1, 150.59, 'BACKSTAGEACCESS, EXTRAMERCH, REPETITIONSACCESS');
INSERT INTO ticket
VALUES (4, 1, 2, 299.45, 'FREEDRINKS, FREEFOOD');


CREATE TABLE users_ticket(
    id number(10) NOT NULL,
    users_id number(10) NOT NULL,
    ticket_id number(10) NOT NULL,

    CONSTRAINT users_ticket_pk PRIMARY KEY (id),
    CONSTRAINT fk_users_ticket_users
        FOREIGN KEY (users_id)
        REFERENCES users(id),
    CONSTRAINT fk_users_ticket_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES ticket(id)
);

INSERT INTO users_ticket
VALUES (1, 1, 1);
INSERT INTO users_ticket
VALUES (2, 1, 2);
INSERT INTO users_ticket
VALUES (3, 2, 2);
INSERT INTO users_ticket
VALUES (4, 3, 3);
INSERT INTO users_ticket
VALUES (5, 4, 4);


CREATE TABLE changes_audit(
    id number(10) NOT NULL,
    users_id number(10) NOT NULL,
    table_name varchar2(50) NOT NULL,
    change_date date NOT NULL,

    CONSTRAINT audit_pk PRIMARY KEY (id),
    CONSTRAINT fk_audit_users
        FOREIGN KEY (users_id)
        REFERENCES users(id)
);

INSERT INTO changes_audit
VALUES (1, 1, 'users', TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));