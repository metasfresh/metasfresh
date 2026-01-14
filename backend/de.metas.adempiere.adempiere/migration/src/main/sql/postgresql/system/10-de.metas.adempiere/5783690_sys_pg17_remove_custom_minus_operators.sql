-- Migration: PostgreSQL 17 Compatibility - Remove Custom Minus Operators
-- https://github.com/metasfresh/metasfresh/pull/21982
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
-- STEP 1: Install helper functions for discovery and dependency checking
-- ============================================================================

-- Function: migration_find_all_affected_objects
-- Uses pg_depend for accurate dependency tracking plus text pattern matching as fallback
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
DECLARE
    v_operator_oids oid[];
BEGIN
    -- Get all custom minus operator OIDs in public schema
    SELECT array_agg(o.oid) INTO v_operator_oids
    FROM pg_operator o
    JOIN pg_namespace n ON n.oid = o.oprnamespace
    WHERE n.nspname = 'public' AND o.oprname = '-';

    IF v_operator_oids IS NULL OR array_length(v_operator_oids, 1) IS NULL THEN
        RETURN;
    END IF;

    -- Method 1: Check pg_depend for direct dependencies (most accurate)
    RETURN QUERY
    SELECT DISTINCT
        CASE c.relkind
            WHEN 'v' THEN 'VIEW'
            WHEN 'r' THEN 'TABLE'
            WHEN 'm' THEN 'MATERIALIZED VIEW'
            ELSE 'OBJECT (' || c.relkind || ')'
        END::TEXT,
        n.nspname::TEXT,
        c.relname::TEXT,
        'pg_depend reference'::TEXT,
        'FIX REQUIRED (direct dependency)'::TEXT
    FROM pg_depend d
    JOIN pg_class c ON c.oid = d.objid
    JOIN pg_namespace n ON n.oid = c.relnamespace
    WHERE d.refobjid = ANY(v_operator_oids)
      AND c.relkind IN ('v', 'r', 'm');

    -- Method 2: Check pg_depend via rewrite rules (for views)
    RETURN QUERY
    SELECT DISTINCT
        'VIEW'::TEXT,
        n.nspname::TEXT,
        c.relname::TEXT,
        'pg_rewrite dependency'::TEXT,
        'FIX REQUIRED (rewrite rule)'::TEXT
    FROM pg_depend d
    JOIN pg_rewrite r ON r.oid = d.objid
    JOIN pg_class c ON c.oid = r.ev_class
    JOIN pg_namespace n ON n.oid = c.relnamespace
    WHERE d.refobjid = ANY(v_operator_oids)
      AND n.nspname NOT IN ('pg_catalog', 'information_schema');

    -- Method 3: Check for operator class dependencies
    RETURN QUERY
    SELECT
        'OPERATOR CLASS'::TEXT,
        n.nspname::TEXT,
        opc.opcname::TEXT,
        'operator class member'::TEXT,
        'FIX REQUIRED (operator class)'::TEXT
    FROM pg_amop amop
    JOIN pg_opclass opc ON opc.oid = amop.amopfamily
    JOIN pg_namespace n ON n.oid = opc.opcnamespace
    WHERE amop.amopopr = ANY(v_operator_oids);

    -- Method 4: Text search in view definitions (fallback for edge cases)
    RETURN QUERY
    SELECT
        'VIEW'::TEXT,
        v.schemaname::TEXT,
        v.viewname::TEXT,
        'timestamp_expr - numeric (text match)'::TEXT,
        CASE
            WHEN v.definition LIKE '%subtractdays%' THEN 'VERIFY - may already be fixed'
            ELSE 'FIX REQUIRED (text pattern)'
        END::TEXT
    FROM pg_views v
    WHERE v.schemaname NOT IN ('pg_catalog', 'information_schema')
      AND (
          v.definition ~ E'timestamp\\s+with\\s+time\\s+zone[^)]*\\)\\s*-\\s*\\d+[^:]'
          OR v.definition ~ E'\\+ si\\.[a-z_]+\\)\\s*-\\s*1'
      )
      AND NOT EXISTS (
          SELECT 1 FROM pg_depend d
          JOIN pg_rewrite r ON r.oid = d.objid
          JOIN pg_class c ON c.oid = r.ev_class
          WHERE d.refobjid = ANY(v_operator_oids)
            AND c.relname = v.viewname
      );
END;
$func$;


-- Function: migration_test_drop_operators
-- Tests if operators can be dropped by checking pg_depend for dependencies
-- Reports blocking dependencies if any (without actually attempting to drop)
DROP FUNCTION IF EXISTS migration_test_drop_operators();
CREATE OR REPLACE FUNCTION migration_test_drop_operators()
RETURNS TABLE (
    operator_signature TEXT,
    can_drop BOOLEAN,
    blocking_reason TEXT
)
LANGUAGE plpgsql
AS $func$
DECLARE
    v_rec RECORD;
    v_dep_count INTEGER;
    v_dep_objects TEXT;
BEGIN
    FOR v_rec IN
        SELECT
            o.oid,
            o.oprname || ' (' || COALESCE(lt.typname, 'NONE') || ', ' || COALESCE(rt.typname, 'NONE') || ')' AS signature
        FROM pg_operator o
        JOIN pg_namespace n ON n.oid = o.oprnamespace
        LEFT JOIN pg_type lt ON lt.oid = o.oprleft
        LEFT JOIN pg_type rt ON rt.oid = o.oprright
        WHERE n.nspname = 'public' AND o.oprname = '-'
    LOOP
        operator_signature := v_rec.signature;

        -- Check for dependencies in pg_depend (direct object dependencies)
        SELECT COUNT(*), string_agg(DISTINCT c.relname, ', ')
        INTO v_dep_count, v_dep_objects
        FROM pg_depend d
        JOIN pg_class c ON c.oid = d.objid
        WHERE d.refobjid = v_rec.oid
          AND d.deptype = 'n';

        -- Also check for rewrite rule dependencies (views)
        IF v_dep_count = 0 THEN
            SELECT COUNT(*), string_agg(DISTINCT c.relname, ', ')
            INTO v_dep_count, v_dep_objects
            FROM pg_depend d
            JOIN pg_rewrite r ON r.oid = d.objid
            JOIN pg_class c ON c.oid = r.ev_class
            WHERE d.refobjid = v_rec.oid;
        END IF;

        IF v_dep_count > 0 THEN
            can_drop := FALSE;
            blocking_reason := 'Blocked by ' || v_dep_count || ' object(s): ' || COALESCE(v_dep_objects, '(unknown)');
        ELSE
            can_drop := TRUE;
            blocking_reason := NULL;
        END IF;

        RETURN NEXT;
    END LOOP;

    IF NOT FOUND THEN
        operator_signature := '(no custom operators found)';
        can_drop := TRUE;
        blocking_reason := 'No operators to drop';
        RETURN NEXT;
    END IF;
END;
$func$;


-- Function: migration_generate_view_fix
-- Generates DROP + CREATE VIEW script with custom operator patterns replaced
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
BEGIN
    SELECT definition INTO v_definition
    FROM pg_views
    WHERE schemaname = p_schema_name AND viewname = p_view_name;

    IF v_definition IS NULL THEN
        RETURN '-- ERROR: View ' || p_schema_name || '.' || p_view_name || ' not found';
    END IF;

    v_fixed_definition := v_definition;

    -- Replace patterns: (timestamp + offset) - 1 -> subtractdays(timestamp + offset, 1)
    -- Only replace the - 1 patterns, NOT the + 14 patterns
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

    v_result := '-- Fix for VIEW: ' || p_schema_name || '.' || p_view_name || E'\n';
    v_result := v_result || 'DROP VIEW IF EXISTS ' || p_schema_name || '.' || p_view_name || ';' || E'\n';
    v_result := v_result || 'CREATE OR REPLACE VIEW ' || p_schema_name || '.' || p_view_name || ' AS' || E'\n';
    v_result := v_result || v_fixed_definition || E'\n';

    RETURN v_result;
END;
$func$;


-- ============================================================================
-- STEP 2: Check if migration is needed
-- ============================================================================

DO $$
DECLARE
    v_op_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO v_op_count
    FROM pg_operator o
    JOIN pg_namespace n ON n.oid = o.oprnamespace
    WHERE n.nspname = 'public' AND o.oprname = '-';

    IF v_op_count = 0 THEN
        RAISE NOTICE 'No custom minus operators found. Migration already complete or not needed.';
    ELSE
        RAISE NOTICE 'Found % custom minus operator(s) to remove.', v_op_count;
    END IF;
END $$;


-- ============================================================================
-- STEP 3: Pre-flight check - verify all operators can be dropped
-- ============================================================================

DO $$
DECLARE
    v_rec RECORD;
    v_has_blockers BOOLEAN := FALSE;
BEGIN
    FOR v_rec IN SELECT * FROM migration_test_drop_operators() WHERE NOT can_drop
    LOOP
        v_has_blockers := TRUE;
        RAISE WARNING 'Cannot drop operator %: %', v_rec.operator_signature, v_rec.blocking_reason;
    END LOOP;

    IF v_has_blockers THEN
        RAISE NOTICE '';
        RAISE NOTICE 'Blocked dependencies found. Checking affected objects...';
        FOR v_rec IN SELECT * FROM migration_find_all_affected_objects()
        LOOP
            RAISE NOTICE '  % %.% - % (%)',
                v_rec.object_type, v_rec.schema_name, v_rec.object_name,
                v_rec.pattern_found, v_rec.action_needed;
        END LOOP;
        RAISE EXCEPTION 'Migration blocked: Fix all dependencies before dropping operators. Do NOT use CASCADE.';
    END IF;

    RAISE NOTICE 'Pre-flight check passed: All operators can be safely dropped.';
END $$;


-- ============================================================================
-- STEP 4: Fix c_invoice_candidate_v if it exists and uses custom operators
-- ============================================================================

DO $$
DECLARE
    v_definition TEXT;
    v_fixed_definition TEXT;
BEGIN
    SELECT definition INTO v_definition
    FROM pg_views
    WHERE schemaname = 'public' AND viewname = 'c_invoice_candidate_v';

    IF v_definition IS NULL THEN
        RAISE NOTICE 'View c_invoice_candidate_v does not exist - skipping';
        RETURN;
    END IF;

    IF v_definition NOT LIKE '%- 1)%' OR v_definition LIKE '%subtractdays%' THEN
        RAISE NOTICE 'View c_invoice_candidate_v does not need fixing - skipping';
        RETURN;
    END IF;

    RAISE NOTICE 'Fixing c_invoice_candidate_v...';

    v_fixed_definition := v_definition;

    -- Apply fixes - only for - 1 patterns, NOT + 14 patterns
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

    DROP VIEW IF EXISTS public.c_invoice_candidate_v;
    EXECUTE 'CREATE OR REPLACE VIEW public.c_invoice_candidate_v AS ' || v_fixed_definition;

    RAISE NOTICE 'View c_invoice_candidate_v fixed successfully';
END $$;


-- ============================================================================
-- STEP 5: Drop custom operators (NO CASCADE - must fail if dependencies exist)
-- ============================================================================

DROP OPERATOR IF EXISTS public.- (timestamptz, numeric);
DROP OPERATOR IF EXISTS public.- (interval, numeric);
DROP OPERATOR IF EXISTS public.- (numeric, timestamptz);
DROP OPERATOR IF EXISTS public.- (numeric, interval);


-- ============================================================================
-- STEP 6: Verification
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
        RAISE EXCEPTION 'Migration incomplete: % custom minus operator(s) still exist', v_op_count;
    ELSE
        RAISE NOTICE '';
        RAISE NOTICE '=========================================================';
        RAISE NOTICE 'SUCCESS: All custom minus operators removed';
        RAISE NOTICE 'Database is now ready for pg_upgrade to PostgreSQL 17';
        RAISE NOTICE '=========================================================';
    END IF;
END $$;
