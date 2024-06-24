CREATE TABLE migration_data."AD_FieldGroup_trad"
(
    ad_fieldgroup_id numeric NOT NULL
        UNIQUE,
    name_de          text,
    name_trad        text,
    name_trl         text
)
;

ALTER TABLE migration_data."AD_FieldGroup_trad"
    OWNER TO metasfresh
;

CREATE TABLE migration_data.ad_index_table_trad
(
    ad_index_table_id        numeric NOT NULL
        UNIQUE,
    errormsg_de              text,
    errormsg_trad            text,
    errormsg_trl             text,
    description_de           text,
    description_trad         text,
    description_trl          text,
    beforechangewarning_de   text,
    beforechangewarning_trad text,
    beforechangewarning_trl  text
)
;

ALTER TABLE migration_data.ad_index_table_trad
    OWNER TO metasfresh
;

CREATE TABLE migration_data."AD_InfoColumn_trad"
(
    ad_infocolumn_id numeric NOT NULL
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

ALTER TABLE migration_data."AD_InfoColumn_trad"
    OWNER TO metasfresh
;


CREATE TABLE migration_data."AD_UI_Section_trad"
(
    ad_ui_section_id numeric NOT NULL
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

ALTER TABLE migration_data."AD_UI_Section_trad"
    OWNER TO metasfresh
;

CREATE TABLE migration_data.ad_workflow_trad
(
    ad_workflow_id   numeric NOT NULL
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

ALTER TABLE migration_data.ad_workflow_trad
    OWNER TO metasfresh
;


CREATE TABLE migration_data.c_country_trad
(
    c_country_id     numeric NOT NULL
        UNIQUE,
    name_de          text,
    name_trad        text,
    name_trl         text,
    description_de   text,
    description_trad text,
    description_trl  text,
    regionname_de    text,
    regionname_trad  text,
    regionname_trl   text
)
;

ALTER TABLE migration_data.c_country_trad
    OWNER TO metasfresh
;

CREATE TABLE migration_data.c_currency_trad
(
    c_currency_id    numeric NOT NULL
        UNIQUE,
    description_de   text,
    description_trad text,
    description_trl  text
)
;

ALTER TABLE migration_data.c_currency_trad
    OWNER TO metasfresh
;





CREATE TABLE migration_data."C_ElementValue_trad"
(
    c_elementvalue_id numeric,
    value             text,
    name_de           text,
    name_trad         text,
    name_trl          text,
    description_de    text,
    description_trad  text,
    description_trl   text
)
;

ALTER TABLE migration_data."C_ElementValue_trad"
    OWNER TO metasfresh
;


CREATE TABLE migration_data."C_Incoterms_trad"
(
    c_incoterms_id   numeric NOT NULL
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

ALTER TABLE migration_data."C_Incoterms_trad"
    OWNER TO metasfresh
;






CREATE TABLE migration_data.c_paymentterm_trad
(
    c_paymentterm_id  numeric NOT NULL
        UNIQUE,
    value             text,
    name_de           text,
    name_trad         text,
    name_trl          text,
    description_de    text,
    description_trad  text,
    description_trl   text,
    name_invoice_de   text,
    name_invoice_trad text,
    name_invoice_trl  text
)
;

ALTER TABLE migration_data.c_paymentterm_trad
    OWNER TO metasfresh
;


CREATE TABLE migration_data."C_Period_trad"
(
    c_period_id numeric NOT NULL
        UNIQUE,
    name_de     text,
    name_trad   text,
    name_trl    text
)
;

ALTER TABLE migration_data."C_Period_trad"
    OWNER TO metasfresh
;



CREATE TABLE migration_data."C_Tax_trad"
(
    c_tax_id          numeric NOT NULL
        UNIQUE,
    name_de           text,
    name_trad         text,
    name_trl          text,
    description_de    text,
    description_trad  text,
    description_trl   text,
    taxindicator_de   text,
    taxindicator_trad text,
    taxindicator_trl  text
)
;

ALTER TABLE migration_data."C_Tax_trad"
    OWNER TO metasfresh
;




CREATE TABLE migration_data.c_uom_trad
(
    c_uom_id         numeric NOT NULL
        UNIQUE,
    name_de          text,
    name_trad        text,
    name_trl         text,
    description_de   text,
    description_trad text,
    description_trl  text,
    uomsymbol_de     text,
    uomsymbol_trad   text,
    uomsymbol_trl    text
)
;

ALTER TABLE migration_data.c_uom_trad
    OWNER TO metasfresh
;



CREATE TABLE migration_data.r_requesttype_trad
(
    r_requesttype_id numeric NOT NULL
        UNIQUE,
    name_de          text,
    name_trad        text,
    name_trl         text,
    description_de   text,
    description_trad text,
    description_trl  text
)
;

ALTER TABLE migration_data.r_requesttype_trad
    OWNER TO metasfresh
;


CREATE TABLE migration_data."WEBUI_KPI_Field_trad"
(
    webui_kpi_field_id numeric NOT NULL
        UNIQUE,
    name_de            text,
    name_trad          text,
    name_trl           text,
    offsetname_de      text,
    offsetname_trad    text,
    offsetname_trl     text
)
;

ALTER TABLE migration_data."WEBUI_KPI_Field_trad"
    OWNER TO metasfresh
;


