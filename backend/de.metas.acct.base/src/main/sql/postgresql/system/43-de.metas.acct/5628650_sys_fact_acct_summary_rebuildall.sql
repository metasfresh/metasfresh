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
































DROP FUNCTION IF EXISTS de_metas_acct.Fact_Acct_Summary_RebuildAll()
;

CREATE OR REPLACE FUNCTION de_metas_acct.Fact_Acct_Summary_RebuildAll()
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_rowcount numeric;
BEGIN
    PERFORM backup_table('Fact_Acct_Summary');
    PERFORM backup_table('Fact_Acct_Log');

    RAISE NOTICE 'Preparing TMP_Fact_Acct...';
    DROP TABLE IF EXISTS TMP_Fact_Acct;
    CREATE TEMPORARY TABLE TMP_Fact_Acct AS
    SELECT * FROM Fact_Acct;
    CREATE INDEX ON TMP_Fact_Acct (AD_Client_ID, C_Period_ID, DateAcct, C_AcctSchema_ID, PostingType, Account_ID);

    RAISE NOTICE 'Preparing TMP_Fact_Acct_PerDateAcct...';
    DROP TABLE IF EXISTS TMP_Fact_Acct_PerDateAcct;
    CREATE TEMPORARY TABLE TMP_Fact_Acct_PerDateAcct AS
    SELECT fa.AD_Client_ID
         , fa.Account_ID
         , fa.C_AcctSchema_ID
         , fa.PostingType
         , p.C_Period_ID
         , p.C_Year_ID
         , fa.DateAcct
         -- Aggregated amounts
         , COALESCE(SUM(AmtAcctDr), 0) AS AmtAcctDr
         , COALESCE(SUM(AmtAcctCr), 0) AS AmtAcctCr
         , COALESCE(SUM(Qty), 0)       AS Qty
         , fa.ad_org_id
    FROM TMP_Fact_Acct fa
             LEFT OUTER JOIN C_Period p ON (p.C_Period_ID = fa.C_Period_ID)
    GROUP BY fa.AD_Client_ID
           , p.C_Period_ID
           , fa.DateAcct
           , fa.C_AcctSchema_ID
           , fa.PostingType
           , fa.Account_ID
           , fa.ad_org_id;


    RAISE NOTICE 'Preparing TMP_Fact_Acct_Summary...';
    DROP TABLE IF EXISTS TMP_Fact_Acct_Summary;
    CREATE TABLE TMP_Fact_Acct_Summary AS
    SELECT * FROM Fact_Acct_Summary LIMIT 0;
    INSERT INTO TMP_Fact_Acct_Summary
    ( AD_Client_ID
    , AD_Org_ID
    , Account_ID
    , C_AcctSchema_ID
    , PostingType
    , C_Period_ID, C_Year_ID
    , DateAcct
    , PA_ReportCube_ID
        -- Aggregated amounts: (beginning) to Date
    , AmtAcctDr
    , AmtAcctCr
    , Qty
        -- Aggregated amounts: Year to Date
    , AmtAcctDr_YTD
    , AmtAcctCr_YTD
        -- Standard columns
    , Created
    , CreatedBy
    , Updated
    , UpdatedBy
    , IsActive)
    SELECT fa.AD_Client_ID
         , fa.AD_Org_ID
         , fa.Account_ID
         , fa.C_AcctSchema_ID
         , fa.PostingType
         , fa.C_Period_ID
         , fa.C_Year_ID
         , fa.DateAcct
         , NULL                                 AS PA_ReportCube_ID
         -- Aggregated amounts: (beginning) to Date
         , SUM(AmtAcctDr) OVER facts_ToDate     AS AmtAcctDr
         , SUM(AmtAcctCr) OVER facts_ToDate     AS AmtAcctCr
         , SUM(Qty) OVER facts_ToDate           AS Qty
         -- Aggregated amounts: Year to Date
         , SUM(AmtAcctDr) OVER facts_YearToDate AS AmtAcctDr_YTD
         , SUM(AmtAcctCr) OVER facts_YearToDate AS AmtAcctCr_YTD
         -- Standard columns
         , NOW()                                AS Created
         , 0                                    AS CreatedBy
         , NOW()                                AS Updated
         , 0                                    AS UpdatedBy
         , 'Y'                                  AS IsActive
    FROM TMP_Fact_Acct_PerDateAcct fa
        WINDOW
            facts_ToDate AS (PARTITION BY fa.AD_Client_ID, fa.ad_org_id, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID ORDER BY fa.DateAcct)
            , facts_YearToDate AS (PARTITION BY fa.AD_Client_ID, fa.ad_org_id, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Year_ID ORDER BY fa.DateAcct);

    --
    --
    -- WARNING: Perform the actual changes:
    --
    --
    RAISE NOTICE 'Performing the actual changes...';

    DELETE FROM Fact_Acct_Log WHERE 1 = 1; -- make sure Fact_Acct_Summary is not updated again in meantime
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Removed % rows from Fact_Acct_Log', v_rowcount;


    DELETE FROM Fact_Acct_Summary WHERE PA_ReportCube_ID IS NULL;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Removed % rows from Fact_Acct_Summary', v_rowcount;

    INSERT INTO Fact_Acct_Summary SELECT * FROM TMP_Fact_Acct_Summary;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Inserted % rows to Fact_Acct_Summary', v_rowcount;


    --
    -- Recalculate Fact_Acct_EndingBalance
    --
    PERFORM de_metas_acct.Fact_Acct_EndingBalance_RebuildAll();
END
$$
;

