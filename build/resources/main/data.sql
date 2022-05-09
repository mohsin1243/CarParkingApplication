DELETE FROM admin WHERE id=1;
DELETE FROM person WHERE id=1;

insert into person (id, email, password, role) values (1, 'admin@carparking.com', '$2a$12$I5V9j31hm2.IRbXWE1V8Ne.ziXDCndD69RPhtCQomGA3mYWGL3ASi', 'ADMIN');
insert into admin(address, firstname, lastname, phonenumber, id) values ('Delhi, India', 'Markus', 'Maverick', '+91 9936123405', 1);
