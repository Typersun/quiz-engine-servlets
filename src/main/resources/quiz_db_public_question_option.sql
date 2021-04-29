create table question_option
(
    id          serial not null
        constraint questionoption_pk
            primary key,
    text        varchar(100),
    is_correct  boolean,
    question_id integer
        constraint questionoption_question_id_fk
            references question
);

alter table question_option
    owner to postgres;

