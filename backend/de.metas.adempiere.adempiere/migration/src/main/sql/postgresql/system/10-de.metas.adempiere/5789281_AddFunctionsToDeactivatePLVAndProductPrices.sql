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


/*

Purpose: Deactivates old Price List Versions (PLVs) and their associated
         Product Prices based on the validfrom date and optional filters.

CRITICAL SAFETY FEATURE: Only deactivates PLVs when a newer version exists
         for the same Price List. This prevents deactivating the latest/only
         PLV for a price list, ensuring pricing data continuity.

IMPORTANT: The "Valid Days Back" parameter is INCLUSIVE.
           Example: p_valid_days_back = 30 means:
           "Deactivate PLVs where validfrom is 30 days ago OR OLDER"
           Formula: validfrom <= NOW() - INTERVAL 'X days'
Deactivation Criteria (ALL must be met):
  1. PLV is currently active (isactive = 'Y')
  2. PLV's validfrom date <= cutoff date (X days ago)
  3. A NEWER PLV exists for the same m_pricelist_id (with later validfrom)
  4. Any optional filters match (pricing system, pricelist, etc.)

Parameters:
  p_m_pricingsystem_id   - (Optional) Filter by Pricing System ID
  p_m_pricelist_id       - (Optional) Filter by Price List ID
  p_basepricelist_id     - (Optional) Filter by Base Price List ID of the Price List
  p_base_plv_pricelist_id- (Optional) Filter by Price List ID of the PLV's Base PLV
  p_issotrx              - (Optional) Filter by Sales Transaction flag:
                           'Y' = Sales, 'N' = Purchase, NULL = All
                           Note: Only applies if p_m_pricelist_id is not specified
  p_valid_days_back      - (Required) Number of days back (INCLUSIVE).
                           PLVs with validfrom <= (today - X days) will be deactivated.

    Returns: Summary message with counts and backup table names

*/
CREATE OR REPLACE FUNCTION deactivate_old_plv_productprices(
    p_m_pricingsystem_id    INTEGER DEFAULT NULL,
    p_m_pricelist_id        INTEGER DEFAULT NULL,
    p_basepricelist_id      INTEGER DEFAULT NULL,
    p_base_plv_pricelist_id INTEGER DEFAULT NULL,
    p_issotrx               CHAR(1) DEFAULT NULL,
    p_valid_days_back       INTEGER DEFAULT NULL
)
    RETURNS TEXT
    LANGUAGE plpgsql
AS
$func$
DECLARE
    v_cutoff_date        TIMESTAMP;
    v_where_clause       TEXT    := '';
    v_plv_ids            BIGINT[];
    v_productprice_ids   BIGINT[];
    v_plv_count          INTEGER := 0;
    v_productprice_count INTEGER := 0;
    v_plv_backup_table   TEXT;
    v_pp_backup_table    TEXT;
    v_sql                TEXT;
    v_summary            TEXT;
BEGIN
    IF p_issotrx IS NOT NULL AND p_issotrx NOT IN ('Y', 'N') THEN
        RAISE EXCEPTION 'Invalid p_issotrx: %. Must be Y or N', p_issotrx;
    END IF;

    IF p_valid_days_back IS NOT NULL AND p_valid_days_back < 0 THEN
        RAISE EXCEPTION 'Invalid p_valid_days_back: %. Must be > 0', p_valid_days_back;
    END IF;

    -- Calculate cutoff date (INCLUSIVE: X days ago or older)
    v_cutoff_date := NOW() - (p_valid_days_back || ' days')::INTERVAL;


    RAISE NOTICE '========================================================';
    RAISE NOTICE 'Starting PLV Deactivation Process';
    RAISE NOTICE '========================================================';
    RAISE NOTICE 'SAFETY: Only deactivates PLVs when a newer version exists for the same Price List';
    RAISE NOTICE 'Cutoff Date: % (PLVs with validfrom <= this date will be considered)', v_cutoff_date;
    RAISE NOTICE 'Parameters:';
    RAISE NOTICE '  - p_m_pricingsystem_id: %', COALESCE(p_m_pricingsystem_id::TEXT, 'ALL');
    RAISE NOTICE '  - p_m_pricelist_id: %', COALESCE(p_m_pricelist_id::TEXT, 'ALL');
    RAISE NOTICE '  - p_basepricelist_id: %', COALESCE(p_basepricelist_id::TEXT, 'ALL');
    RAISE NOTICE '  - p_base_plv_pricelist_id: %', COALESCE(p_base_plv_pricelist_id::TEXT, 'ALL');
    RAISE NOTICE '  - p_issotrx: %', COALESCE(p_issotrx, 'ALL');
    RAISE NOTICE '  - p_valid_days_back: % days (INCLUSIVE)', p_valid_days_back;
    RAISE NOTICE '--------------------------------------------------------';

    -- Base conditions: active records with validfrom <= cutoff date
    -- CRITICAL: Only include PLVs where a NEWER version exists for the same price list
    v_where_clause := 'plv.isactive = ''Y'' AND plv.validfrom <= $1' ||
                      ' AND EXISTS (SELECT 1 FROM m_pricelist_version newer_plv ' ||
                      '             WHERE newer_plv.m_pricelist_id = plv.m_pricelist_id ' ||
                      '             AND newer_plv.validfrom > plv.validfrom)';

    -- Add optional filters (AND logic)
    IF p_m_pricingsystem_id IS NOT NULL THEN
        v_where_clause := v_where_clause || ' AND pl.m_pricingsystem_id = ' || p_m_pricingsystem_id;
    END IF;

    IF p_m_pricelist_id IS NOT NULL THEN
        v_where_clause := v_where_clause || ' AND pl.m_pricelist_id = ' || p_m_pricelist_id;
    END IF;

    IF p_basepricelist_id IS NOT NULL THEN
        v_where_clause := v_where_clause || ' AND pl.basepricelist_id = ' || p_basepricelist_id;
    END IF;

    IF p_base_plv_pricelist_id IS NOT NULL THEN
        v_where_clause := v_where_clause ||
                          ' AND EXISTS (SELECT 1 FROM m_pricelist_version base_plv ' ||
                          '             JOIN m_pricelist base_pl ON base_plv.m_pricelist_id = base_pl.m_pricelist_id ' ||
                          '             WHERE base_plv.m_pricelist_version_id = plv.m_pricelist_version_base_id ' ||
                          '             AND base_pl.m_pricelist_id = ' || p_base_plv_pricelist_id || ')';
    END IF;

    -- IsSOTrx filter only applies if no specific price list is selected
    IF p_issotrx IS NOT NULL AND p_m_pricelist_id IS NULL THEN
        v_where_clause := v_where_clause || ' AND pl.issotrx = ''' || p_issotrx || '''';
    END IF;

    -- Find PLVs to deactivate
    v_sql := 'SELECT ARRAY_AGG(plv.m_pricelist_version_id) ' ||
             'FROM m_pricelist_version plv ' ||
             'JOIN m_pricelist pl ON plv.m_pricelist_id = pl.m_pricelist_id ' ||
             'WHERE ' || v_where_clause;

    EXECUTE v_sql INTO v_plv_ids USING v_cutoff_date;

    -- Check if any PLVs found
    IF v_plv_ids IS NULL OR ARRAY_LENGTH(v_plv_ids, 1) IS NULL THEN
        RAISE NOTICE 'No matching Price List Versions found to deactivate.';
        RETURN 'No records to deactivate. No changes made.';
    END IF;

    v_plv_count := ARRAY_LENGTH(v_plv_ids, 1);
    RAISE NOTICE 'Found % Price List Version(s) to deactivate (all have newer versions)', v_plv_count;
    RAISE NOTICE 'Price List Version(s) to deactivate are %', v_plv_ids;

    -- Find associated ProductPrices to deactivate
    SELECT ARRAY_AGG(pp.m_productprice_id)
    INTO v_productprice_ids
    FROM m_productprice pp
    WHERE pp.m_pricelist_version_id = ANY (v_plv_ids)
      AND pp.isactive = 'Y';

    IF v_productprice_ids IS NOT NULL THEN
        v_productprice_count := ARRAY_LENGTH(v_productprice_ids, 1);
    END IF;

    RAISE NOTICE 'Found % active Product Price(s) to deactivate', COALESCE(v_productprice_count, 0);

    -- Create backups BEFORE updating
    RAISE NOTICE '--------------------------------------------------------';
    RAISE NOTICE 'Creating backups...';

    -- Backup PLVs
    -- Ensure backup schema exists
    CREATE SCHEMA IF NOT EXISTS backup;

    v_plv_backup_table := backup_records_selective(
            'public',
            'm_pricelist_version',
            'm_pricelist_version_id',
            v_plv_ids
                          );

    -- Backup ProductPrices (if any)
    IF v_productprice_count > 0 THEN
        v_pp_backup_table := backup_records_selective(
                'public',
                'm_productprice',
                'm_productprice_id',
                v_productprice_ids
                             );
    END IF;

    -- Deactivate PLVs
    RAISE NOTICE '--------------------------------------------------------';
    RAISE NOTICE 'Deactivating Price List Versions...';

    UPDATE m_pricelist_version
    SET isactive = 'N',
        updated = NOW(),
        updatedby = 99
    WHERE m_pricelist_version_id = ANY (v_plv_ids);

-- Same for m_productprice UPDATE

    RAISE NOTICE 'Deactivated % Price List Version(s)', v_plv_count;

    -- Deactivate ProductPrices
    IF v_productprice_count > 0 THEN
        RAISE NOTICE 'Deactivating Product Prices...';

        UPDATE m_productprice
        SET isactive = 'N',
            updated  = NOW(),
            updatedby = 99
        WHERE m_productprice_id = ANY (v_productprice_ids);

        RAISE NOTICE 'Deactivated % Product Price(s)', v_productprice_count;
    END IF;

    -- Build summary
    RAISE NOTICE '========================================================';
    RAISE NOTICE 'SUMMARY';
    RAISE NOTICE '========================================================';
    RAISE NOTICE 'Price List Versions deactivated: %', v_plv_count;
    RAISE NOTICE 'Product Prices deactivated: %', COALESCE(v_productprice_count, 0);
    RAISE NOTICE 'PLV Backup Table: %', COALESCE(v_plv_backup_table, 'N/A');
    RAISE NOTICE 'ProductPrice Backup Table: %', COALESCE(v_pp_backup_table, 'N/A');
    RAISE NOTICE '========================================================';

    v_summary := FORMAT(
            'Deactivation completed. PLVs deactivated: %s, ProductPrices deactivated: %s. ' ||
            'Backup tables: PLV=[%s], ProductPrice=[%s]',
            v_plv_count,
            COALESCE(v_productprice_count, 0),
            COALESCE(v_plv_backup_table, 'N/A'),
            COALESCE(v_pp_backup_table, 'N/A')
                 );

    RETURN v_summary;
END;
$func$
;

COMMENT ON FUNCTION deactivate_old_plv_productprices(INTEGER, INTEGER, INTEGER, INTEGER, CHAR, INTEGER) IS
    'Deactivates old Price List Versions (PLVs) and their associated Product Prices.

    CRITICAL SAFETY FEATURE: Only deactivates PLVs when a newer version exists for the same Price List.
    This prevents deactivating the latest/only PLV for a price list, ensuring pricing data continuity.

    IMPORTANT: The "Valid Days Back" parameter (p_valid_days_back) is INCLUSIVE.
    - Example: p_valid_days_back = 30 means "30 days ago OR OLDER"
    - Formula: validfrom <= NOW() - INTERVAL ''X days''

    Deactivation Criteria (ALL must be met):
    1. PLV is currently active (isactive = ''Y'')
    2. PLV''s validfrom date <= cutoff date (X days ago)
    3. A NEWER PLV exists for the same m_pricelist_id (with later validfrom date)
    4. Any optional filters match (pricing system, pricelist, etc.)

    All provided filter parameters are combined with AND logic.

    Parameters:
    - p_m_pricingsystem_id:    (Optional) Filter by Pricing System ID
    - p_m_pricelist_id:        (Optional) Filter by Price List ID
    - p_basepricelist_id:      (Optional) Filter by Base Price List ID of the Price List
    - p_base_plv_pricelist_id: (Optional) Filter by Price List ID of the PLV''s Base PLV
    - p_issotrx:               (Optional) ''Y''=Sales, ''N''=Purchase, NULL=All (ignored if p_m_pricelist_id is set)
    - p_valid_days_back:       (Required) Days back - INCLUSIVE (X days ago or older)

    Returns: Summary message with deactivation counts and backup table names.

    Backups are created BEFORE updates in schema "backup" with format: {tablename}_bkp_YYYYMMDD_HHMMSS'
;

/*
-- NOTE: All examples below will ONLY deactivate PLVs where a newer version
-- exists for the same Price List. PLVs that are the latest/only version
-- for their price list will NOT be deactivated, even if they meet the age criteria.

-- Example 1: Deactivate all PLVs older than 90 days
-- Only PLVs with newer versions will be deactivated
SELECT deactivate_old_plv_productprices(
    NULL,   -- p_m_pricingsystem_id: ALL
    NULL,   -- p_m_pricelist_id: ALL
    NULL,   -- p_basepricelist_id: ALL
    NULL,   -- p_base_plv_pricelist_id: ALL
    NULL,   -- p_issotrx: ALL
    90      -- p_valid_days_back: 90 days or older (INCLUSIVE)
);

-- Example 2: Deactivate PLVs for a specific Pricing System, older than 60 days
-- Only if newer version exists for the same price list
SELECT deactivate_old_plv_productprices(
    1000001,  -- p_m_pricingsystem_id
    NULL,     -- p_m_pricelist_id
    NULL,     -- p_basepricelist_id
    NULL,     -- p_base_plv_pricelist_id
    NULL,     -- p_issotrx
    60        -- p_valid_days_back: 60 days or older
);

-- Example 3: Deactivate only Sales PLVs older than 30 days
-- Only if newer version exists for the same price list
SELECT deactivate_old_plv_productprices(
    NULL,   -- p_m_pricingsystem_id
    NULL,   -- p_m_pricelist_id
    NULL,   -- p_basepricelist_id
    NULL,   -- p_base_plv_pricelist_id
    'Y',    -- p_issotrx: Sales only
    30      -- p_valid_days_back: 30 days or older
);

-- Example 4: Deactivate PLVs for a specific Base Price List
-- Only if newer version exists for the same price list
SELECT deactivate_old_plv_productprices(
    NULL,       -- p_m_pricingsystem_id
    NULL,       -- p_m_pricelist_id
    1000010,    -- p_basepricelist_id: Filter by this base price list
    NULL,       -- p_base_plv_pricelist_id
    NULL,       -- p_issotrx
    45          -- p_valid_days_back: 45 days or older
);

-- Example 5: Deactivate PLVs where the Base PLV belongs to a specific Price List
-- Only if newer version exists for the same price list
SELECT deactivate_old_plv_productprices(
    NULL,       -- p_m_pricingsystem_id
    NULL,       -- p_m_pricelist_id
    NULL,       -- p_basepricelist_id
    1000020,    -- p_base_plv_pricelist_id: Base PLV's price list
    NULL,       -- p_issotrx
    60          -- p_valid_days_back: 60 days or older
);
*/

