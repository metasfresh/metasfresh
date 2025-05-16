CREATE TABLE IF NOT EXISTS backup.backup_tables
(
    backup_table_name text PRIMARY KEY,
    source_table_name text        NOT NULL,
    created_at        timestamptz NOT NULL DEFAULT NOW(),
    row_count         integer
)
;


DROP FUNCTION IF EXISTS backup_table(p_TableName text,
                                     p_suffix    text)
;

CREATE OR REPLACE FUNCTION backup_table(p_TableName text,
                                        p_suffix    text = '')
    RETURNS text
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_backupTableName text;
    v_rowcount        integer;
BEGIN
    v_backupTableName := 'backup.' || p_TableName || '_bkp_' || TO_CHAR(NOW(), 'YYYYMMDD_HH24MISS_MS') || p_suffix;
    RAISE NOTICE 'Backup `%` to `%`...', p_TableName, v_backupTableName;

    EXECUTE 'CREATE TABLE ' || v_backupTableName || ' AS SELECT * FROM ' || p_TableName;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;

    INSERT INTO backup.backup_tables (backup_table_name, source_table_name, created_at, row_count)
    VALUES (v_backupTableName, p_TableName, NOW(), v_rowcount);

    RAISE NOTICE 'Backup done. % rows copied.', v_rowcount;

    RETURN v_backupTableName;
END
$$
;

DROP FUNCTION IF EXISTS cleanup_backup_tables(p_SourceTableName  text,
                                              p_DaysToKeepBackup integer)
;


CREATE OR REPLACE FUNCTION cleanup_backup_tables(p_SourceTableName  text,
                                                 p_DaysToKeepBackup integer)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    r              record;
    v_totalDeleted INTEGER := 0;
    v_deleted      INTEGER;
BEGIN
    FOR r IN
        SELECT backup_table_name
        FROM backup.backup_tables
        WHERE created_at <= NOW() - (p_DaysToKeepBackup || ' days')::interval
          AND source_table_name = p_SourceTableName
        LOOP
            RAISE NOTICE 'Dropping table %', r.backup_table_name;
            EXECUTE 'DROP TABLE IF EXISTS ' || r.backup_table_name;

            DELETE FROM backup.backup_tables WHERE backup_table_name = r.backup_table_name;
            GET DIAGNOSTICS v_deleted = ROW_COUNT;
            v_totalDeleted := v_totalDeleted + v_deleted;
        END LOOP;

    RAISE NOTICE 'Total backup table entries removed: %', v_totalDeleted;
END
$$
;
