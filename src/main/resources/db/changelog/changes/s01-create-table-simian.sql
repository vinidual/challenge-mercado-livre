create table dna (
    id uuid,
    dna_string text unique not null,
    dna_type varchar(16) not null,
    creation_date timestamp not null,
    update_date timestamp not null
)