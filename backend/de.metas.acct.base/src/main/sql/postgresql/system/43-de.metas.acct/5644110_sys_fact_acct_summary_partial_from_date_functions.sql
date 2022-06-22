DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_summary_partial_compute_from_date(
    p_StartDateAcct timestamp
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_summary_partial_compute_from_date(
    p_StartDateAcct timestamp
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_Year     integer;
    v_rowcount numeric;
BEGIN
    RAISE NOTICE 'fact_acct_summary_partial_update_from_date: p_StartDateAcct=%', p_StartDateAcct;


    RAISE NOTICE 'Computing aggregated amounts before %...', p_StartDateAcct;
    DROP TABLE IF EXISTS TMP_Fact_Acct_Summary_FromDate_previous_amounts;
    CREATE TEMPORARY TABLE TMP_Fact_Acct_Summary_FromDate_previous_amounts AS
    SELECT fa.AD_Client_ID
         , fa.AD_Org_ID
         , fa.Account_ID
         , fa.C_AcctSchema_ID
         , fa.PostingType
         -- Aggregated amounts: (beginning) to Date
         , SUM(AmtAcctDr) AS AmtAcctDr
         , SUM(AmtAcctCr) AS AmtAcctCr
         , SUM(Qty)       AS Qty
    FROM fact_acct fa
    WHERE TRUE
      AND fa.dateacct < p_StartDateAcct
    GROUP BY fa.AD_Client_ID
           , fa.AD_Org_ID
           , fa.Account_ID
           , fa.C_AcctSchema_ID
           , fa.PostingType;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Prepared % rows with aggregated amounts before %', v_rowcount, p_StartDateAcct;
    CREATE UNIQUE INDEX ON TMP_Fact_Acct_Summary_FromDate_previous_amounts (AD_Client_ID, AD_Org_ID, Account_ID, C_AcctSchema_ID, PostingType);


    v_Year := EXTRACT(YEAR FROM p_StartDateAcct);
    RAISE NOTICE 'Computing aggregated amounts YTD from year % until % ...', v_Year, p_StartDateAcct;
    DROP TABLE IF EXISTS TMP_Fact_Acct_Summary_FromDate_previous_amounts_ytd;
    CREATE TEMPORARY TABLE TMP_Fact_Acct_Summary_FromDate_previous_amounts_ytd AS
    SELECT fa.AD_Client_ID
         , fa.AD_Org_ID
         , fa.Account_ID
         , fa.C_AcctSchema_ID
         , fa.PostingType
         , p.c_year_id
         , SUM(AmtAcctDr) AS AmtAcctDr_YTD
         , SUM(amtacctcr) AS AmtAcctCr_YTD
    FROM fact_acct fa
             INNER JOIN c_period p ON p.c_period_id = fa.c_period_id
             INNER JOIN c_year y ON y.c_year_id = p.c_year_id
    WHERE TRUE
      AND y.fiscalyear::integer = v_Year
      AND fa.dateacct < p_StartDateAcct
    GROUP BY fa.AD_Client_ID
           , fa.AD_Org_ID
           , fa.Account_ID
           , fa.C_AcctSchema_ID
           , fa.PostingType
           , p.c_year_id;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE ' => Prepared % rows', v_rowcount;
    CREATE UNIQUE INDEX ON TMP_Fact_Acct_Summary_FromDate_previous_amounts_ytd (AD_Client_ID, AD_Org_ID, Account_ID, C_AcctSchema_ID, PostingType, c_year_id);


    RAISE NOTICE 'Aggregating Fact_Acct starting from %...', p_StartDateAcct;
    DROP TABLE IF EXISTS TMP_Fact_Acct_PerDateAcct;
    CREATE TEMPORARY TABLE TMP_Fact_Acct_PerDateAcct AS
    SELECT fa.AD_Client_ID
         , fa.ad_org_id
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
    FROM Fact_Acct fa
             LEFT OUTER JOIN C_Period p ON (p.C_Period_ID = fa.C_Period_ID)
    WHERE fa.dateacct >= p_StartDateAcct
    GROUP BY fa.AD_Client_ID
           , p.C_Period_ID
           , fa.DateAcct
           , fa.C_AcctSchema_ID
           , fa.PostingType
           , fa.Account_ID
           , fa.ad_org_id;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Aggregated % rows from Fact_Acct starting from %', v_rowcount, p_StartDateAcct;
    CREATE INDEX ON TMP_Fact_Acct_PerDateAcct (AD_Client_ID, ad_org_id, C_AcctSchema_ID, PostingType, Account_ID);
    -- CREATE unique INDEX ON TMP_Fact_Acct_PerDateAcct (AD_Client_ID, ad_org_id, C_AcctSchema_ID, PostingType, Account_ID, dateacct);


    RAISE NOTICE 'Preparing TMP_Fact_Acct_Summary_FromDate...';
    DROP TABLE IF EXISTS TMP_Fact_Acct_Summary_FromDate;
    CREATE TABLE TMP_Fact_Acct_Summary_FromDate AS
    SELECT * FROM Fact_Acct_Summary LIMIT 0;
    INSERT INTO TMP_Fact_Acct_Summary_FromDate
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
    CREATE UNIQUE INDEX ON TMP_Fact_Acct_Summary_FromDate (ad_client_id, ad_org_id, c_acctschema_id, postingtype, account_id, dateacct);


    RAISE NOTICE 'Adding previous amounts to TMP_Fact_Acct_Summary_FromDate...';
    UPDATE TMP_Fact_Acct_Summary_FromDate fas
    SET --
        amtacctdr = fas.amtacctdr + prev.AmtAcctDr,
        amtacctcr = fas.amtacctcr + prev.AmtAcctCr
        --
    FROM TMP_Fact_Acct_Summary_FromDate_previous_amounts prev
    WHERE prev.AD_Client_ID = fas.ad_client_id
      AND prev.AD_Org_ID = fas.ad_org_id
      AND prev.Account_ID = fas.account_id
      AND prev.C_AcctSchema_ID = fas.c_acctschema_id
      AND prev.PostingType = fas.postingtype;


    RAISE NOTICE 'Adding previous YTD amounts to TMP_Fact_Acct_Summary_FromDate...';
    UPDATE TMP_Fact_Acct_Summary_FromDate fas
    SET --
        amtacctdr_ytd = fas.amtacctdr_ytd + prev.amtacctdr_ytd,
        amtacctcr_ytd = fas.amtacctcr_ytd + prev.amtacctcr_ytd
        --
    FROM TMP_Fact_Acct_Summary_FromDate_previous_amounts_ytd prev
    WHERE prev.AD_Client_ID = fas.ad_client_id
      AND prev.AD_Org_ID = fas.ad_org_id
      AND prev.Account_ID = fas.account_id
      AND prev.C_AcctSchema_ID = fas.c_acctschema_id
      AND prev.PostingType = fas.postingtype
      AND prev.c_year_id = fas.c_year_id;


    --
    --
    RAISE NOTICE 'DONE. TMP_Fact_Acct_Summary_FromDate is prepared starting from %', p_StartDateAcct;
END;
$$
;

--
--
--
--
--
--
--
--

DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_summary_partial_compare_from_date(
    p_StartDateAcct timestamp,
    p_ComputeFirst  char(1)
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_summary_partial_compare_from_date(
    p_StartDateAcct timestamp,
    p_ComputeFirst  char(1) = 'N'
)
    RETURNS TABLE
            (
                account      varchar,
                dateacct     date,
                comp_DR      numeric,
                comp_CR      numeric,
                comp_dr_ytd  numeric,
                comp_cr_ytd  numeric,
                --
                DR           numeric,
                CR           numeric,
                dr_ytd       numeric,
                cr_ytd       numeric,
                diff         numeric,
                ad_client_id numeric(10),
                ad_org_id    numeric(10),
                postingtype  char(1),
                is_diferent  boolean
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF (p_ComputeFirst = 'Y') THEN
        RAISE NOTICE 'Computing TMP_Fact_Acct_Summary_FromDate...';
        PERFORM de_metas_acct.fact_acct_summary_partial_compute_from_date(p_StartDateAcct);
    ELSE
        RAISE NOTICE 'Assuming TMP_Fact_Acct_Summary_FromDate is already computed';
    END IF;

    RETURN QUERY
        SELECT (SELECT ev.value || '_' || ev.name FROM c_elementvalue ev WHERE ev.c_elementvalue_id = COALESCE(comp.account_id, fas.account_id))::varchar AS account,
               COALESCE(comp.dateacct, fas.dateacct)::date                                                                                                AS dateacct,
               comp.amtacctdr                                                                                                                             AS comp_DR,
               comp.amtacctcr                                                                                                                             AS comp_CR,
               comp.amtacctdr_ytd                                                                                                                         AS comp_dr_ytd,
               comp.amtacctcr_ytd                                                                                                                         AS comp_cr_ytd,
               --
               fas.amtacctdr                                                                                                                              AS DR,
               fas.amtacctcr                                                                                                                              AS CR,
               fas.amtacctdr_ytd                                                                                                                          AS DR_ytd,
               fas.amtacctcr_ytd                                                                                                                          AS CR_ytd,
               COALESCE(comp.amtacctdr - comp.amtacctcr, 0) - COALESCE(fas.amtacctdr - fas.amtacctcr, 0)                                                  AS diff,
               COALESCE(comp.ad_client_id, fas.ad_client_id)                                                                                              AS ad_client_id,
               COALESCE(comp.ad_org_id, fas.ad_org_id)                                                                                                    AS ad_org_id,
               COALESCE(comp.postingtype, fas.postingtype)                                                                                                AS postingtype,
               (
                       comp.amtacctdr IS DISTINCT FROM fas.amtacctdr
                       OR comp.amtacctcr IS DISTINCT FROM fas.amtacctcr
                       OR comp.amtacctdr_ytd IS DISTINCT FROM fas.amtacctdr_ytd
                       OR comp.amtacctcr_ytd IS DISTINCT FROM fas.amtacctcr_ytd
                   )                                                                                                                                      AS is_different
        FROM TMP_Fact_Acct_Summary_FromDate comp
                 FULL JOIN fact_acct_summary fas ON (
                    fas.AD_Client_ID = comp.ad_client_id
                AND fas.AD_Org_ID = comp.ad_org_id
                AND fas.Account_ID = comp.account_id
                AND fas.C_AcctSchema_ID = comp.c_acctschema_id
                AND fas.PostingType = comp.postingtype
                AND fas.dateacct = comp.dateacct
            )
        WHERE TRUE
          AND COALESCE(comp.dateacct, fas.dateacct) >= p_StartDateAcct;
END;
$$
;

--
--
--
--
--
--
--
--

DROP FUNCTION IF EXISTS de_metas_acct.fact_acct_summary_partial_update_from_date(
    p_StartDateAcct timestamp,
    p_ComputeFirst  char(1),
    p_DryRun        char(1)
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.fact_acct_summary_partial_update_from_date(
    p_StartDateAcct timestamp,
    p_ComputeFirst  char(1),
    p_DryRun        char(1)
)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_rowcount integer;
BEGIN
    RAISE NOTICE 'fact_acct_summary_partial_update_from_date: Starting with p_StartDateAcct=%, p_ComputeFirst=%, p_DryRun=%', p_StartDateAcct::date, p_ComputeFirst, p_DryRun;

    --
    RAISE NOTICE 'Locking fact_acct, fact_acct_log, fact_acct_summary';
    LOCK TABLE fact_acct IN EXCLUSIVE MODE;
    LOCK TABLE fact_acct_log IN EXCLUSIVE MODE;
    LOCK TABLE fact_acct_summary IN EXCLUSIVE MODE;

    --
    -- Check/compute TMP_Fact_Acct_Summary_FromDate if needed
    IF (p_ComputeFirst = 'Y') THEN
        RAISE NOTICE 'Computing TMP_Fact_Acct_Summary_FromDate...';
        PERFORM de_metas_acct.fact_acct_summary_partial_compute_from_date(p_StartDateAcct);
    ELSE
        IF (p_DryRun = 'N') THEN
            RAISE EXCEPTION 'p_ComputeFirst must be Y when p_DryRun=N';
        END IF;

        RAISE NOTICE 'Assuming TMP_Fact_Acct_Summary_FromDate is already computed';
        SELECT COUNT(1) INTO v_rowcount FROM TMP_Fact_Acct_Summary_FromDate;
        RAISE NOTICE 'Found % rows prepared', v_rowcount;
    END IF;

    --
    --
    --
    -- Delete from Log
    DELETE
    FROM fact_acct_log l
    WHERE TRUE
      AND l.dateacct >= p_StartDateAcct;
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_summary_partial_update_from_date: Removed % fact_acct_log rows', v_rowcount;

    --
    -- Remove old records from fact_acct_summary
    DELETE
    FROM fact_acct_summary fas
    WHERE TRUE
      AND fas.dateacct >= p_StartDateAcct;
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_summary_partial_update_from_date: Removed % fact_acct_summary rows', v_rowcount;

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
    FROM TMP_Fact_Acct_Summary_FromDate fas_comp
    WHERE TRUE
    -- AND fas_comp.dateacct >= p_StartDateAcct -- not needed, it's redundant
    ;
    --
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'fact_acct_summary_partial_update_from_date: Inserted % fact_acct_summary rows', v_rowcount;

    --
    --
    IF (p_DryRun = 'Y') THEN
        RAISE EXCEPTION 'ROLLBACK because p_DryRun=Y';
    END IF;
END;
$$
;






