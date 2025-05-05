
CREATE TABLE migration_data.ad_element_trl_220907_2
(
    ad_element_id                numeric,
    name_de                      text,
    name_trad                    text,
    name_trl                     text,
    description_de               text,
    description_trad             text,
    description_trl              text,
    po_name_de                   text,
    po_name_trad                 text,
    po_name_trl                  text,
    po_printname_de              text,
    po_printname_trad            text,
    po_printname_trl             text,
    po_description_de            text,
    po_description_trad          text,
    po_description_trl           text,
    po_help_de                   text,
    po_help_trad                 text,
    po_help_trl                  text,
    webui_namebrowse_de          text,
    webui_namebrowse_trad        text,
    webui_namebrowse_trl         text,
    webui_namenew_de             text,
    webui_namenew_trad           text,
    webui_namenew_trl            text,
    webui_namenewbreadcrumb_de   text,
    webui_namenewbreadcrumb_trad text,
    webui_namenewbreadcrumb_trl  text
)
;

ALTER TABLE  migration_data.ad_element_trl_220907_2
    OWNER TO metasfresh
;

DROP FUNCTION IF EXISTS backup_table(p_TableName text)
;

CREATE OR REPLACE FUNCTION backup_table(p_TableName text) RETURNS text
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_backupTableName text;
    v_rowcount        numeric;
BEGIN
    v_backupTableName = 'backup.' || p_TableName || '_bkp_' || TO_CHAR(NOW(), 'YYYYMMDD_HH24MISS_MS');
    RAISE NOTICE 'Backup `%` to `%`...', p_TableName, v_backupTableName;

    EXECUTE 'CREATE TABLE ' || v_backupTableName || ' AS SELECT * FROM ' || p_TableName;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Backup done. % rows copied.', v_rowcount;

    RETURN v_backupTableName;
END
$$
;

ALTER FUNCTION backup_table(text) OWNER TO metasfresh
;

