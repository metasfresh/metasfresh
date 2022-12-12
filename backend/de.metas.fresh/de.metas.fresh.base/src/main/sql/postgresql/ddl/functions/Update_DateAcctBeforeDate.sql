DROP FUNCTION IF EXISTS update_dateacctbeforedate(date)
;

CREATE OR REPLACE FUNCTION Update_DateAcctBeforeDate(p_DateAcct date)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_table text;
    BKP     text;
BEGIN
    BKP = 'backup.BKP_Fact_Acct_' || TO_CHAR(CURRENT_DATE, 'yyyy_mm_dd');
    EXECUTE FORMAT('DROP TABLE IF EXISTS %s', BKP);
    EXECUTE FORMAT('CREATE TABLE %s AS SELECT * FROM Fact_Acct', BKP);
    UPDATE Fact_Acct
    SET dateacct = p_DateAcct
    WHERE dateacct::date <= p_DateAcct;
    FOR v_table IN
        SELECT tablename
        FROM ad_table
        WHERE ad_table_id IN (SELECT DISTINCT ad_table_id
                              FROM fact_acct
                              WHERE dateacct::date <= p_DateAcct)
        LOOP
            BKP = 'backup.BKP_' || v_table || '_' || TO_CHAR(CURRENT_DATE, 'yyyy_mm_dd');
            EXECUTE FORMAT('DROP TABLE IF EXISTS %s', BKP);
            EXECUTE FORMAT('CREATE TABLE %s AS SELECT * FROM %s', BKP, v_table);
            EXECUTE FORMAT('UPDATE %s SET DateAcct = %L WHERE DateAcct::date <= %L', v_table, TO_CHAR(p_DateAcct, 'yyyy-mm-dd'), TO_CHAR(p_DateAcct, 'yyyy-mm-dd'));
        END LOOP;
END
$$
;

COMMENT ON FUNCTION update_dateacctbeforedate(date) IS 'generic function to update the Fact_Acct.DateAcct before date:
1. Update all Fact_Acct for records with DateAcct < p_DateAcct and change the DateAcct = p_DateAcct.
2. Update the DateAcct for the correspondents documents.
3. Make a backup of the adjusted tables before.'
;

ALTER FUNCTION update_dateacctbeforedate(date) OWNER TO metasfresh
;