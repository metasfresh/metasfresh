CREATE TABLE fix.AD_Table_Changelog_Config_26092025
(
    I_ChangeLog_Config_id numeric(10)              NOT NULL
        CONSTRAINT I_ChangeLog_Config_key
            PRIMARY KEY,
    ad_client_id          numeric(10)              NOT NULL,
    ad_org_id             numeric(10)              NOT NULL,
    ad_table_id           numeric(10)
        CONSTRAINT adtable_adchangelogconfig
            REFERENCES ad_table
            DEFERRABLE INITIALLY DEFERRED,
    created               timestamp with time zone NOT NULL,
    createdby             numeric(10)              NOT NULL,
    isactive              char                     NOT NULL
        CONSTRAINT ad_changelog_config_isactive_check
            CHECK (isactive = ANY (ARRAY ['Y'::bpchar, 'N'::bpchar])),
    keepchangelogsdays    numeric(10) DEFAULT 30   NOT NULL,
    updated               timestamp with time zone NOT NULL,
    updatedby             numeric(10)              NOT NULL
)
;
