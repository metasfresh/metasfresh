SELECT db_drop_functions('backup_table')
;

CREATE OR REPLACE FUNCTION backup_table(p_TableName       text,
                                        p_suffix          text = '',
                                        p_BackupTableName text = NULL)
    RETURNS text
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_backupTableName   text;
    v_backupTableNameFQ text;
    v_rowcount          integer;
BEGIN
    IF NULLIF(TRIM(p_BackupTableName), '') IS NOT NULL THEN
        v_backupTableName := TRIM(p_BackupTableName);
    ELSE
        v_backupTableName := p_TableName || '_bkp_' || TO_CHAR(NOW(), 'YYYYMMDD_HH24MISS_MS') || p_suffix;
    END IF;

    v_backupTableNameFQ := 'backup.' || v_backupTableName;

    RAISE NOTICE 'Backup `%` to `%`...', p_TableName, v_backupTableNameFQ;

    EXECUTE 'CREATE TABLE ' || v_backupTableNameFQ || ' AS SELECT * FROM ' || p_TableName;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;

    CREATE TABLE IF NOT EXISTS backup.backup_tables

    (
        backup_table_name text PRIMARY KEY,
        source_table_name text        NOT NULL,
        created_at        timestamptz NOT NULL DEFAULT NOW(),
        row_count         integer
    );

    INSERT INTO backup.backup_tables (backup_table_name, source_table_name, created_at, row_count)
    VALUES (v_backupTableNameFQ, p_TableName, NOW(), v_rowcount);

    RAISE NOTICE 'Backup done. % rows copied.', v_rowcount;

    RETURN v_backupTableNameFQ;
END
$$
;


-- Usage example:
-- SELECT backup_table('C_BP_Group');
-- => select * from backup.C_BP_Group_bkp_20220303_084927_890
-- => select * from backup.backup_tables where source_table_name='C_BP_Group'
