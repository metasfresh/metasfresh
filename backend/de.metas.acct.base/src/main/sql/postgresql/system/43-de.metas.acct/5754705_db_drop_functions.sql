DROP FUNCTION IF EXISTS db_drop_functions(p_function_name TEXT)
;


CREATE OR REPLACE FUNCTION db_drop_functions(p_function_name TEXT)
    RETURNS VOID
AS
$$
DECLARE
    v_schema_name        text;
    v_function_name      text;
    v_function_signature text;
    v_func               RECORD;
BEGIN
    -- Check if the function name contains a dot ("schema_name.function_name")
    IF STRPOS(p_function_name, '.') > 0 THEN
        -- Split into schema and function name
        v_schema_name := NULLIF(TRIM(SPLIT_PART(p_function_name, '.', 1)), '');
        v_function_name := NULLIF(TRIM(SPLIT_PART(p_function_name, '.', 2)), '');
    ELSE
        -- If no dot, assign a default schema (e.g., 'public') and use input as function name
        v_schema_name := 'public';
        v_function_name := NULLIF(TRIM(p_function_name), '');
    END IF;

    IF (v_schema_name IS NULL) THEN
        RAISE EXCEPTION 'Cannot determine schema name from: %', p_function_name;
    END IF;
    IF (v_function_name IS NULL) THEN
        RAISE EXCEPTION 'Cannot determine function name from: %', p_function_name;
    END IF;

    RAISE NOTICE 'Dropping all functions that match schema_name=% and function_name=% ...', v_schema_name, v_function_name;

    -- Loop through all functions in the given schema with the specified name
    FOR v_func IN (SELECT pg_proc.oid          AS oid,
                          pg_namespace.nspname AS schema_name,
                          proname              AS function_name
                   FROM pg_proc
                            JOIN pg_namespace ON pg_proc.pronamespace = pg_namespace.oid
                   WHERE TRUE
                     AND (v_schema_name = '*' OR LOWER(pg_namespace.nspname) = LOWER(v_schema_name))
                     AND LOWER(pg_proc.proname) = LOWER(v_function_name))
        LOOP
            v_function_signature := FORMAT('%I.%I(%s)',
                                           v_func.schema_name,
                                           v_func.function_name,
                                           PG_GET_FUNCTION_IDENTITY_ARGUMENTS(v_func.oid));

            EXECUTE 'DROP FUNCTION IF EXISTS ' || v_function_signature;
            RAISE NOTICE 'Dropped function %', v_function_signature;
        END LOOP;

    RAISE NOTICE 'Dropped all functions that match schema_name=% and function_name=%', v_schema_name, v_function_name;
END;
$$
    LANGUAGE plpgsql
;



