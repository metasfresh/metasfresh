/*

Purpose: Automatically deactivate old Price List Versions and their
         associated Product Prices based on configurable criteria.
         Includes SINGLE-LEVEL CASCADING DEACTIVATION for dependent PLVs.

CRITICAL SAFETY FEATURE: Only deactivates PLVs when a newer version exists
         for the same Price List. This prevents deactivating the latest/only
         PLV for a price list, ensuring pricing data continuity.
SINGLE-LEVEL CASCADING DEACTIVATION BEHAVIOR:
  When a PLV is marked for deactivation, the function checks if any other
  PLVs DIRECTLY use it as their base (via m_pricelist_version_base_id).
  If a dependent PLV meets the following criteria, it is also deactivated:
    - Is currently active
    - Meets the age criteria (validfrom <= cutoff date)
    - Has a newer version for its own price list

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
    v_cutoff_date               TIMESTAMP;
    v_where_clause              TEXT := '';
    v_original_plv_ids          BIGINT[];
    v_dependent_plv_ids         BIGINT[];
    v_all_plv_ids               BIGINT[];
    v_productprice_ids          BIGINT[];
    v_original_plv_count        INTEGER := 0;
    v_dependent_plv_count       INTEGER := 0;
    v_total_plv_count           INTEGER := 0;
    v_productprice_count        INTEGER := 0;
    v_plv_backup_table          TEXT;
    v_pp_backup_table           TEXT;
    v_sql                       TEXT;
    v_summary                   TEXT;
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
    RAISE NOTICE 'Starting PLV Deactivation Process (Single-Level Cascading)';
    RAISE NOTICE '========================================================';
    RAISE NOTICE 'SAFETY: Only deactivates PLVs when a newer version exists for the same Price List';
    RAISE NOTICE 'CASCADING: Single-level only - direct dependent PLVs (using original PLVs as base)';
    RAISE NOTICE '           will be deactivated if they meet age criteria and have newer versions';
    RAISE NOTICE 'Cutoff Date: % (PLVs with validfrom <= this date will be considered)', v_cutoff_date;
    RAISE NOTICE 'Parameters:';
    RAISE NOTICE '  - p_m_pricingsystem_id: %', COALESCE(p_m_pricingsystem_id::TEXT, 'ALL');
    RAISE NOTICE '  - p_m_pricelist_id: %', COALESCE(p_m_pricelist_id::TEXT, 'ALL');
    RAISE NOTICE '  - p_basepricelist_id: %', COALESCE(p_basepricelist_id::TEXT, 'ALL');
    RAISE NOTICE '  - p_base_plv_pricelist_id: %', COALESCE(p_base_plv_pricelist_id::TEXT, 'ALL');
    RAISE NOTICE '  - p_issotrx: %', COALESCE(p_issotrx, 'ALL');
    RAISE NOTICE '  - p_valid_days_back: % days (INCLUSIVE)', p_valid_days_back;
    RAISE NOTICE '--------------------------------------------------------';


    -- Build dynamic WHERE clause for ORIGINAL PLVs
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

    EXECUTE v_sql INTO v_original_plv_ids USING v_cutoff_date;

    -- Check if any original PLVs found
    IF v_original_plv_ids IS NULL OR array_length(v_original_plv_ids, 1) IS NULL THEN
        RAISE NOTICE 'No matching Price List Versions found to deactivate.';
        RETURN 'No records to deactivate. No changes made.';
    END IF;

    v_original_plv_count := array_length(v_original_plv_ids, 1);
    RAISE NOTICE 'Found % original Price List Version(s) to deactivate (from criteria)', v_original_plv_count;

    --  Find DEPENDENT PLVs (single-level cascading)
    -- Find PLVs that:
    -- 1. Use any of the original PLVs as their base (m_pricelist_version_base_id)
    -- 2. Are currently active
    -- 3. Meet the age criteria (validfrom <= cutoff date)
    -- 4. Have a newer version for their own price list
    -- 5. Are not already in the original list
    RAISE NOTICE '--------------------------------------------------------';
    RAISE NOTICE 'Checking for dependent PLVs (single-level)...';

    SELECT ARRAY_AGG(plv.m_pricelist_version_id)
    INTO v_dependent_plv_ids
    FROM m_pricelist_version plv
    WHERE plv.m_pricelist_version_base_id = ANY(v_original_plv_ids)
      AND plv.isactive = 'Y'
      AND plv.validfrom <= v_cutoff_date
      AND NOT (plv.m_pricelist_version_id = ANY(v_original_plv_ids))
      AND EXISTS (
        SELECT 1 FROM m_pricelist_version newer_plv
        WHERE newer_plv.m_pricelist_id = plv.m_pricelist_id
          AND newer_plv.validfrom > plv.validfrom
    );

    -- Calculate dependent count
    IF v_dependent_plv_ids IS NOT NULL AND array_length(v_dependent_plv_ids, 1) IS NOT NULL THEN
        v_dependent_plv_count := array_length(v_dependent_plv_ids, 1);
        RAISE NOTICE 'Found % dependent PLV(s) (single-level) with newer versions', v_dependent_plv_count;
    ELSE
        RAISE NOTICE 'No dependent PLVs found';
        v_dependent_plv_ids := ARRAY[]::BIGINT[];
    END IF;

    -- Combine original and dependent PLVs
    v_all_plv_ids := v_original_plv_ids || v_dependent_plv_ids;
    v_total_plv_count := array_length(v_all_plv_ids, 1);

    RAISE NOTICE 'Total PLVs to deactivate: % (% original + % dependent)',
        v_total_plv_count, v_original_plv_count, v_dependent_plv_count;

    -- Find associated ProductPrices to deactivate (for ALL PLVs)
    SELECT ARRAY_AGG(pp.m_productprice_id)
    INTO v_productprice_ids
    FROM m_productprice pp
    WHERE pp.m_pricelist_version_id = ANY(v_all_plv_ids)
      AND pp.isactive = 'Y';

    IF v_productprice_ids IS NOT NULL THEN
        v_productprice_count := array_length(v_productprice_ids, 1);
    END IF;

    RAISE NOTICE 'Found % active Product Price(s) to deactivate', COALESCE(v_productprice_count, 0);

    -- Create backups BEFORE updating (includes ALL PLVs - original + dependent)
    RAISE NOTICE '--------------------------------------------------------';
    RAISE NOTICE 'Creating backups...';

    -- Ensure backup schema exists
    CREATE SCHEMA IF NOT EXISTS backup;

    -- Backup ALL PLVs (original + dependent)
    v_plv_backup_table := backup_records_selective(
            'public',
            'm_pricelist_version',
            'm_pricelist_version_id',
            v_all_plv_ids
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

    -- Deactivate ALL PLVs (original + dependent)
    RAISE NOTICE '--------------------------------------------------------';
    RAISE NOTICE 'Deactivating Price List Versions...';

    UPDATE m_pricelist_version
    SET isactive = 'N',
        updated = NOW(),
        updatedby = 99
    WHERE m_pricelist_version_id = ANY(v_all_plv_ids);

    RAISE NOTICE 'Deactivated % Price List Version(s) (% original + % dependent)',
        v_total_plv_count, v_original_plv_count, v_dependent_plv_count;

    -- Deactivate ProductPrices
    IF v_productprice_count > 0 THEN
        RAISE NOTICE 'Deactivating Product Prices...';

        UPDATE m_productprice
        SET isactive = 'N',
            updated = NOW(),
            updatedby = 99
        WHERE m_productprice_id = ANY(v_productprice_ids);

        RAISE NOTICE 'Deactivated % Product Price(s)', v_productprice_count;
    END IF;

    -- Build summary
    RAISE NOTICE '========================================================';
    RAISE NOTICE 'SUMMARY';
    RAISE NOTICE '========================================================';
    RAISE NOTICE 'Original PLVs found: %', v_original_plv_count;
    RAISE NOTICE 'Dependent PLVs found (single-level): %', v_dependent_plv_count;
    RAISE NOTICE 'Total PLVs deactivated: %', v_total_plv_count;
    RAISE NOTICE 'Product Prices deactivated: %', COALESCE(v_productprice_count, 0);
    RAISE NOTICE 'PLV Backup Table: %', COALESCE(v_plv_backup_table, 'N/A');
    RAISE NOTICE 'ProductPrice Backup Table: %', COALESCE(v_pp_backup_table, 'N/A');
    RAISE NOTICE '========================================================';

    v_summary := format(
            'Deactivation completed. PLVs: %s total (%s original + %s dependent). ProductPrices: %s. ' ||
            'Backup tables: PLV=[%s], ProductPrice=[%s]',
            v_total_plv_count,
            v_original_plv_count,
            v_dependent_plv_count,
            COALESCE(v_productprice_count, 0),
            COALESCE(v_plv_backup_table, 'N/A'),
            COALESCE(v_pp_backup_table, 'N/A')
                 );

    RETURN v_summary;
END;
$func$;

COMMENT ON FUNCTION deactivate_old_plv_productprices(INTEGER, INTEGER, INTEGER, INTEGER, CHAR, INTEGER) IS
    'Deactivates old Price List Versions (PLVs) and their associated Product Prices.
    Includes SINGLE-LEVEL CASCADING DEACTIVATION for dependent PLVs.

    CRITICAL SAFETY FEATURE: Only deactivates PLVs when a newer version exists for the same Price List.
    This prevents deactivating the latest/only PLV for a price list, ensuring pricing data continuity.

    SINGLE-LEVEL CASCADING DEACTIVATION BEHAVIOR:
    When a PLV is marked for deactivation, the function checks if any other PLVs DIRECTLY use it
    as their base (via m_pricelist_version_base_id). If a dependent PLV meets the age criteria
    and has a newer version for its own price list, it is also deactivated.

    NOTE: This is SINGLE-LEVEL only - it does NOT recursively find PLVs that depend on the
    dependent PLVs. Only direct dependencies are included.

    IMPORTANT: The minimum age parameter (p_minimum_age_days) is INCLUSIVE.
    - Example: p_minimum_age_days = 30 means "30 days ago OR OLDER"
    - Formula: validfrom <= NOW() - INTERVAL ''X days''

    Deactivation Criteria for ORIGINAL PLVs (ALL must be met):
    1. PLV is currently active (isactive = ''Y'')
    2. PLV''s validfrom date <= cutoff date (X days ago)
    3. A NEWER PLV exists for the same m_pricelist_id (with later validfrom date)
    4. Any optional filters match (pricing system, pricelist, etc.)

    Deactivation Criteria for DEPENDENT PLVs (ALL must be met):
    1. PLV is currently active (isactive = ''Y'')
    2. PLV''s m_pricelist_version_base_id references an original PLV
    3. PLV''s validfrom date <= cutoff date (X days ago)
    4. A NEWER PLV exists for the same m_pricelist_id (with later validfrom date)

    All provided filter parameters are combined with AND logic.

    Parameters:
    - p_m_pricingsystem_id:    (Optional) Filter by Pricing System ID
    - p_m_pricelist_id:        (Optional) Filter by Price List ID
    - p_basepricelist_id:      (Optional) Filter by Base Price List ID of the Price List
    - p_base_plv_pricelist_id: (Optional) Filter by Price List ID of the PLV''s Base PLV
    - p_issotrx:               (Optional) ''Y''=Sales, ''N''=Purchase, NULL=All (ignored if p_m_pricelist_id is set)
    - p_minimum_age_days:      (Required) Minimum age in days for deactivation (INCLUSIVE). PLVs with
                               validfrom this many days ago or older are eligible. Example: 30 means
                               PLVs from 30 days ago or earlier (only if a newer version exists).

    Returns: Summary message with counts (original, dependent, total) and backup table names.

    Backups are created BEFORE updates in schema "backup" with format: {tablename}_bkp_YYYYMMDD_HHMMSS
    Backups include BOTH original and dependent PLVs and their ProductPrices.';

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

