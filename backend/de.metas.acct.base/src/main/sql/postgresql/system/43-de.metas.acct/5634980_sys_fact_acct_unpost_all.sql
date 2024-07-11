DROP FUNCTION IF EXISTS "de_metas_acct".fact_acct_unpost_all(
    p_CheckPeriodOpen char(1)
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_unpost_all(
    p_CheckPeriodOpen char(1) = 'Y'
)
    RETURNS void
AS
$$
DECLARE
    v_tableInfo RECORD;
    v_count     numeric;
BEGIN
    FOR v_tableInfo IN (SELECT t.tablename, t.tablename || '_ID' AS KeyColumnName
                        FROM ad_table t
                        WHERE t.isactive = 'Y'
                          AND t.isview = 'N'
                          AND t.tablename != 'X_AcctDocTableTemplate'
                          AND EXISTS(SELECT 1 FROM ad_column c WHERE c.ad_table_id = t.ad_table_id AND c.isactive = 'Y' AND c.columnname = 'Posted')
                        ORDER BY t.tablename)
        LOOP
            EXECUTE 'SELECT COUNT(1) FROM ('
                        || '    SELECT "de_metas_acct".fact_acct_unpost('
                        || '         p_tablename :=''' || v_tableInfo.tablename || ''', '
                        || '         p_Record_ID :=d.' || v_tableInfo.KeyColumnName || ', '
                        || '         p_Force := ''Y'','
                        || '         p_CheckPeriodOpen := ''' || p_CheckPeriodOpen || ''' '
                        || '    )'
                        || '    FROM ' || v_tableInfo.tablename || ' d '
                || ') t'
                INTO v_count;

            RAISE NOTICE 'Unposted % documents from %', v_count, v_tableInfo.tablename;
        END LOOP;
END;
$$
    LANGUAGE plpgsql
;


/*
SELECT "de_metas_acct".fact_acct_unpost_all(
               p_CheckPeriodOpen := 'N'
           )
;
 */


