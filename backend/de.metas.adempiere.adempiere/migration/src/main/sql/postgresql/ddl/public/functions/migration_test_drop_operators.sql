-- Function: migration_test_drop_operators
-- Purpose: Tests if custom minus operators can be dropped by checking pg_depend
--          for dependencies. Reports blocking dependencies if any.
--
-- Usage:
--   SELECT * FROM migration_test_drop_operators();
--
-- Returns:
--   operator_signature - The operator being tested (e.g., "- (timestamptz, numeric)")
--   can_drop          - TRUE if operator can be safely dropped, FALSE if blocked
--   blocking_reason   - Description of blocking objects, NULL if can drop
--
-- Safety:
--   This function only queries pg_depend - it does not attempt any modifications.
--   It checks both direct dependencies and view rewrite rule dependencies.

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
