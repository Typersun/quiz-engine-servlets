create table question
(
    id      serial not null
        constraint question_pk
            primary key,
    text    varchar(200),
    quiz_id integer
        constraint question_quiz_id_fk
            references quiz
);

alter table question
    owner to postgres;

INSERT INTO public.question (id, text, quiz_id) VALUES (1, 'What''s better? Windows or MacOs?', 1);
INSERT INTO public.question (id, text, quiz_id) VALUES (2, 'Which university is best?', 1);
INSERT INTO public.question (id, text, quiz_id) VALUES (3, 'In what year serfdom was abolished in Russia?', 1);
INSERT INTO public.question (id, text, quiz_id) VALUES (4, 'Who invented Java?', 2);
INSERT INTO public.question (id, text, quiz_id) VALUES (5, 'What is the dominant paradigm in Java?', 2);
INSERT INTO public.question (id, text, quiz_id) VALUES (6, 'AAAAAAAAA', 2);