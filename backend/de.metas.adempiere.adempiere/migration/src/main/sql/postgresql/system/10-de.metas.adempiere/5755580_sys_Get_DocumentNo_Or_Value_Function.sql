CREATE OR REPLACE FUNCTION get_documentNo_Or_Value(
    p_ad_table_id NUMERIC,
    p_record_id NUMERIC
)
    RETURNS TEXT AS
$$
DECLARE
    v_table_name TEXT;
    v_column_id TEXT;
    v_result TEXT;
    dyn_sql TEXT;
BEGIN
    -- Get the table name
    SELECT tablename INTO v_table_name
    FROM ad_table
    WHERE ad_table_id = p_ad_table_id;

    IF v_table_name IS NULL THEN
        RAISE NOTICE 'Table not found for AD_Table_ID = %', p_ad_table_id;
        RETURN NULL;
    END IF;

    -- Get the column ID (assuming it's the primary key in the form {table}_id)
    v_column_id := v_table_name || '_id';

    -- Try to get DocumentNo
    dyn_sql := format('SELECT documentno FROM %I WHERE %I = $1', lower(v_table_name), lower(v_column_id));
    BEGIN
        EXECUTE dyn_sql INTO v_result USING p_record_id;
        IF v_result IS NOT NULL THEN
            RETURN v_result;
        END IF;
    EXCEPTION WHEN OTHERS THEN
        -- Ignore if column doesn't exist
        NULL;
    END;

    -- Try to get Value
    dyn_sql := format('SELECT value FROM %I WHERE %I = $1', lower(v_table_name), lower(v_column_id));
    BEGIN
        EXECUTE dyn_sql INTO v_result USING p_record_id;
        IF v_result IS NOT NULL THEN
            RETURN v_result;
        END IF;
    EXCEPTION WHEN OTHERS THEN
        -- Ignore if column doesn't exist
        NULL;
    END;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

/* 
-- test
select get_documentNo_Or_Value( get_table_id('C_Order'), 1261096);

select get_documentNo_Or_Value( get_table_id('M_Product'), 2007170);

*/