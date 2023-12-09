
    create table tb_role (
        id bigserial not null,
        authority varchar(255),
        primary key (id)
    );

    create table tb_task (
        deadline date,
        id bigserial not null,
        user_activity bigint,
        description varchar(255),
        status varchar(255) check (status in ('TODO','DOING','DONE')),
        primary key (id)
    );

    create table tb_user (
        id bigserial not null,
        email varchar(255) unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table tb_user_role (
        role_id bigint not null,
        user_id bigint not null,
        primary key (role_id, user_id)
    );

    alter table if exists tb_task
       add constraint FKud681s9veswfpqtr1arq0o6b
       foreign key (user_activity)
       references tb_user;

    alter table if exists tb_user_role
       add constraint FKea2ootw6b6bb0xt3ptl28bymv
       foreign key (role_id)
       references tb_role;

    alter table if exists tb_user_role
       add constraint FK7vn3h53d0tqdimm8cp45gc0kl
       foreign key (user_id)
       references tb_user;

INSERT INTO tb_role (authority) VALUES ('ADMIN');
INSERT INTO tb_role (authority) VALUES ('USER');

INSERT INTO tb_user (name, email, password) VALUES ('Danilo Ewerton', 'ewerton@email.com', '$2a$10$3OWaQs1gME4tH3ZjvhmY2.vCtjKriRzo0NS0rVLt5atvNZM7Df4qu');
INSERT INTO tb_user (name, email, password) VALUES ('Mariazinha', 'mariazinha@email.com', '$2a$10$PAKgGEtuE0WUqzIPGn2Kl.X.A2a8T3YYXhihdqkZCAaR5Mt3KRmNy');

INSERT INTO tb_task (description, deadline, status, user_activity) VALUES ('Ride bike', '2022-10-25', 'TODO', 1);
INSERT INTO tb_task (description, deadline, status, user_activity) VALUES ('Go to the gym', '2022-10-28', 'TODO', 1);
INSERT INTO tb_task (description, deadline, status, user_activity) VALUES ('Read a book', '2022-10-20', 'TODO', 1);
INSERT INTO tb_task (description, deadline, status, user_activity) VALUES ('Go to parent''s house', '2022-11-05', 'TODO', 2);
INSERT INTO tb_task (description, deadline, status, user_activity) VALUES ('Study at least 1 hour', '2022-11-01', 'TODO', 2);
INSERT INTO tb_task (description, deadline, status, user_activity) VALUES ('Walk the dog', '2022-11-02', 'TODO', 2);

INSERT INTO tb_user_role (role_id, user_id) VALUES (2, 1);
INSERT INTO tb_user_role (role_id, user_id) VALUES (2, 2);
INSERT INTO tb_user_role (role_id, user_id) VALUES (1, 1);