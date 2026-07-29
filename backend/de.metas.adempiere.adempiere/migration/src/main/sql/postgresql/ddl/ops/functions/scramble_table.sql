
--## Function to scramble all string columns of a given table
--..where PersonalDataCategory is P (personal) or SP (sensitive personal)

CREATE OR REPLACE FUNCTION ops.scramble_table(
    p_tableName character varying,
    p_dryRun    boolean = TRUE)
    RETURNS void
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE
AS
$BODY$
DECLARE
    v_columnName             text;
    v_columnsToScrambleCnt   INT;
    v_scramble_update_stmt   text;
    v_where_clause           text;
    v_lastTableRowCount      bigint;
    v_updateStartTime        timestamp;
BEGIN
    v_columnsToScrambleCnt = 0;
    v_scramble_update_stmt = 'UPDATE ' || p_tableName || ' SET ';
    v_where_clause = '';

    FOR v_columnName IN
        SELECT c.ColumnName
        FROM ad_column c
                 JOIN ad_reference r ON c.ad_reference_id = r.ad_reference_id
                 JOIN ad_table t ON c.ad_table_id = t.ad_table_id
        WHERE c.ad_table_id = get_table_id(p_tableName)
          AND r.Name IN ('Memo', 'String', 'Text', 'Text Long')
          AND TRIM(COALESCE(c.columnsql, '')) = ''
          AND COALESCE(c.personaldatacategory, t.personaldatacategory) IN ('P', 'SP')
        LOOP
            IF v_columnsToScrambleCnt > 0
            THEN
                v_scramble_update_stmt = v_scramble_update_stmt || ', ';
                v_where_clause = v_where_clause || ' OR ';
            END IF;
            v_scramble_update_stmt = v_scramble_update_stmt
                || v_columnName || ' = ops.scramble_string(' || v_columnName || ')';
            v_where_clause = v_where_clause || v_columnName || ' IS NOT NULL';
            v_columnsToScrambleCnt = v_columnsToScrambleCnt + 1;
        END LOOP;

    RAISE NOTICE 'Number of columns to scramble: %', v_columnsToScrambleCnt;

    IF v_columnsToScrambleCnt > 0
    THEN
        -- Skip rows where all personal columns are NULL (avoids unnecessary UPDATE + WAL)
        v_scramble_update_stmt = v_scramble_update_stmt || ' WHERE ' || v_where_clause;

        IF p_dryRun THEN
            RAISE NOTICE 'DRY-RUN - scramble UPDATE statement = %', v_scramble_update_stmt;
        ELSE
            v_updateStartTime = clock_timestamp();
            EXECUTE v_scramble_update_stmt;
            GET DIAGNOSTICS v_lastTableRowCount = ROW_COUNT;
            RAISE NOTICE '% TableName = % - updated % rows in %', clock_timestamp()
                , p_tableName, v_lastTableRowCount, clock_timestamp() - v_updateStartTime;
        END IF;
    END IF;
END;
$BODY$
;
COMMENT ON FUNCTION ops.scramble_table(character varying, boolean)
    IS 'Uses scramble_string (translate-based) to scramble all string columns of the given table
where PersonalDataCategory is P (personal) or SP (sensitive personal).
Also respects table-level PersonalDataCategory as fallback for unclassified columns.
Skips rows where all personal columns are NULL to avoid unnecessary I/O.
If called with p_dryRun := true, the UPDATE statement is constructed but not executed.';
