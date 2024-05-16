 -- Copy accounting records from C_AcctSchema_ID=1000000 to C_AcctSchema_ID=540003
DO
$$
    DECLARE
        p_From_AcctSchema_ID numeric = 1000000;
        p_To_AcctSchema_ID   numeric = 540003;
        p_CreatedBy          numeric = 99;
        --
        tableInfo            record;
        rowcount_deleted     integer;
        rowcount_inserted    integer;
    BEGIN
        FOR tableInfo IN (
            SELECT t.tablename
                 , t.ad_table_id
                 , (SELECT STRING_AGG(c.columnname, ', ')
                    FROM ad_column c
                    WHERE c.ad_table_id = t.ad_table_id
                      AND c.isactive = 'Y'
                      AND c.iskey = 'N'
                      AND c.columnname NOT IN ('C_AcctSchema_ID', 'Created', 'CreatedBy', 'Updated', 'UpdatedBy')
            ) AS columns_to_copy
            FROM ad_table t
            WHERE t.tablename LIKE '%\_Acct' ESCAPE '\'
                AND t.tablename NOT IN ('Fact_Acct')
                AND t.isview = 'N'
                AND t.isactive = 'Y'
                AND EXISTS(SELECT 1 FROM ad_column c WHERE c.ad_table_id = t.ad_table_id AND c.columnname = 'C_AcctSchema_ID')
            ORDER BY t.tablename)
            LOOP
                -- RAISE NOTICE 'TABLE %: %', tableInfo.tablename, tableInfo.columns_to_copy;
                EXECUTE 'DELETE FROM ' || tableInfo.tablename || ' WHERE C_AcctSchema_ID=' || p_To_AcctSchema_ID;
                GET DIAGNOSTICS rowcount_deleted = ROW_COUNT;
                EXECUTE 'INSERT INTO ' || tableInfo.tablename
                            || ' (C_AcctSchema_ID, Created, CreatedBy, Updated, UpdatedBy, ' || tableInfo.columns_to_copy || ') '
                            || ' SELECT '
                            || p_To_AcctSchema_ID || ', now(), ' || p_CreatedBy || ', now(), ' || p_CreatedBy || ', ' || tableInfo.columns_to_copy
                            || ' FROM ' || tableInfo.tablename
                            || ' WHERE C_AcctSchema_ID=' || p_From_AcctSchema_ID;
                GET DIAGNOSTICS rowcount_inserted = ROW_COUNT;
                RAISE NOTICE 'Table %: % rows inserted, % rows deleted', tableInfo.tablename, rowcount_inserted, rowcount_deleted;
            END LOOP;
    END;
$$
;
