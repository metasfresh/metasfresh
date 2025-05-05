DROP FUNCTION IF EXISTS backup_table(p_TableName text)
;

CREATE OR REPLACE FUNCTION backup_table(p_TableName text)
    RETURNS text
    LANGUAGE plpgsql
    VOLATILE
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
