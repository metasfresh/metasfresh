-- Migration: PostgreSQL 17 Compatibility - Remove Custom Minus Operators
-- https://github.com/metasfresh/metasfresh/issues/XXXX
--
-- Problem: Custom operators in public schema that override the built-in '-' operator
--          cannot be migrated by pg_upgrade to PostgreSQL 17.
--
-- Solution: Replace operator usage with explicit subtractdays() function calls, then drop operators.
--
-- Affected operators:
--   - numeric - timestamptz
--   - numeric - interval
--   - timestamptz - numeric  -> calls subtractdays(timestamptz, numeric)
--   - interval - numeric     -> calls subtractdays(interval, numeric)
--

-- ============================================================================
-- STEP 1: Install helper functions for discovery and fix generation
-- ============================================================================

-- Function: migration_find_all_affected_objects
-- Scans the database for views and functions that use custom minus operators.
DROP FUNCTION IF EXISTS migration_find_all_affected_objects();
CREATE OR REPLACE FUNCTION migration_find_all_affected_objects()
RETURNS TABLE (
    object_type TEXT,
    schema_name TEXT,
    object_name TEXT,
    pattern_found TEXT,
    action_needed TEXT
)
LANGUAGE plpgsql
AS $func$
BEGIN
    RETURN QUERY
    SELECT
        'VIEW'::TEXT,
        v.schemaname::TEXT,
        v.viewname::TEXT,
        'timestamp_expr - numeric_literal'::TEXT,
        CASE
            WHEN v.definition LIKE '%subtractdays%' THEN 'VERIFY - may already be fixed'
            ELSE 'FIX REQUIRED'
        END::TEXT
    FROM pg_views v
    WHERE v.schemaname NOT IN ('pg_catalog', 'information_schema')
      AND (
          v.definition ~ E'timestamp\\s+with\\s+time\\s+zone[^)]*\\)\\s*-\\s*\\d+[^:]'
          OR v.definition ~ E'\\+ si\\.[a-z_]+\\)\\s*-\\s*1'
      )
    ORDER BY v.schemaname, v.viewname;

    RETURN QUERY
    SELECT
        'FUNCTION'::TEXT,
        n.nspname::TEXT,
        p.proname::TEXT,
        CASE
            WHEN p.prosrc ~ E'now\\(\\)\\s*-\\s*\\d+' THEN 'now() - numeric'
            WHEN p.prosrc ~ E'getdate\\(\\)\\s*-\\s*\\d+' THEN 'getdate() - numeric'
            ELSE 'possible timestamp - numeric'
        END::TEXT,
        CASE
            WHEN p.prosrc LIKE '%subtractdays%' THEN 'VERIFY - may already be fixed'
            ELSE 'REVIEW REQUIRED'
        END::TEXT
    FROM pg_proc p
    JOIN pg_namespace n ON n.oid = p.pronamespace
    WHERE n.nspname NOT IN ('pg_catalog', 'information_schema')
      AND p.prokind = 'f'
      AND (
          p.prosrc ~ E'now\\(\\)\\s*-\\s*\\d+'
          OR p.prosrc ~ E'getdate\\(\\)\\s*-\\s*\\d+'
      )
      AND p.proname NOT LIKE 'migration_%'
    ORDER BY n.nspname, p.proname;
END;
$func$;


-- Function: migration_generate_view_fix
-- Generates DROP + CREATE VIEW script with custom operator patterns replaced.
DROP FUNCTION IF EXISTS migration_generate_view_fix(TEXT, TEXT);
CREATE OR REPLACE FUNCTION migration_generate_view_fix(
    p_schema_name TEXT,
    p_view_name TEXT
)
RETURNS TEXT
LANGUAGE plpgsql
AS $func$
DECLARE
    v_definition TEXT;
    v_fixed_definition TEXT;
    v_result TEXT;
    v_changes INTEGER := 0;
BEGIN
    SELECT definition INTO v_definition
    FROM pg_views
    WHERE schemaname = p_schema_name AND viewname = p_view_name;

    IF v_definition IS NULL THEN
        RETURN '-- ERROR: View ' || p_schema_name || '.' || p_view_name || ' not found';
    END IF;

    v_fixed_definition := v_definition;

    -- Replace known patterns with explicit subtractdays() calls
    v_fixed_definition := replace(v_fixed_definition,
        'si.invoicedaycutoff) - 1))',
        'si.invoicedaycutoff), 1))');

    v_fixed_definition := replace(v_fixed_definition,
        'si.invoiceday) - 1)))',
        'si.invoiceday), 1)))');

    v_fixed_definition := replace(v_fixed_definition,
        '(((firstof(getdate()',
        'subtractdays(((firstof(getdate()');

    v_fixed_definition := replace(v_fixed_definition,
        '(((firstof((o.dateordered)',
        'subtractdays(((firstof((o.dateordered)');

    v_changes := (length(v_definition) - length(replace(v_definition, ') - 1)', '')))
               - (length(v_fixed_definition) - length(replace(v_fixed_definition, ') - 1)', '')));

    v_result := '-- Fix for VIEW: ' || p_schema_name || '.' || p_view_name || E'\n';
    v_result := v_result || '-- Changes: ' || v_changes || ' operator replacements' || E'\n\n';
    v_result := v_result || 'DROP VIEW IF EXISTS ' || p_schema_name || '.' || p_view_name || ';' || E'\n\n';
    v_result := v_result || 'CREATE OR REPLACE VIEW ' || p_schema_name || '.' || p_view_name || ' AS' || E'\n';
    v_result := v_result || v_fixed_definition || E'\n';

    RETURN v_result;
END;
$func$;


-- ============================================================================
-- STEP 2: Check if operators exist and need to be fixed
-- ============================================================================

DO $$
DECLARE
    v_op_count INTEGER;
    v_affected_count INTEGER;
BEGIN
    -- Check if custom operators exist
    SELECT COUNT(*) INTO v_op_count
    FROM pg_operator o
    JOIN pg_namespace n ON n.oid = o.oprnamespace
    WHERE n.nspname = 'public' AND o.oprname = '-';

    IF v_op_count = 0 THEN
        RAISE NOTICE 'No custom minus operators found in public schema. Migration not needed.';
        RETURN;
    END IF;

    RAISE NOTICE 'Found % custom minus operator(s) in public schema.', v_op_count;

    -- Check for affected objects
    SELECT COUNT(*) INTO v_affected_count
    FROM migration_find_all_affected_objects()
    WHERE action_needed = 'FIX REQUIRED';

    IF v_affected_count > 0 THEN
        RAISE NOTICE 'Found % object(s) that need to be fixed.', v_affected_count;
    ELSE
        RAISE NOTICE 'No objects need fixing - operators can be safely dropped.';
    END IF;
END $$;


-- ============================================================================
-- STEP 3: Fix c_invoice_candidate_v if it exists and uses custom operators
-- ============================================================================

DO $$
DECLARE
    v_view_exists BOOLEAN;
    v_uses_custom_op BOOLEAN;
BEGIN
    -- Check if view exists
    SELECT EXISTS (
        SELECT 1 FROM pg_views WHERE schemaname = 'public' AND viewname = 'c_invoice_candidate_v'
    ) INTO v_view_exists;

    IF NOT v_view_exists THEN
        RAISE NOTICE 'View c_invoice_candidate_v does not exist - skipping';
        RETURN;
    END IF;

    -- Check if it uses custom operators (has ') - 1)' but no 'subtractdays')
    SELECT EXISTS (
        SELECT 1 FROM pg_views
        WHERE schemaname = 'public'
          AND viewname = 'c_invoice_candidate_v'
          AND definition LIKE '%- 1)%'
          AND definition NOT LIKE '%subtractdays%'
    ) INTO v_uses_custom_op;

    IF NOT v_uses_custom_op THEN
        RAISE NOTICE 'View c_invoice_candidate_v does not use custom operators or is already fixed - skipping';
        RETURN;
    END IF;

    RAISE NOTICE 'Fixing c_invoice_candidate_v...';
END $$;

-- Drop and recreate c_invoice_candidate_v with explicit subtractdays() calls
-- Only executed if the view exists and uses custom operators
DO $$
DECLARE
    v_definition TEXT;
    v_fixed_definition TEXT;
BEGIN
    -- Get current definition
    SELECT definition INTO v_definition
    FROM pg_views
    WHERE schemaname = 'public' AND viewname = 'c_invoice_candidate_v';

    IF v_definition IS NULL THEN
        RETURN;
    END IF;

    -- Check if fix is needed
    IF v_definition NOT LIKE '%- 1)%' OR v_definition LIKE '%subtractdays%' THEN
        RETURN;
    END IF;

    v_fixed_definition := v_definition;

    -- Apply fixes
    v_fixed_definition := replace(v_fixed_definition,
        'si.invoicedaycutoff) - 1))',
        'si.invoicedaycutoff), 1))');

    v_fixed_definition := replace(v_fixed_definition,
        'si.invoiceday) - 1)))',
        'si.invoiceday), 1)))');

    v_fixed_definition := replace(v_fixed_definition,
        '(((firstof(getdate()',
        'subtractdays(((firstof(getdate()');

    v_fixed_definition := replace(v_fixed_definition,
        '(((firstof((o.dateordered)',
        'subtractdays(((firstof((o.dateordered)');

    -- Drop and recreate view
    DROP VIEW IF EXISTS public.c_invoice_candidate_v;

    EXECUTE 'CREATE OR REPLACE VIEW public.c_invoice_candidate_v AS ' || v_fixed_definition;

    RAISE NOTICE 'View c_invoice_candidate_v fixed successfully';
END $$;


-- ============================================================================
-- STEP 4: Drop custom operators
-- ============================================================================

DROP OPERATOR IF EXISTS public.- (timestamptz, numeric);
DROP OPERATOR IF EXISTS public.- (interval, numeric);
DROP OPERATOR IF EXISTS public.- (numeric, timestamptz);
DROP OPERATOR IF EXISTS public.- (numeric, interval);


-- ============================================================================
-- STEP 5: Verification
-- ============================================================================

DO $$
DECLARE
    v_op_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_op_count
    FROM pg_operator o
    JOIN pg_namespace n ON n.oid = o.oprnamespace
    WHERE n.nspname = 'public' AND o.oprname = '-';

    IF v_op_count > 0 THEN
        RAISE EXCEPTION 'Migration incomplete: % custom minus operator(s) still exist in public schema', v_op_count;
    ELSE
        RAISE NOTICE 'SUCCESS: All custom minus operators removed from public schema';
        RAISE NOTICE 'Database is now ready for pg_upgrade to PostgreSQL 17';
    END IF;
END $$;


-- ============================================================================
-- STEP 6: Cleanup helper functions (optional - keep for future use)
-- ============================================================================
-- Note: Helper functions are kept for diagnosing similar issues in other databases
-- To remove them, uncomment the following:
-- DROP FUNCTION IF EXISTS migration_find_all_affected_objects();
-- DROP FUNCTION IF EXISTS migration_generate_view_fix(TEXT, TEXT);
