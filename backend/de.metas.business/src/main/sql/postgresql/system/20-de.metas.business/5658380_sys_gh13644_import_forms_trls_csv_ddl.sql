
CREATE TABLE migration_data."AD_Form_trad"
(
    ad_form_id       numeric NOT NULL
        UNIQUE,
    name_de          text,
    name_trad        text,
    name_trl         text,
    description_de   text,
    description_trad text,
    description_trl  text,
    help_de          text,
    help_trad        text,
    help_trl         text
)
;

ALTER TABLE migration_data."AD_Form_trad"
    OWNER TO metasfresh
;

