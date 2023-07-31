DROP FUNCTION IF EXISTS db_compare_tables(
    p_table_name1  text,
    p_table_name2  text,
    p_id_column    text,
    p_table_schema text
)
;

CREATE OR REPLACE FUNCTION db_compare_tables(
    p_table_name1  text,
    p_table_name2  text,
    p_id_column    text,
    p_table_schema text = 'public'
)
    RETURNS TABLE
            (
                id1  text,
                id2  text,
                diff text
            )
    LANGUAGE plpgsql
    VOLATILE
AS
$$
DECLARE
    v_columnInfo   record;
    v_sql_where    text = '';
    v_sql_diff_msg text = '';
    v_sql          text = '';
BEGIN
    FOR v_columnInfo IN (SELECT table_catalog,
                                table_schema,
                                column_name
                         FROM information_schema.columns
                         WHERE TRUE
                           AND table_catalog = 'metasfresh'
                           AND table_schema = p_table_schema
                           AND (LOWER(table_name) = LOWER(p_table_name1) OR LOWER(table_name) = LOWER(p_table_name2))
                         GROUP BY table_catalog, table_schema, column_name
                         HAVING COUNT(1) = 2
                         ORDER BY table_catalog, table_schema, column_name)
        LOOP
            IF (LENGTH(v_sql_diff_msg) > 0) THEN
                v_sql_diff_msg = v_sql_diff_msg || ' || ';
            END IF;
            v_sql_diff_msg = v_sql_diff_msg
                                 || '(case when t1.' || v_columnInfo.column_name || ' IS DISTINCT FROM t2.' || v_columnInfo.column_name ||
                             ' then ''' || v_columnInfo.column_name || '=''||coalesce(t1.' || v_columnInfo.column_name || '::text,''NULL'')||''->''||coalesce(t2.' || v_columnInfo.column_name || '::text,''NULL'')||''; ''' ||
                             ' else '''' end)';

            IF (LENGTH(v_sql_where) > 0) THEN
                v_sql_where = v_sql_where || ' OR ';
            END IF;
            v_sql_where = v_sql_where || 't1.' || v_columnInfo.column_name || ' IS DISTINCT FROM t2.' || v_columnInfo.column_name;

            -- RAISE NOTICE 'v_columnInfo=%', v_columnInfo;
        END LOOP;

    v_sql = 'SELECT t1.' || p_id_column || '::text as id1,'
                || 't2.' || p_id_column || '::text as id2,'
                || '(' || v_sql_diff_msg || ') as diff'
                || ' FROM ' || p_table_schema || '.' || p_table_name1 || ' t1'
                || ' FULL JOIN ' || p_table_schema || '.' || p_table_name2 || ' t2 ON t1.' || p_id_column || '=t2.' || p_id_column
                || ' WHERE (' || v_sql_where || ')';
    RAISE NOTICE 'v_sql: %', v_sql;

    RETURN QUERY EXECUTE v_sql;
END
$$
;

/*
SELECT *
FROM db_compare_tables(
        p_table_schema := 'backup',
        p_table_name1 := 'tmp_fa_old',
        p_table_name2 := 'tmp_fa',
        p_id_column := 'id'
    )
;
 */