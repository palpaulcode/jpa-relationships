insert into employee (id, birthdate, email, firstname, identifier, lastname, role)
values (nextval('hibernate_sequence'), current_date, 'paul@gmail.com', 'paul', '12345', 'ryan', 'ROLE_ADMIN');