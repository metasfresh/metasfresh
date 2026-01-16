-- Function: migration_find_all_affected_objects
-- Purpose: Discovers ALL objects that depend on custom minus operators in public schema.
--          Uses multiple detection methods for comprehensive coverage.
--
-- Usage:
--   SELECT * FROM migration_find_all_affected_objects();
--
-- Detection methods:
--   1. pg_depend - direct dependencies
--   2. pg_rewrite - view rewrite rules
--   3. pg_amop - operator class memberships
--   4. Text pattern matching - fallback for edge cases

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
            ELSE 'OBJECT (' || c.relkind::text || ')'
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
      -- Pattern: timestamp with time zone ... ) - N  (not followed by ::)
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
