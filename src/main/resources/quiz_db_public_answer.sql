create table answer
(
    id                 serial not null
        constraint answer_pk
            primary key,
    question_id        integer
        constraint answer_question_id_fk
            references question
        constraint answer_question_option_id_fk
            references question_option,
    question_option_id integer,
    user_id            integer
        constraint answer_user_id_fk
            references "user"
);

alter table answer
    owner to postgres;

