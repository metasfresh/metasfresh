
CREATE TABLE migration_data."AD_Process_trad"
(
    ad_process_id    numeric NOT NULL
        UNIQUE,
    value            text,
    name_de          text,
    name_trad        text,
    name_trl         text,
    description_de   text,
    description_trad text,
    description_trl  text
)
;

ALTER TABLE migration_data."AD_Process_trad"
    OWNER TO metasfresh
;


CREATE TABLE migration_data."AD_Process_para_trad"
(
    ad_process_para_id numeric NOT NULL
        UNIQUE,
    name_de            text,
    name_trad          text,
    name_trl           text,
    description_de     text,
    description_trad   text,
    description_trl    text,
    help_de            text,
    help_trad          text,
    help_trl           text
)
;

ALTER TABLE migration_data."AD_Process_para_trad"
    OWNER TO metasfresh
;
