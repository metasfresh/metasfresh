CREATE TABLE migration_data."AD_Ref_List_Trad"
(
    ad_ref_list_id   numeric NOT NULL
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

ALTER TABLE migration_data."AD_Ref_List_Trad"
    OWNER TO metasfresh
;

CREATE TABLE migration_data."AD_Reference_trad"
(
    ad_reference_id  numeric NOT NULL
        UNIQUE,
    name_de          text,
    name_trad        text,
    name_trl         text,
    description_de   text,
    description_trad text,
    description_trl  text
)
;

ALTER TABLE migration_data."AD_Reference_trad"
    OWNER TO metasfresh
;
