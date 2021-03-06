
	CREATE ROLE kchamorro NOSUPERUSER CREATEDB NOCREATEROLE NOINHERIT LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'kchamorro';

	CREATE DATABASE db_kruger_challenge OWNER kchamorro;

    create table contacts (
       id  bigserial not null,
        type varchar(255),
        value varchar(255),
        person_entity_id int8,
        primary key (id)
    );

    create table employees (
       salary float8,
        status varchar(255),
        workstation varchar(255),
        id int8 not null,
        user_id int8,
        primary key (id)
    );

    create table persons (
       person_type varchar(31) not null,
        id  bigserial not null,
        second_surname varchar(255),
        date_birth date,
        first_name varchar(255),
        first_surname varchar(255),
        home_address varchar(255),
        identification varchar(255),
        identification_type varchar(255),
        middle_name varchar(255),
        vaccine_status varchar(255),
        primary key (id)
    );

    create table persons_contacts (
       persons_id int8 not null,
        contacts_id int8 not null
    );

    create table persons_vaccine_entities (
       persons_id int8 not null,
        vaccine_entities_id int8 not null
    );

    create table roles (
       id  bigserial not null,
        name varchar(255),
        primary key (id)
    );

    create table users (
       id  bigserial not null,
        password varchar(255),
        username varchar(255),
        primary key (id)
    );

    create table users_roles (
       users_id int8 not null,
        roles_id int8 not null
    );

    create table vaccine_types (
       id  bigserial not null,
        name varchar(255),
        primary key (id)
    );

    create table vaccines (
       id  bigserial not null,
        date date,
        number_doses int4 not null,
        type_id int8,
        primary key (id)
    );

    alter table persons_contacts 
       add constraint UK_60vt71xg2f9kfj2endvelg4p7 unique (contacts_id);

    alter table contacts 
       add constraint FK6nub6aw6lxxpjiwh4wcr1emgq 
       foreign key (person_entity_id) 
       references persons;

    alter table employees 
       add constraint FK69x3vjuy1t5p18a5llb8h2fjx 
       foreign key (user_id) 
       references users;

    alter table employees 
       add constraint FKsfxfst8b5qgcjmw6nlktd94jh 
       foreign key (id) 
       references persons;

    alter table persons_contacts 
       add constraint FK1s8dyusmh6ojy9r8353i5fp1c 
       foreign key (contacts_id) 
       references contacts;

    alter table persons_contacts 
       add constraint FK7u31hryng4jawi2eadd9no8gs 
       foreign key (persons_id) 
       references persons;

    alter table persons_vaccine_entities 
       add constraint FKgehpmte1rftjxlx4ug1rt7vu8 
       foreign key (vaccine_entities_id) 
       references vaccines;

    alter table persons_vaccine_entities 
       add constraint FKca9poqddtyfq6jxlxowfvvwl 
       foreign key (persons_id) 
       references persons;

    alter table users_roles 
       add constraint FKa62j07k5mhgifpp955h37ponj 
       foreign key (roles_id) 
       references roles;

    alter table users_roles 
       add constraint FKml90kef4w2jy7oxyqv742tsfc 
       foreign key (users_id) 
       references users;

    alter table vaccines 
       add constraint FKjqnposhodprbvvvu63wf42vyq 
       foreign key (type_id) 
       references vaccine_types;
	