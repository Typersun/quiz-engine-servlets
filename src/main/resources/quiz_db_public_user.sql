create table "user"
(
    id       serial      not null
        constraint user_pk
            primary key,
    username varchar(40) not null,
    password varchar(20) not null
);

alter table "user"
    owner to postgres;

create unique index user_username_uindex
    on "user" (username);

INSERT INTO public."user" (id, username, password) VALUES (2, 'Kokutov', '123123');
INSERT INTO public."user" (id, username, password) VALUES (3, 'Mileri', '111');
INSERT INTO public."user" (id, username, password) VALUES (4, 'XXXLatkinXXX', 'otec');
INSERT INTO public."user" (id, username, password) VALUES (5, 'Kamil', 'LoveSwift');
INSERT INTO public."user" (id, username, password) VALUES (8, 'OlegRomanov', 'qwerty');