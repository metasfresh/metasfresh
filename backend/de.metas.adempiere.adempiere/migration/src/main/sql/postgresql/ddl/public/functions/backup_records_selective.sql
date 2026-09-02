-- Purpose: Creates a selective backup of specific records from a table.
--          Only backs up records that will be modified, identified by their IDs.
--
-- Parameters:
--   p_schema_name   - Schema of the source table (e.g., 'public')
--   p_table_name    - Name of the source table (e.g., 'm_pricelist_version')
--   p_id_column     - Name of the primary key column (e.g., 'm_pricelist_version_id')
--   p_record_ids    - Array of record IDs to backup
--
-- Returns: Name of the created backup table (format: backup.{tablename}_bkp_YYYYMMDD_HHMMSS)

CREATE OR REPLACE FUNCTION backup_records_selective(
    p_schema_name TEXT,
    p_table_name  TEXT,
    p_id_column   TEXT,
    p_record_ids  BIGINT[]
)
    RETURNS TEXT
    LANGUAGE plpgsql
AS
$func$
DECLARE
    v_backup_table_name TEXT;
    v_timestamp         TEXT;
    v_sql               TEXT;
    v_record_count      INTEGER;
BEGIN
    -- Generate timestamp for backup table name
    v_timestamp := TO_CHAR(NOW(), 'YYYYMMDD_HH24MISS');
    v_backup_table_name := 'backup.' || p_table_name || '_bkp_' || v_timestamp;

    -- Check if there are records to backup
    IF p_record_ids IS NULL OR ARRAY_LENGTH(p_record_ids, 1) IS NULL THEN
        RAISE NOTICE 'No records to backup for table %', p_table_name;
        RETURN NULL;
    END IF;

    -- Create backup table with selected records
    v_sql := FORMAT(
            'CREATE TABLE %s AS SELECT * FROM %I.%I WHERE %I = ANY($1)',
            v_backup_table_name,
            p_schema_name,
            p_table_name,
            p_id_column
             );

    EXECUTE v_sql USING p_record_ids;

    -- Get count of backed up records
    EXECUTE FORMAT('SELECT COUNT(*) FROM %s', v_backup_table_name) INTO v_record_count;

    RAISE NOTICE 'Backup created: % (% records)', v_backup_table_name, v_record_count;

    RETURN v_backup_table_name;
END;
$func$
;

COMMENT ON FUNCTION backup_records_selective(TEXT, TEXT, TEXT, BIGINT[]) IS
    'Creates a selective backup of specific records from a table.
    Only the records matching the provided IDs are copied to a new backup table.
    Backup table naming format: backup.{tablename}_bkp_YYYYMMDD_HHMMSS'
;
