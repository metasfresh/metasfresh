DROP FUNCTION IF EXISTS public.db_dependent_views_of_function(text);

CREATE OR REPLACE FUNCTION public.db_dependent_views_of_function(IN p_function_name text)
    RETURNS TABLE
            (
                view_schema text,
                view_name   text,
                view_def    text
            )
AS
$BODY$
DECLARE
    v_function_name text;
BEGIN
    -- Strip signature if present (e.g., 'schema.func(int, text)' -> 'schema.func')
    v_function_name := regexp_replace(p_function_name, '\([^)]*\)$', '');

    RETURN QUERY
        WITH function_oid AS (
            -- Find the function OID by name (handles schema.function_name format)
            SELECT p.oid
            FROM pg_proc p
                     JOIN pg_namespace n ON p.pronamespace = n.oid
            WHERE CASE
                      WHEN position('.' IN v_function_name) > 0 THEN
                              n.nspname || '.' || p.proname = lower(v_function_name)
                      ELSE
                          p.proname = lower(v_function_name)
                END
            LIMIT 1
        ),
             dependent_views AS (
                 -- Find views that depend on this function
                 SELECT DISTINCT c.relnamespace::regnamespace::text AS view_schema,
                                 c.relname::text                    AS view_name
                 FROM pg_depend d
                          JOIN function_oid f ON d.refobjid = f.oid
                          JOIN pg_rewrite r ON d.objid = r.oid
                          JOIN pg_class c ON r.ev_class = c.oid
                 WHERE d.classid = 'pg_rewrite'::regclass
                   AND c.relkind = 'v' -- views only
             )
        SELECT dv.view_schema,
               dv.view_name,
               pg_get_viewdef((dv.view_schema || '.' || dv.view_name)::regclass, true) AS view_def
        FROM dependent_views dv;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE;

COMMENT ON FUNCTION public.db_dependent_views_of_function(text)
    IS 'Finds views that directly depend on the given function. Returns schema, name, and definition of each dependent view. Handles both schema.function_name and schema.function_name(args) formats.';
