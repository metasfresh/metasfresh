DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_summary_partial_compute(
    p_Account_ID numeric
)
;

DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_summary_partial_compare(
    p_Account_ID numeric
)
;

DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_summary_partial_update(
    p_Account_ID    numeric,
    p_StartDateAcct timestamp,
    p_DryRun        char(1)
)
;

--
--
--
--
--

CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_summary_partial_compute(
    p_Account_ID numeric
)
    RETURNS TABLE
            (
                AD_Client_ID    numeric(10),
                AD_Org_ID       numeric(10),
                Account_ID      numeric(10),
                C_AcctSchema_ID numeric(10),
                PostingType     char(1),
                C_Period_ID     numeric(10),
                C_Year_ID       numeric(10),
                DateAcct        timestamp,
                -- PA_ReportCube_ID
                --
                -- Aggregated amounts: (beginning) to Date
                AmtAcctDr       numeric,
                AmtAcctCr       numeric,
                Qty             numeric,
                --
                -- Aggregated amounts: Year to Date
                AmtAcctDr_YTD   numeric,
                AmtAcctCr_YTD   numeric,
                --
                -- Standard columns
                Created         timestamp WITH TIME ZONE,
                CreatedBy       numeric(10),
                Updated         timestamp WITH TIME ZONE,
                UpdatedBy       numeric(10),
                IsActive        char(1)
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_rowcount integer;
BEGIN
    RAISE NOTICE 'fact_acct_summary_partial_compute: starting for p_Account_ID=%', p_Account_ID;
    --
    --
    DROP TABLE IF EXISTS TMP_Fact_Acct;
    --
    CREATE TEMPORARY TABLE TMP_Fact_Acct AS
    SELECT *
    FROM Fact_Acct fa
    WHERE fa.account_id = p_Account_ID;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_summary_partial_compute: TMP_Fact_Acct created (% rows)', v_rowcount;
    --
    CREATE INDEX ON TMP_Fact_Acct (AD_Client_ID, C_Period_ID, DateAcct, C_AcctSchema_ID, PostingType, Account_ID);
    RAISE NOTICE 'fact_acct_summary_partial_compute: TMP_Fact_Acct indexed';

    --
    --
    DROP TABLE IF EXISTS TMP_Fact_Acct_PerDateAcct;
    --
    CREATE TEMPORARY TABLE TMP_Fact_Acct_PerDateAcct AS
    SELECT fa.AD_Client_ID
         , fa.Account_ID
         , fa.C_AcctSchema_ID
         , fa.PostingType
         , p.C_Period_ID
         , p.C_Year_ID
         , fa.DateAcct
         -- Aggregated amounts
         , COALESCE(SUM(fa.AmtAcctDr), 0) AS AmtAcctDr
         , COALESCE(SUM(fa.AmtAcctCr), 0) AS AmtAcctCr
         , COALESCE(SUM(fa.Qty), 0)       AS Qty
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
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_summary_partial_compute: TMP_Fact_Acct_PerDateAcct created (% rows)', v_rowcount;


    RAISE NOTICE 'fact_acct_summary_partial_compute: Returning computed table...';
    RETURN QUERY
        SELECT fa.AD_Client_ID
             , fa.AD_Org_ID
             , fa.Account_ID
             , fa.C_AcctSchema_ID
             , fa.PostingType
             , fa.C_Period_ID
             , fa.C_Year_ID
             , fa.DateAcct
             -- , NULL                                 AS PA_ReportCube_ID
             --
             -- Aggregated amounts: (beginning) to Date
             , SUM(fa.AmtAcctDr) OVER facts_ToDate     AS AmtAcctDr
             , SUM(fa.AmtAcctCr) OVER facts_ToDate     AS AmtAcctCr
             , SUM(fa.Qty) OVER facts_ToDate           AS Qty
             --
             -- Aggregated amounts: Year to Date
             , SUM(fa.AmtAcctDr) OVER facts_YearToDate AS AmtAcctDr_YTD
             , SUM(fa.AmtAcctCr) OVER facts_YearToDate AS AmtAcctCr_YTD
             --
             -- Standard columns
             , NOW()                                   AS Created
             , 0::numeric                              AS CreatedBy
             , NOW()                                   AS Updated
             , 0::numeric                              AS UpdatedBy
             , 'Y'::char(1)                            AS IsActive
        FROM TMP_Fact_Acct_PerDateAcct fa
            WINDOW
                facts_ToDate AS (PARTITION BY fa.AD_Client_ID, fa.ad_org_id, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID ORDER BY fa.DateAcct)
                , facts_YearToDate AS (PARTITION BY fa.AD_Client_ID, fa.ad_org_id, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Year_ID ORDER BY fa.DateAcct);
END;
$$
;

--
--
--
--
--

CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_summary_partial_compare(
    p_Account_ID numeric
)
    RETURNS TABLE
            (
                ad_org_id          numeric(10),
                postingtype        char(1),
                account_id         numeric(10),
                accountno          varchar,
                accountname        varchar,
                dateacct           date,
                --
                comp_amtacctdr     numeric,
                comp_amtacctcr     numeric,
                amtacctdr          numeric,
                amtacctcr          numeric,
                diff               numeric,
                --
                comp_amtacctdr_ytd numeric,
                comp_amtacctcr_ytd numeric,
                amtacctdr_ytd      numeric,
                amtacctcr_ytd      numeric,
                diff_ytd           numeric,
                --
                comp_updated       timestamp WITH TIME ZONE,
                updated            timestamp WITH TIME ZONE
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    DROP TABLE IF EXISTS TMP_Fact_Acct_Summary_Computed;
    --
    CREATE TABLE TMP_Fact_Acct_Summary_Computed AS
    SELECT *
    FROM de_metas_acct.fact_acct_summary_partial_compute(p_Account_ID);
    RAISE NOTICE 'fact_acct_summary_partial_compare: TMP_Fact_Acct_Summary_Computed created';
    --
    CREATE INDEX ON TMP_Fact_Acct_Summary_Computed (account_id, dateacct, ad_org_id, postingtype);
    RAISE NOTICE 'fact_acct_summary_partial_compare: TMP_Fact_Acct_Summary_Computed indexed';

    --
    --
    DROP TABLE IF EXISTS TMP_Fact_Acct_Summary_NOW;
    --
    CREATE TEMPORARY TABLE TMP_Fact_Acct_Summary_NOW AS
    SELECT *
    FROM fact_acct_summary fas
    WHERE fas.account_id = p_Account_ID;
    RAISE NOTICE 'fact_acct_summary_partial_compare: TMP_Fact_Acct_Summary_NOW created';
    --
    CREATE INDEX ON TMP_Fact_Acct_Summary_NOW (account_id, dateacct, ad_org_id, postingtype);
    RAISE NOTICE 'fact_acct_summary_partial_compare: TMP_Fact_Acct_Summary_NOW indexed';

    --
    --
    RAISE NOTICE 'fact_acct_summary_partial_compare: Returning compare result...';
    RETURN QUERY
        SELECT COALESCE(fas_comp.ad_org_id, fas_now.ad_org_id)                                                                                AS ad_org_id,
               COALESCE(fas_comp.postingtype, fas_now.postingtype)                                                                            AS postingtype,
               COALESCE(fas_comp.account_id, fas_now.account_id)                                                                              AS account_id,
               ev.value                                                                                                                       AS accountno,
               ev.name                                                                                                                        AS accountname,
               COALESCE(fas_comp.dateacct, fas_now.dateacct)::date                                                                            AS dateacct,
               --
               fas_comp.amtacctdr                                                                                                             AS comp_amtacctdr,
               fas_comp.amtacctcr                                                                                                             AS comp_amtacctcr,
               fas_now.amtacctdr,
               fas_now.amtacctcr,
               ABS(COALESCE(fas_comp.amtacctdr - fas_comp.amtacctcr, 0) - COALESCE(fas_now.amtacctdr - fas_now.amtacctcr, 0))                 AS diff,
               --
               fas_comp.amtacctdr_ytd                                                                                                         AS comp_amtacctdr_ytd,
               fas_comp.amtacctcr_ytd                                                                                                         AS comp_amtacctcr_ytd,
               fas_now.amtacctdr_ytd,
               fas_now.amtacctcr_ytd,
               ABS(COALESCE(fas_comp.amtacctdr_ytd - fas_comp.amtacctcr_ytd, 0) - COALESCE(fas_now.amtacctdr_ytd - fas_now.amtacctcr_ytd, 0)) AS diff_ytd,
               --
               fas_comp.updated                                                                                                               AS comp_updated,
               fas_now.updated                                                                                                                AS updated
        FROM TMP_Fact_Acct_Summary_Computed fas_comp
                 FULL JOIN TMP_Fact_Acct_Summary_NOW fas_now ON (
                    fas_now.account_id = fas_comp.account_id
                AND fas_now.dateacct = fas_comp.dateacct
                AND fas_now.ad_org_id = fas_comp.ad_org_id
                AND fas_now.postingtype = fas_comp.postingtype)
                 LEFT OUTER JOIN c_elementvalue ev ON ev.c_elementvalue_id = COALESCE(fas_comp.account_id, fas_now.account_id)
        WHERE TRUE
          -- AND COALESCE(fas_comp.ad_org_id, fas_now.ad_org_id) = 1000000
          -- AND (COALESCE(fas_comp.dateacct, fas_now.dateacct) < '2022-01-01')
          AND (FALSE
            OR COALESCE(fas_now.amtacctdr, 0) != COALESCE(fas_comp.amtacctdr, 0)
            OR COALESCE(fas_now.amtacctcr, 0) != COALESCE(fas_comp.amtacctcr, 0)
            OR COALESCE(fas_now.amtacctdr_ytd, 0) != COALESCE(fas_comp.amtacctdr_ytd, 0)
            OR COALESCE(fas_now.amtacctcr_ytd, 0) != COALESCE(fas_comp.amtacctcr_ytd, 0)
            --
            )
        ORDER BY
            --ev.value,
            COALESCE(fas_comp.dateacct, fas_now.dateacct);
END;
$$
;


--
--
--
--
--
CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_summary_partial_update(
    p_Account_ID    numeric,
    p_StartDateAcct timestamp,
    p_DryRun        char(1) DEFAULT 'Y'
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_rowcount integer;
BEGIN
    RAISE NOTICE 'fact_acct_summary_partial_update: Starting with p_Account_ID=%, p_StartDateAcct=%, p_DryRun=%', p_Account_ID, p_StartDateAcct, p_DryRun;

    LOCK TABLE fact_acct IN EXCLUSIVE MODE;
    LOCK TABLE fact_acct_log IN EXCLUSIVE MODE;
    LOCK TABLE fact_acct_summary IN EXCLUSIVE MODE;

    --
    -- Compute summaries for given params
    DROP TABLE IF EXISTS TMP_Fact_Acct_Summary_Computed;
    --
    CREATE TABLE TMP_Fact_Acct_Summary_Computed AS
    SELECT *
    FROM de_metas_acct.fact_acct_summary_partial_compute(p_Account_ID);
    RAISE NOTICE 'fact_acct_summary_partial_update: TMP_Fact_Acct_Summary_Computed created';
    --
    CREATE INDEX ON TMP_Fact_Acct_Summary_Computed (account_id);
    CREATE INDEX ON TMP_Fact_Acct_Summary_Computed (dateacct);
    RAISE NOTICE 'fact_acct_summary_partial_update: TMP_Fact_Acct_Summary_Computed indexed';

    --
    -- Delete from Log
    DELETE
    FROM fact_acct_log l
    WHERE TRUE
      AND l.c_elementvalue_id = p_Account_ID
      AND l.dateacct >= p_StartDateAcct;
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_summary_partial_update: Removed % fact_acct_log rows', v_rowcount;

    --
    -- Remove old records from fact_acct_summary
    DELETE
    FROM fact_acct_summary fas
    WHERE TRUE
      AND fas.account_id = p_Account_ID
      AND fas.dateacct >= p_StartDateAcct;
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_summary_partial_update: Removed % fact_acct_summary rows', v_rowcount;

    --
    -- And replace them with computed summaries
    INSERT INTO fact_acct_summary
    ( AD_Client_ID
    , AD_Org_ID
    , Account_ID
    , C_AcctSchema_ID
    , PostingType
    , C_Period_ID
    , C_Year_ID
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
    SELECT fas_comp.AD_Client_ID
         , fas_comp.AD_Org_ID
         , fas_comp.Account_ID
         , fas_comp.C_AcctSchema_ID
         , fas_comp.PostingType
         , fas_comp.C_Period_ID
         , fas_comp.C_Year_ID
         , fas_comp.DateAcct
         , NULL AS PA_ReportCube_ID
         -- Aggregated amounts: (beginning) to Date
         , fas_comp.AmtAcctDr
         , fas_comp.AmtAcctCr
         , fas_comp.Qty
         -- Aggregated amounts: Year to Date
         , fas_comp.AmtAcctDr_YTD
         , fas_comp.AmtAcctCr_YTD
         -- Standard columns
         , fas_comp.Created
         , fas_comp.CreatedBy
         , fas_comp.Updated
         , fas_comp.UpdatedBy
         , fas_comp.IsActive
    FROM TMP_Fact_Acct_Summary_Computed fas_comp
    WHERE TRUE
      AND fas_comp.account_id = p_Account_ID
      AND fas_comp.dateacct >= p_StartDateAcct;
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_summary_partial_update: Inserted % fact_acct_summary rows', v_rowcount;

    --
    --
    IF (p_DryRun = 'Y') THEN
        RAISE EXCEPTION 'ROLLBACK because p_DryRun=Y';
    END IF;
END;
$$
;



--
--
--
--
--


