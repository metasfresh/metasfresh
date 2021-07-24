
--## Function to scramble all string columns of a given table
--..unless they are flagged with personalDataCategory = NP (not personal)

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
    v_columnIsString         boolean;
    v_columnShallBeScrambled boolean;
    v_columnsToScrambleCnt   INT;
    v_scramble_update_stmt   text;
    v_lastTableRowCount      bigint;
    v_updateStartTime        timestamp;
BEGIN
    v_columnsToScrambleCnt = 0;
    v_scramble_update_stmt = 'UPDATE ' || p_tableName || ' SET ';
    FOR v_columnName, v_columnShallBeScrambled, v_columnIsString IN
        SELECT c.ColumnName,
               TRIM(COALESCE(c.columnsql, '')) = '' AND COALESCE(c.personaldatacategory, '') != 'NP', /*scramble every text column that is not *explicity* marked as not-personal */
               r.Name IN ('Memo', 'String', 'Text', 'Text Long')
        FROM ad_column c
                 JOIN ad_reference r ON c.ad_reference_id = r.ad_reference_id
        WHERE ad_table_id = get_table_id(p_tableName)
        LOOP
            IF v_columnIsString AND v_columnShallBeScrambled
            THEN
                IF v_columnsToScrambleCnt > 0
                THEN
                    v_scramble_update_stmt = v_scramble_update_stmt || ', ';
                END IF;
                v_scramble_update_stmt = v_scramble_update_stmt || v_columnName || ' = ' || 'ops.scramble_string(' || v_columnName || ')';
                v_columnsToScrambleCnt = v_columnsToScrambleCnt + 1;
            END IF;
        END LOOP;
    RAISE NOTICE 'Number of columns to scramble: %', v_columnsToScrambleCnt;
    IF v_columnsToScrambleCnt > 0
    THEN
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
    IS 'Uses the function scramble_string to scramble all string-columns of the given table, unless they are marked with PersonalDataCategory=NP (not-personal).
If called with p_dryRun := true, then the corresponding update statement is just constructed but not executed.';
