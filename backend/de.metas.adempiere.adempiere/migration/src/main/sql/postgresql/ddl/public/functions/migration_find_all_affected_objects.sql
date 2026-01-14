-- Function: migration_find_all_affected_objects
-- Purpose: Scans the database for views and functions that use custom minus operators
--          (timestamptz - numeric, interval - numeric) which are incompatible with pg_upgrade.
--
-- Usage:
--   SELECT * FROM migration_find_all_affected_objects();
--
-- Returns:
--   object_type   - 'VIEW' or 'FUNCTION'
--   schema_name   - Schema containing the object
--   object_name   - Name of the view/function
--   pattern_found - Description of the detected pattern
--   action_needed - 'FIX REQUIRED', 'REVIEW REQUIRED', or 'VERIFY - may already be fixed'

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
    -- Find views with custom operator usage
    -- Pattern: (timestamp_expr) - numeric_literal like ") - 1)" or "+ offset) - 1"
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
      -- Pattern: timestamp with time zone ... ) - N  (not followed by ::)
      AND (
          v.definition ~ E'timestamp\\s+with\\s+time\\s+zone[^)]*\\)\\s*-\\s*\\d+[^:]'
          OR v.definition ~ E'\\+ si\\.[a-z_]+\\)\\s*-\\s*1'  -- specific metasfresh pattern
      )
    ORDER BY v.schemaname, v.viewname;

    -- Find functions that might use custom operators
    -- More conservative: look for now() - N or getdate() - N patterns
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
      AND p.proname NOT LIKE 'migration_%'  -- exclude our helper functions
    ORDER BY n.nspname, p.proname;
END;
$func$;
