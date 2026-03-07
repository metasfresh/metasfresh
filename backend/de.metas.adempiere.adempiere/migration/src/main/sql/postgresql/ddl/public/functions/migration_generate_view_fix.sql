-- Function: migration_generate_view_fix
-- Purpose: Generates DROP + CREATE VIEW script with custom operator patterns
--          replaced by explicit subtractdays() function calls.
--
-- Usage:
--   SELECT migration_generate_view_fix('public', 'c_invoice_candidate_v');
--
-- Returns:
--   SQL script text that can be executed to fix the view
--
-- Pattern Replacements:
--   Original: (timestamp_expr + offset) - 1
--   Fixed:    subtractdays(timestamp_expr + offset, 1)

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

    -- Replace patterns: (timestamp + offset) - 1 -> subtractdays(timestamp + offset, 1)
    -- Only replace the - 1 patterns, NOT the + 14 patterns
    v_fixed_definition := replace(v_fixed_definition,
        'si.invoicedaycutoff) - 1))',
        'si.invoicedaycutoff), 1))');

    -- Replace: si.invoiceday) - 1))) with: si.invoiceday), 1)))
    v_fixed_definition := replace(v_fixed_definition,
        'si.invoiceday) - 1)))',
        'si.invoiceday), 1)))');

    -- Now wrap with subtractdays - replace the opening pattern
    -- ((firstof(...)::timestamp with time zone + si.X), N)
    -- needs: subtractdays((firstof(...)::timestamp with time zone + si.X), N)

    -- For invoicedaycutoff pattern
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
