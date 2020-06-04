CREATE TABLE event_locations(
    id number(10) NOT NULL,
    address varchar2(50) NOT NUll,
    name varchar2(50) NOT NUll,
    country varchar2(50) NOT NUll,
    city varchar2(50) NOT NUll,

    CONSTRAINT event_locations_pk PRIMARY KEY (id)
);

INSERT INTO event_locations
VALUES (1, 'somewhere', 'Arena Nationala', 'Romania' , 'Bucuresti');
INSERT INTO event_locations
VALUES (2, 'nowhere', 'Romexpo', 'Romania' , 'Bucuresti');


CREATE TABLE event_types(
    id number(10) NOT NULL,
    name varchar2(50) NOT NUll,

    CONSTRAINT event_types_pk PRIMARY KEY (id)
);

INSERT INTO event_types
VALUES (0, 'Concert');
INSERT INTO event_types
VALUES (1, 'Art Exposition');


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


CREATE TABLE events(
    id number(10) NOT NULL,
    event_type_id number(10) NOT NULL,
    events_location_id number(10) NOT NULL,
    name varchar2(50) NOT NULL,
    eventDate date NOT NULL,

    CONSTRAINT events_pk PRIMARY KEY (id),
    CONSTRAINT fk_event_type_id
        FOREIGN KEY (event_type_id)
        REFERENCES event_types(id),
    CONSTRAINT fk_event_event_location
        FOREIGN KEY (events_location_id)
        REFERENCES event_locations(id)
);

INSERT INTO events
VALUES (1, 0, 1, 'Burning Skies Tour',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO events
VALUES (2, 1, 2, 'Blue Wave Exp',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));


CREATE TABLE bonuses(
    id number(10) NOT NULL,
    name varchar2(200),

    CONSTRAINT bonuses_pk PRIMARY KEY (id)
);

INSERT INTO bonuses
VALUES (1, 'BACKSTAGEACCESS');

INSERT INTO bonuses
VALUES (2, 'EXTRAMERCH');

INSERT INTO bonuses
VALUES (3, 'REPETITIONSACCESS');

INSERT INTO bonuses
VALUES (4, 'FREEDRINKS');

INSERT INTO bonuses
VALUES (5, 'FREEFOOD');

CREATE TABLE tickets(
    id number(10) NOT NULL,
    type number(10) NOT NULL,
    event_id number(10) NOT NULL,
    price float (10) NOT NULL,

    CONSTRAINT tickets_pk PRIMARY KEY (id),
    CONSTRAINT fk_tickets_events
        FOREIGN KEY (event_id)
        REFERENCES events(id)
);

INSERT INTO tickets
VALUES (1, 0, 1, 49.99);
INSERT INTO tickets
VALUES (2, 0, 2, 99.99);
INSERT INTO tickets
VALUES (3, 1, 1, 150.59);
INSERT INTO tickets
VALUES (4, 1, 2, 299.45);


CREATE TABLE tickets_bonuses(
    ticket_id number(10) NOT NULL,
    bonus_id number(10) NOT NULL,

    CONSTRAINT tickets_bonuses_pk PRIMARY KEY (ticket_id, bonus_id),
    CONSTRAINT fk_tickets_bonuses_tickets
        FOREIGN KEY (ticket_id)
        REFERENCES tickets(id),
    CONSTRAINT fk_tickets_bonuses_bonuses
        FOREIGN KEY (bonus_id)
        REFERENCES bonuses(id)
);

INSERT INTO tickets_bonuses
VALUES (1, 1);
INSERT INTO tickets_bonuses
VALUES (1, 2);
INSERT INTO tickets_bonuses
VALUES (2, 3);
INSERT INTO tickets_bonuses
VALUES (2, 4);
INSERT INTO tickets_bonuses
VALUES (2, 5);


CREATE TABLE users_tickets(
    users_id number(10) NOT NULL,
    ticket_id number(10) NOT NULL,

    CONSTRAINT users_tickets_pk PRIMARY KEY (users_id, ticket_id),
    CONSTRAINT fk_users_tickets_users
        FOREIGN KEY (users_id)
        REFERENCES users(id),
    CONSTRAINT fk_users_tickets_tickets
        FOREIGN KEY (ticket_id)
        REFERENCES tickets(id)
);

INSERT INTO users_tickets
VALUES (1, 1);
INSERT INTO users_tickets
VALUES (1, 2);
INSERT INTO users_tickets
VALUES (2, 2);
INSERT INTO users_tickets
VALUES (3, 3);
INSERT INTO users_tickets
VALUES (4, 4);


CREATE TABLE changes_audit(
    id number(10) NOT NULL,
    users_id number(10) NOT NULL,
    table_name varchar2(50) NOT NULL,
    thread_name varchar2(50) NOT NULL,
    change_date date NOT NULL,

    CONSTRAINT audit_pk PRIMARY KEY (id),
    CONSTRAINT fk_audit_users
        FOREIGN KEY (users_id)
        REFERENCES users(id)
);

INSERT INTO changes_audit
VALUES (1, 1, 'users', ' ',TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'));

commit;