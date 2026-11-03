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
