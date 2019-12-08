create table stats_dna (
    id integer primary key,
    count_mutant_dna bigint not null,
    count_human_dna bigint not null,
    ratio numeric(5, 2) not null,
    creation_date timestamp not null,
    update_date timestamp not null
);