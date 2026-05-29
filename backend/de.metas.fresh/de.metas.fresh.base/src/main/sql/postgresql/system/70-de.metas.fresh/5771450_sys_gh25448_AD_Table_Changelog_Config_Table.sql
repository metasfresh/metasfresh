CREATE TABLE fix.AD_Table_Changelog_Config_26092025
(
    ad_table_id           numeric(10)
        CONSTRAINT adtable_adchangelogconfig
            REFERENCES ad_table
            DEFERRABLE INITIALLY DEFERRED
)
;
