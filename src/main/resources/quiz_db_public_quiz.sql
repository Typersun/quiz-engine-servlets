create table quiz
(
    id      serial not null
        constraint quiz_pk
            primary key,
    name    varchar(50),
    user_id integer
        constraint quiz_user_id_fk
            references "user"
);

alter table quiz
    owner to postgres;

INSERT INTO public.quiz (id, name, user_id) VALUES (1, 'Super krutoi test', 2);
INSERT INTO public.quiz (id, name, user_id) VALUES (2, 'Questions about my beloved Java', 2);