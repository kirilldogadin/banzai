create table entry
(
    id            bigint not null
        constraint entry_pkey
            primary key,
    content       varchar(255),
    creation_date varchar(255)
);