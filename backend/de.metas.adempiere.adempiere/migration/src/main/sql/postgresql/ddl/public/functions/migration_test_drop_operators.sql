-- Function: migration_test_drop_operators
-- Purpose: Tests if custom minus operators can be dropped WITHOUT actually dropping them.
--          Uses SAVEPOINT to rollback test drops, reporting blocking dependencies if any.
--
-- Usage:
--   SELECT * FROM migration_test_drop_operators();
--
-- Returns:
--   operator_signature - The operator being tested (e.g., "- (timestamptz, numeric)")
--   can_drop          - TRUE if operator can be safely dropped, FALSE if blocked
--   blocking_reason   - Error message if blocked, NULL if can drop
--
-- Safety:
--   This function uses SAVEPOINT to ensure no actual changes are made.
--   All test drops are rolled back immediately after testing.

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
BEGIN
    FOR v_rec IN
        SELECT
            o.oid,
            o.oprname || ' (' || COALESCE(lt.typname, 'NONE') || ', ' || COALESCE(rt.typname, 'NONE') || ')' AS signature,
            'public.- (' || COALESCE(lt.typname, 'NONE') || ', ' || COALESCE(rt.typname, 'NONE') || ')' AS drop_syntax
        FROM pg_operator o
        JOIN pg_namespace n ON n.oid = o.oprnamespace
        LEFT JOIN pg_type lt ON lt.oid = o.oprleft
        LEFT JOIN pg_type rt ON rt.oid = o.oprright
        WHERE n.nspname = 'public' AND o.oprname = '-'
    LOOP
        operator_signature := v_rec.signature;
        BEGIN
            EXECUTE 'SAVEPOINT test_drop';
            EXECUTE 'DROP OPERATOR IF EXISTS ' || v_rec.drop_syntax;
            can_drop := TRUE;
            blocking_reason := NULL;
            EXECUTE 'ROLLBACK TO SAVEPOINT test_drop';
        EXCEPTION WHEN OTHERS THEN
            can_drop := FALSE;
            blocking_reason := SQLERRM;
            EXECUTE 'ROLLBACK TO SAVEPOINT test_drop';
        END;
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
