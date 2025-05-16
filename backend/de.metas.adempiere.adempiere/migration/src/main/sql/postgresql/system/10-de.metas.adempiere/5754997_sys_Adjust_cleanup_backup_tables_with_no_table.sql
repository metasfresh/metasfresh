DROP FUNCTION IF EXISTS cleanup_backup_tables(p_SourceTableName  text,
                                              p_DaysToKeepBackup integer)
;


CREATE OR REPLACE FUNCTION cleanup_backup_tables(p_SourceTableName  text = NULL,
                                                 p_DaysToKeepBackup integer)
    RETURNS text
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
          AND (p_SourceTableName IS NULL OR lower(source_table_name) = lower(p_SourceTableName))
        LOOP
            RAISE NOTICE 'Dropping table %', r.backup_table_name;
            EXECUTE 'DROP TABLE IF EXISTS ' || r.backup_table_name;

            DELETE FROM backup.backup_tables WHERE backup_table_name = r.backup_table_name;
            GET DIAGNOSTICS v_deleted = ROW_COUNT;
            v_totalDeleted := v_totalDeleted + v_deleted;
        END LOOP;

    RAISE NOTICE 'Total backup table entries removed: %', v_totalDeleted;

    RETURN format('Cleaned up %s: %s tables deleted.', p_SourceTableName, v_totalDeleted);
END
$$
;

-- Usage example:
-- SELECT cleanup_backup_tables('M_Product', 0);
-- => select * from backup.backup_tables where source_table_name='M_Product'