-- Title: Add db_alter_function helper for safely altering functions with dependent views
-- Description: Creates db_dependent_views_of_function and db_alter_function to handle
--              function alterations that have dependent views, similar to db_alter_view.
-- 2026-01-17
-- Task: Improve migration tooling for function DDL changes

-- ===========================================================================
-- 1. db_dependent_views_of_function - finds views that depend on a given function
-- ===========================================================================
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


-- ===========================================================================
-- 2. db_alter_function - safely alters a function by handling dependent views
-- ===========================================================================
DROP FUNCTION IF EXISTS public.db_alter_function(text, text);

CREATE OR REPLACE FUNCTION public.db_alter_function(
    p_function_name text,       -- Function name (with schema), e.g. 'de_metas_endcustomer_fresh_reports.Docs_Sales_InOut_Sum_Weight'
    p_function_definition text  -- Full CREATE OR REPLACE FUNCTION statement
)
    RETURNS void AS
$BODY$
DECLARE
    i                int;
    j                int;
    v                record;
    viewFullName     text[];
    viewDef          text[];
    dropViews        text[];
    currentViewName  text;
    command          text;
BEGIN
    -- 1. Fetch dependent views and store them
    i := 0;
    FOR v IN (SELECT view_schema, view_name, view_def
              FROM public.db_dependent_views_of_function(p_function_name))
        LOOP
            currentViewName := '"' || v.view_schema || '"."' || v.view_name || '"';

            -- Skip if already in the list
            IF viewFullName @> array [currentViewName] THEN
                RAISE NOTICE 'skip view % because it was already detected', currentViewName;
                CONTINUE;
            END IF;

            i := i + 1;
            viewFullName[i] := currentViewName;
            viewDef[i] := v.view_def;
            RAISE NOTICE 'Found dependent view: %', currentViewName;
        END LOOP;

    -- 2. Drop dependent views
    IF i > 0 THEN
        FOR j IN 1 .. i
            LOOP
                RAISE NOTICE 'Dropping view %', viewFullName[j];
                command := 'DROP VIEW IF EXISTS ' || viewFullName[j];
                EXECUTE command;
                dropViews[j] := viewFullName[j];
            END LOOP;
    END IF;

    -- 3. Drop and recreate the function
    RAISE NOTICE 'Dropping function %', p_function_name;
    EXECUTE 'DROP FUNCTION IF EXISTS ' || p_function_name;

    RAISE NOTICE 'Creating function with new definition';
    EXECUTE p_function_definition;

    -- 4. Recreate dependent views in reverse order
    IF i > 0 THEN
        FOR j IN REVERSE i .. 1
            LOOP
                RAISE NOTICE 'Recreating view %', dropViews[j];
                command := 'CREATE OR REPLACE VIEW ' || dropViews[j] || ' AS ' || viewDef[j];
                EXECUTE command;
            END LOOP;
    END IF;

    RAISE NOTICE 'db_alter_function completed successfully for %', p_function_name;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE;

COMMENT ON FUNCTION public.db_alter_function(text, text)
    IS 'Safely alters a function by first dropping dependent views, then dropping and recreating the function, and finally recreating the dependent views. Use this when you need to change a function''s parameter names or signature and there are dependent views.';
