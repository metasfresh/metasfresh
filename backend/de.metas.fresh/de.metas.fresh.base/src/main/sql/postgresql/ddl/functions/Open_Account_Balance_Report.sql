DROP FUNCTION IF EXISTS report.open_account_balance_report(
    p_date               date,
    p_c_acctschema_id    numeric,
    p_ad_org_id          numeric,
    p_account_id         numeric,
    p_c_bpartner_id      numeric,
    p_c_currency_id      numeric,
    p_tolerance          numeric,
    p_show_details       character,
    p_only_unreconciled  character
)
;

CREATE OR REPLACE FUNCTION report.open_account_balance_report(
    p_date               date,
    p_c_acctschema_id    numeric,
    p_ad_org_id          numeric,
    p_account_id         numeric    DEFAULT NULL,
    p_c_bpartner_id      numeric    DEFAULT NULL,
    p_c_currency_id      numeric    DEFAULT NULL,
    p_tolerance          numeric    DEFAULT 0.01,
    p_show_details       character  DEFAULT 'N',
    p_only_unreconciled  character  DEFAULT 'Y'
)
    RETURNS TABLE
            (
                -- -------------------------------------------------------
                -- Level indicator
                -- -------------------------------------------------------
                report_level             text,       -- 'SUMMARY' | 'DETAIL'

                -- -------------------------------------------------------
                -- Mandatory dimensions (both levels)
                -- -------------------------------------------------------
                c_acctschema_id          numeric,
                ad_org_id                numeric,
                account_id               numeric,
                account_value            varchar,
                account_name             varchar,
                c_bpartner_id            numeric,
                c_currency_id            numeric,

                -- -------------------------------------------------------
                -- Summary-level amounts
                -- -------------------------------------------------------
                oi_open_amt              numeric,     -- SUM(OI_OpenAmount)
                oi_open_amt_unreconciled numeric,     -- SUM where IsOpenItemsReconciled='N'
                oldest_open_dateacct     date,        -- MIN(DateAcct) of unreconciled lines

                -- -------------------------------------------------------
                -- Data-quality flags (summary level)
                -- -------------------------------------------------------
                has_open_items           char(1),     -- 'Y' if oi_open_amt_unreconciled > tolerance
                is_reconciliation_error  char(1),     -- 'Y' if gl_balance != oi_open_amt (within tolerance)
                gl_balance_amt           numeric,     -- SUM(AmtAcctDr - AmtAcctCr) — for integrity check
                gl_balance_source_amt    numeric,     -- SUM(AmtSourceDr - AmtSourceCr)
                gl_vs_oi_diff            numeric,     -- gl_balance_amt - oi_open_amt  (must be 0)

                -- -------------------------------------------------------
                -- Detail-level columns (populated only when p_show_details='Y')
                -- -------------------------------------------------------
                fact_acct_id             numeric,
                dateacct                 date,
                documentno               text,
                oi_trxtype               varchar,
                openitemkey              varchar,
                isopenitemsreconciled    char(1),
                amt_acct_dr              numeric,
                amt_acct_cr              numeric,
                amt_source_dr            numeric,
                amt_source_cr            numeric,
                oi_openamount            numeric,
                description              text,

                -- -------------------------------------------------------
                -- Diagnostics
                -- -------------------------------------------------------
                total_fact_lines         bigint,
                open_fact_lines          bigint,
                overall_count            bigint
            )
    LANGUAGE plpgsql
AS
$BODY$
    # VARIABLE_CONFLICT USE_COLUMN
DECLARE
    v_rowcount    numeric;
    v_ad_client_id numeric;
    v_overall     bigint;
BEGIN

    /* ================================================================
       STEP 0 — Resolve AD_Client_ID from the accounting schema.
       All subsequent filters use this to guarantee tenant isolation
       in multi-tenant deployments.
       ================================================================ */
    SELECT s.AD_Client_ID
      INTO v_ad_client_id
      FROM AD_AcctSchema s
     WHERE s.C_AcctSchema_ID = p_c_acctschema_id;

    IF v_ad_client_id IS NULL THEN
        RAISE EXCEPTION 'No AD_AcctSchema found for C_AcctSchema_ID = %', p_c_acctschema_id;
    END IF;

    RAISE NOTICE 'Resolved AD_Client_ID: % for C_AcctSchema_ID: %', v_ad_client_id, p_c_acctschema_id;


    /* ================================================================
       STEP 1 — Resolve open-item accounts.
       Only accounts with C_ElementValue.IsOpenItem = 'Y' participate.
       Scoped to the resolved client for tenant isolation.
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_accounts;
    CREATE TEMPORARY TABLE tmp_oib_accounts ON COMMIT DROP AS
    SELECT
        ev.C_ElementValue_ID   AS account_id,
        ev.Value               AS account_value,
        ev.Name                AS account_name
    FROM C_ElementValue ev
    WHERE ev.IsOpenItem      = 'Y'
      AND ev.IsActive        = 'Y'
      AND ev.AD_Client_ID    = v_ad_client_id
      AND (p_account_id IS NULL OR ev.C_ElementValue_ID = p_account_id)
    ;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Open-item accounts resolved: %', v_rowcount;


    /* ================================================================
       STEP 2 — Base fact lines.
       Pull all Fact_Acct lines up to p_date for the resolved accounts.
       All amount expressions are centralised here.

       FIX (moderate) — AD_Client_ID filter added for tenant isolation.
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_fact_base;
    CREATE TEMPORARY TABLE tmp_oib_fact_base ON COMMIT DROP AS
    SELECT
        /* --- Mandatory dimensions --- */
        fa.Fact_Acct_ID                                                AS fact_acct_id,
        fa.AD_Client_ID                                                AS ad_client_id,
        fa.AD_Org_ID                                                   AS ad_org_id,
        fa.C_AcctSchema_ID                                             AS c_acctschema_id,
        fa.Account_ID                                                  AS account_id,
        fa.C_BPartner_ID                                               AS c_bpartner_id,   -- may be NULL
        fa.C_Currency_ID                                               AS c_currency_id,

        /* --- Open Item fields --- */
        fa.OpenItemKey                                                 AS openitemkey,
        fa.IsOpenItemsReconciled                                       AS isopenitemsreconciled,
        fa.OI_TrxType                                                  AS oi_trxtype,
        fa.OI_OpenAmount                                               AS oi_openamount,

        /* --- Accounting amounts --- */
        COALESCE(fa.AmtAcctDr, 0)                                      AS amt_acct_dr,
        COALESCE(fa.AmtAcctCr, 0)                                      AS amt_acct_cr,
        (COALESCE(fa.AmtAcctDr, 0) - COALESCE(fa.AmtAcctCr, 0))       AS posting_amt_acct,

        /* --- Source amounts --- */
        COALESCE(fa.AmtSourceDr, 0)                                    AS amt_source_dr,
        COALESCE(fa.AmtSourceCr, 0)                                    AS amt_source_cr,
        (COALESCE(fa.AmtSourceDr, 0) - COALESCE(fa.AmtSourceCr, 0))   AS posting_amt_source,

        /* --- Detail fields --- */
        fa.DateAcct                                                    AS dateacct,
        fa.DocumentNo                                                  AS documentno,
        fa.Description                                                 AS description
    FROM Fact_Acct fa
             JOIN tmp_oib_accounts oa
                  ON oa.account_id = fa.Account_ID
    WHERE TRUE
      AND fa.AD_Client_ID    = v_ad_client_id                          -- FIX: tenant isolation
      AND fa.DateAcct       <= p_date
      AND fa.C_AcctSchema_ID = p_c_acctschema_id
      AND fa.AD_Org_ID       = p_ad_org_id
      AND (p_c_bpartner_id IS NULL OR fa.C_BPartner_ID = p_c_bpartner_id)
      AND (p_c_currency_id  IS NULL OR fa.C_Currency_ID = p_c_currency_id)
    ;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Base fact lines loaded: %', v_rowcount;


    /* ================================================================
       STEP 3 — Summary aggregation (Level 1).
       Group by the mandatory dimensions.
       OpenItemKey is NOT a grouping key here — we aggregate across all
       keys per account / partner / currency to get the account-level view.

       NOTE: c_bpartner_id can be NULL; NULLs group together correctly
       in GROUP BY — no special handling needed here.
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_summary;
    CREATE TEMPORARY TABLE tmp_oib_summary ON COMMIT DROP AS
    SELECT
        f.ad_client_id,
        f.ad_org_id,
        f.c_acctschema_id,
        f.account_id,
        f.c_bpartner_id,                                               -- NULL is a valid group
        f.c_currency_id,

        /* --- GL balance (must match Trial Balance) --- */
        SUM(f.posting_amt_acct)                                        AS gl_balance_amt,
        SUM(f.posting_amt_source)                                      AS gl_balance_source_amt,

        /* --- Open Item measures --- */
        SUM(COALESCE(f.oi_openamount, 0))                              AS oi_open_amt,

        SUM(
                CASE
                    WHEN f.isopenitemsreconciled = 'N'
                        THEN COALESCE(f.oi_openamount, 0)
                        ELSE 0
                END
        )                                                              AS oi_open_amt_unreconciled,

        /* --- Aging anchor --- */
        MIN(
                CASE
                    WHEN f.isopenitemsreconciled = 'N'
                        THEN f.dateacct
                        ELSE NULL
                END
        )                                                              AS oldest_open_dateacct,

        /* --- Diagnostics --- */
        COUNT(*)                                                       AS total_fact_lines,
        COUNT(*) FILTER (WHERE f.isopenitemsreconciled = 'N')          AS open_fact_lines
    FROM tmp_oib_fact_base f
    GROUP BY
        f.ad_client_id,
        f.ad_org_id,
        f.c_acctschema_id,
        f.account_id,
        f.c_bpartner_id,                                               -- NULL groups with NULL — correct
        f.c_currency_id
    ;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Summary rows computed: %', v_rowcount;


    /* ================================================================
       STEP 4 — Suppress fully balanced / reconciled accounts.

       FIX (critical) — Removed the third condition (ABS(gl_balance_amt)
       <= tolerance). That condition was incorrectly suppressing accounts
       that are GL-balanced but carry an OI integrity error
       (gl_balance = 0 yet oi_open_amt ≠ 0). Those must surface as
       is_reconciliation_error = 'Y'.

       An account is suppressed only when BOTH of the following are true:
         1. No unreconciled open items           ABS(oi_open_amt_unreconciled) <= tolerance
         2. No GL-vs-OI integrity discrepancy    ABS(gl_balance_amt - oi_open_amt) <= tolerance
       ================================================================ */
    DELETE FROM tmp_oib_summary
    WHERE ABS(oi_open_amt_unreconciled)     <= p_tolerance
      AND ABS(gl_balance_amt - oi_open_amt) <= p_tolerance
    ;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Balanced accounts suppressed: %', v_rowcount;


    /* ================================================================
       STEP 5 — Assemble final report table.
       Combines:
         - Level 'SUMMARY' rows  (always)
         - Level 'DETAIL'  rows  (only when p_show_details = 'Y')
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_final;
    CREATE TEMPORARY TABLE tmp_oib_final ON COMMIT DROP AS

        /* ----------------------------------------------------------
           SUMMARY rows
           ---------------------------------------------------------- */
        SELECT
            'SUMMARY'::text                                            AS report_level,

            s.c_acctschema_id,
            s.ad_org_id,
            s.account_id,
            oa.account_value,
            oa.account_name,
            s.c_bpartner_id,
            s.c_currency_id,

            /* --- Summary amounts --- */
            s.oi_open_amt,
            s.oi_open_amt_unreconciled,
            s.oldest_open_dateacct,

            /* --- Data quality flags --- */
            CASE
                WHEN ABS(s.oi_open_amt_unreconciled) > p_tolerance
                    THEN 'Y'
                    ELSE 'N'
            END::char(1)                                               AS has_open_items,

            CASE
                WHEN ABS(s.gl_balance_amt - s.oi_open_amt) > p_tolerance
                    THEN 'Y'
                    ELSE 'N'
            END::char(1)                                               AS is_reconciliation_error,

            s.gl_balance_amt,
            s.gl_balance_source_amt,
            (s.gl_balance_amt - s.oi_open_amt)                         AS gl_vs_oi_diff,

            /* --- Detail columns: NULL at summary level --- */
            NULL::numeric                                              AS fact_acct_id,
            NULL::date                                                 AS dateacct,
            NULL::text                                                 AS documentno,
            NULL::varchar                                              AS oi_trxtype,
            NULL::varchar                                              AS openitemkey,
            NULL::char(1)                                              AS isopenitemsreconciled,
            NULL::numeric                                              AS amt_acct_dr,
            NULL::numeric                                              AS amt_acct_cr,
            NULL::numeric                                              AS amt_source_dr,
            NULL::numeric                                              AS amt_source_cr,
            NULL::numeric                                              AS oi_openamount,
            NULL::text                                                 AS description,

            /* --- Diagnostics --- */
            s.total_fact_lines,
            s.open_fact_lines,
            NULL::bigint                                               AS overall_count   -- patched in STEP 6

        FROM tmp_oib_summary s
                 JOIN tmp_oib_accounts oa
                      ON oa.account_id = s.account_id

        UNION ALL

        /* ----------------------------------------------------------
           DETAIL rows — only when p_show_details = 'Y'.
           Joined back to summary to ensure we only show detail for
           accounts that survived the suppression step (STEP 4).

           FIX (critical) — NULL-safe join on c_bpartner_id using
           IS NOT DISTINCT FROM so that journal lines with no partner
           (c_bpartner_id IS NULL) correctly match their summary row
           instead of being silently dropped.

           FIX (moderate) — c_acctschema_id added to the join to
           prevent cross-schema row leakage if the function is ever
           extended to multi-schema use.
           ---------------------------------------------------------- */
        SELECT
            'DETAIL'::text                                             AS report_level,

            f.c_acctschema_id,
            f.ad_org_id,
            f.account_id,
            oa.account_value,
            oa.account_name,
            f.c_bpartner_id,
            f.c_currency_id,

            /* --- Summary amounts: NULL at detail level --- */
            NULL::numeric                                              AS oi_open_amt,
            NULL::numeric                                              AS oi_open_amt_unreconciled,
            NULL::date                                                 AS oldest_open_dateacct,
            NULL::char(1)                                              AS has_open_items,
            NULL::char(1)                                              AS is_reconciliation_error,
            NULL::numeric                                              AS gl_balance_amt,
            NULL::numeric                                              AS gl_balance_source_amt,
            NULL::numeric                                              AS gl_vs_oi_diff,

            /* --- Detail columns --- */
            f.fact_acct_id,
            f.dateacct,
            f.documentno,
            f.oi_trxtype,
            f.openitemkey,
            f.isopenitemsreconciled,
            f.amt_acct_dr,
            f.amt_acct_cr,
            f.amt_source_dr,
            f.amt_source_cr,
            f.oi_openamount,
            f.description,

            /* --- Diagnostics --- */
            NULL::bigint                                               AS total_fact_lines,
            NULL::bigint                                               AS open_fact_lines,
            NULL::bigint                                               AS overall_count   -- patched in STEP 6

        FROM tmp_oib_fact_base f
                 JOIN tmp_oib_accounts oa
                      ON oa.account_id = f.account_id
                 /* Only emit detail for accounts that survived suppression */
                 JOIN tmp_oib_summary s
                      ON  s.c_acctschema_id  = f.c_acctschema_id      -- FIX: schema-scoped join
                      AND s.account_id       = f.account_id
                      AND s.c_bpartner_id    IS NOT DISTINCT FROM f.c_bpartner_id  -- FIX: NULL-safe
                      AND s.c_currency_id    = f.c_currency_id
        WHERE p_show_details = 'Y'
          AND (
              p_only_unreconciled = 'N'
              OR f.isopenitemsreconciled = 'N'
          )
    ;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Final report rows assembled (before overall_count patch): %', v_rowcount;


    /* ================================================================
       STEP 6 — Patch overall_count.

       FIX (moderate) — COUNT(*) OVER () inside a UNION ALL evaluates
       per-branch, not over the combined result set. We compute the true
       total row count here and update every row with a single pass,
       ensuring the diagnostic column is consistent across all rows.
       ================================================================ */
    SELECT COUNT(*) INTO v_overall FROM tmp_oib_final;

    UPDATE tmp_oib_final SET overall_count = v_overall;

    RAISE NOTICE 'overall_count patched: %', v_overall;


    /* ================================================================
       STEP 7 — Return result.
       Order:
         - Account value (alphabetical)
         - Partner (NULLs last)
         - Currency
         - SUMMARY before DETAIL within each group
         - Within DETAIL: OpenItemKey, then DateAcct
       ================================================================ */
    RETURN QUERY
        SELECT
            r.report_level,
            r.c_acctschema_id,
            r.ad_org_id,
            r.account_id,
            r.account_value,
            r.account_name,
            r.c_bpartner_id,
            r.c_currency_id,
            r.oi_open_amt,
            r.oi_open_amt_unreconciled,
            r.oldest_open_dateacct,
            r.has_open_items,
            r.is_reconciliation_error,
            r.gl_balance_amt,
            r.gl_balance_source_amt,
            r.gl_vs_oi_diff,
            r.fact_acct_id,
            r.dateacct,
            r.documentno,
            r.oi_trxtype,
            r.openitemkey,
            r.isopenitemsreconciled,
            r.amt_acct_dr,
            r.amt_acct_cr,
            r.amt_source_dr,
            r.amt_source_cr,
            r.oi_openamount,
            r.description,
            r.total_fact_lines,
            r.open_fact_lines,
            r.overall_count
        FROM tmp_oib_final r
        ORDER BY
            r.account_value,
            r.c_bpartner_id  NULLS LAST,
            r.c_currency_id,
            /* SUMMARY row always comes before its DETAIL rows */
            CASE r.report_level WHEN 'SUMMARY' THEN 0 ELSE 1 END,
            r.openitemkey    NULLS LAST,
            r.dateacct       NULLS LAST
    ;

END;
$BODY$
;