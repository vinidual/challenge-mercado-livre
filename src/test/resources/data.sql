drop table if exists dna;

drop table if exists stats_dna;

create table dna (
    id uuid primary key,
    dna_string varchar unique not null,
    dna_type varchar(16) not null,
    creation_date timestamp not null,
    update_date timestamp not null
);

create table stats_dna (
    id integer primary key,
    count_mutant_dna bigint not null,
    count_human_dna bigint not null,
    ratio numeric(5, 2) not null,
    creation_date timestamp not null,
    update_date timestamp not null
);

insert into stats_dna (id, count_mutant_dna, count_human_dna, ratio, creation_date, update_date)
    values (1, 0, 0, 0.00, current_timestamp, current_timestamp);