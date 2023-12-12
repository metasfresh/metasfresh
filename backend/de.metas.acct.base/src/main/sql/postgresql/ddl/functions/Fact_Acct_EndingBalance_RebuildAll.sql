DROP FUNCTION IF EXISTS de_metas_acct.Fact_Acct_EndingBalance_RebuildAll()
;

CREATE OR REPLACE FUNCTION de_metas_acct.Fact_Acct_EndingBalance_RebuildAll()
    RETURNS text
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    v_rowcount numeric;
BEGIN

    PERFORM backup_table('Fact_Acct_EndingBalance');

    DROP TABLE IF EXISTS TMP_Fact_Acct;
    CREATE TEMPORARY TABLE TMP_Fact_Acct AS
    SELECT * FROM Fact_Acct;
    CREATE INDEX ON TMP_Fact_Acct (AD_Client_ID, AD_Org_ID, C_Period_ID, DateAcct, C_AcctSchema_ID, PostingType, Account_ID);


    DROP TABLE IF EXISTS TMP_Fact_Acct_EndingBalance;
    CREATE TABLE TMP_Fact_Acct_EndingBalance AS
    SELECT * FROM Fact_Acct_EndingBalance LIMIT 0;
    INSERT INTO TMP_Fact_Acct_EndingBalance
    ( fact_acct_id
    , AmtAcctDr_DTD
    , AmtAcctCr_DTD
    , C_AcctSchema_ID
    , Account_ID
    , PostingType
    , DateAcct
    , ad_client_id
    , ad_org_id
    , created
    , createdby
    , isactive
    , updated
    , updatedby)
    SELECT fa.fact_acct_id
         , SUM(AmtAcctDr) OVER facts_previous AS AmtAcctDr_DTD
         , SUM(AmtAcctCr) OVER facts_previous AS AmtAcctCr_DTD
         , fa.C_AcctSchema_ID
         , fa.Account_ID
         , fa.PostingType
         , fa.DateAcct
         , fa.ad_client_id
         , fa.ad_org_id
         , fa.created
         , fa.createdby
         , fa.isactive
         , fa.updated
         , fa.updatedby
    FROM TMP_Fact_Acct fa
        WINDOW facts_previous AS (PARTITION BY fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.DateAcct ORDER BY Fact_Acct_ID);


    --
    --
    -- WARNING: Perform the actual change:
    --
    DELETE FROM Fact_Acct_EndingBalance WHERE 1 = 1;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Removed % rows from Fact_Acct_EndingBalance', v_rowcount;

    INSERT INTO Fact_Acct_EndingBalance SELECT * FROM TMP_Fact_Acct_EndingBalance;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Inserted % rows from Fact_Acct_EndingBalance', v_rowcount;

    RETURN '' || v_rowcount || ' rows inserted into Fact_Acct_EndingBalance';
END;
$BODY$
;

COMMENT ON FUNCTION de_metas_acct.Fact_Acct_EndingBalance_RebuildAll() IS 'Rebuilds Fact_Acct_EndingBalance.'
;

