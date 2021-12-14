create table migration_data."Nahrstoffe"
(
    id           integer,
    "Name"       text,
    "Kategorie"  text,
    "Einheit"    text,
    "Präzision"  integer,
    rm           integer,
    nrv          text,
    "Parent ID"  integer,
    "Position"   numeric,
    "Zusatzinfo" text,
    "Formel"     text
);

alter table migration_data."Nahrstoffe"
    owner to metasfresh;