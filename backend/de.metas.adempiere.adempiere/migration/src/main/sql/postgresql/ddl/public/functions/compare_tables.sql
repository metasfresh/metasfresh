DROP FUNCTION IF EXISTS compare_tables(
    first_tablename      TEXT,
    second_tablename     TEXT,
    join_column_names    TEXT[],
    exclude_column_names TEXT[]
)
;

CREATE OR REPLACE FUNCTION compare_tables(
    first_tablename      TEXT,
    second_tablename     TEXT,
    join_column_names    TEXT[],
    exclude_column_names TEXT[] = ARRAY ['created', 'createdby', 'updated', 'updatedby']
)
    RETURNS TABLE
            (
                diff_type    TEXT,  -- Type of difference: 'ONLY_IN_FIRST', 'ONLY_IN_SECOND', 'DIFFERENT', 'IDENTICAL'
                join_id      JSONB, -- JSON object of join column values
                column_name  TEXT,  -- The column name that differs
                first_value  TEXT,  -- Value in the first table
                second_value TEXT   -- Value in the second table
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    diff_query             TEXT;
    join_condition         TEXT;
    join_columns           TEXT;

    -- Variables for schema and table name extraction
    first_table_schema     TEXT := NULL;
    first_table_name       TEXT;
    second_table_schema    TEXT := NULL;
    second_table_name      TEXT;

    -- Converted exclude_column_names list for dynamic SQL
    exclude_columns_filter TEXT := '';
BEGIN
    -- Check and split first_tablename into schema and table
    IF POSITION('.' IN first_tablename) > 0 THEN
        first_table_schema := LOWER(SPLIT_PART(first_tablename, '.', 1));
        first_table_name := LOWER(SPLIT_PART(first_tablename, '.', 2));
    ELSE
        first_table_schema := 'public'; -- Default to the 'public' schema
        first_table_name := LOWER(first_tablename);
    END IF;

    -- Check and split second_tablename into schema and table
    IF POSITION('.' IN second_tablename) > 0 THEN
        second_table_schema := LOWER(SPLIT_PART(second_tablename, '.', 1));
        second_table_name := LOWER(SPLIT_PART(second_tablename, '.', 2));
    ELSE
        second_table_schema := 'public'; -- Default to the 'public' schema
        second_table_name := LOWER(second_tablename);
    END IF;

    -- Construct the JOIN condition as a concatenated AND clause
    join_condition := ARRAY_TO_STRING(
            ARRAY(
                    SELECT FORMAT('f.%I = s.%I', LOWER(col), LOWER(col))
                    FROM UNNEST(join_column_names) AS col
            ),
            ' AND '
                      );

    -- Construct JSONB object for join column values
    join_columns := ARRAY_TO_STRING(
            ARRAY(
                    SELECT FORMAT(' ''%s'', f.%I', LOWER(col), LOWER(col))
                    FROM UNNEST(join_column_names) AS col
            ),
            ', '
                    );

    -- RAISE NOTICE 'join_columns: %', join_columns;

    -- Prepare the "exclude_column_names" filter for the query
    IF exclude_column_names IS NOT NULL THEN
        exclude_columns_filter := ' AND column_name NOT IN (''' || ARRAY_TO_STRING(exclude_column_names, ''', ''') || ''')';
    END IF;

    -- Construct the dynamic query
    diff_query := FORMAT($f$
        WITH 
        first_table AS (SELECT * FROM /* first_table_schema and name */ %I.%I),
        second_table AS (SELECT * FROM /* first_table_schema and name */ %I.%I),
        joined_data AS (
            SELECT
                JSONB_BUILD_OBJECT(/* join_columns */ %s) AS join_id,
                CASE
                    WHEN f IS NULL THEN 'ONLY_IN_SECOND'
                    WHEN s IS NULL THEN 'ONLY_IN_FIRST'
                    WHEN ROW_TO_JSON(f)::JSONB IS DISTINCT FROM ROW_TO_JSON(s)::JSONB THEN 'DIFFERENT'
                    ELSE 'IDENTICAL'
                END AS diff_type,
                ROW_TO_JSON(f)::JSONB AS first_row,
                ROW_TO_JSON(s)::JSONB AS second_row
            FROM first_table f
            FULL OUTER JOIN second_table s ON (%s /* join_columns */)
        ),
        column_diff AS (
            SELECT 
                jd.diff_type,
                jd.join_id,
                col.column_name,
                CASE
                    WHEN col.data_type IN ('numeric', 'decimal', 'real', 'double precision') THEN
                        (jd.first_row ->> col.column_name)::NUMERIC::TEXT
                    ELSE
                        jd.first_row ->> col.column_name
                END AS first_value,
                CASE
                    WHEN col.data_type IN ('numeric', 'decimal', 'real', 'double precision') THEN
                        (jd.second_row ->> col.column_name)::NUMERIC::TEXT
                    ELSE
                        jd.second_row ->> col.column_name
                END AS second_value
            FROM
                joined_data jd,
                LATERAL (
                    SELECT column_name, data_type
                    FROM information_schema.columns
                    WHERE table_schema = %L
                      AND table_name = %L
                      %s /* exclude_columns_filter */
                ) col
            WHERE
                jd.diff_type = 'DIFFERENT'
                AND (
                    (col.data_type IN ('numeric', 'decimal', 'real', 'double precision') 
                        AND (jd.first_row ->> col.column_name)::NUMERIC IS DISTINCT FROM (jd.second_row ->> col.column_name)::NUMERIC)
                    OR
                    (col.data_type NOT IN ('numeric', 'decimal', 'real', 'double precision') 
                        AND jd.first_row ->> col.column_name IS DISTINCT FROM jd.second_row ->> col.column_name)
                )
        )
        SELECT
            diff_type,
            join_id,
            column_name::TEXT,
            first_value,
            second_value
        FROM column_diff
        UNION ALL
        SELECT
            diff_type,
            join_id,
            NULL AS column_name,
            NULL AS first_value,
            NULL AS second_value
        FROM joined_data
        WHERE diff_type IN ('ONLY_IN_FIRST', 'ONLY_IN_SECOND');
    $f$,
                         first_table_schema, first_table_name,
                         second_table_schema, second_table_name,
                         join_columns,
                         join_condition,
                         first_table_schema, first_table_name,
                         exclude_columns_filter -- Inject the exclude filter dynamically
                  );

    -- Raise Notice to log the generated dynamic query
    RAISE NOTICE 'SQL: %', diff_query;

    -- Execute the dynamic query
    RETURN QUERY EXECUTE diff_query;
END;
$$
;

-- e.g.
/* 

SELECT *
FROM compare_tables(
        first_tablename =>'backup.Fact_Acct_Summary_bkp_20250404_080401_866_before_rebuildall',
        second_tablename => 'backup.Fact_Acct_Summary_bkp_20250404_080401_866_after_rebuildall',
        join_column_names => ARRAY ['ad_client_id', 'c_period_id', 'dateacct', 'c_acctschema_id', 'postingtype', 'account_id', 'ad_org_id'],
     
     )
;

 */