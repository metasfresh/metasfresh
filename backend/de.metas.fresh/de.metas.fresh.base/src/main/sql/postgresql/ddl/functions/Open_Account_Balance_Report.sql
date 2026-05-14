DROP FUNCTION IF EXISTS report.open_account_balance_report(
    p_date            date,
    p_c_acctschema_id numeric,
    p_ad_org_id       numeric,
    p_account_id      numeric,
    p_c_bpartner_id   numeric,
    p_tolerance       numeric,
    p_showdetails     varchar
)
;

CREATE OR REPLACE FUNCTION report.open_account_balance_report(
    p_date            date,
    p_c_acctschema_id numeric,
    p_ad_org_id       numeric,
    p_account_id      numeric DEFAULT NULL,
    p_c_bpartner_id   numeric DEFAULT NULL,
    p_tolerance       numeric DEFAULT 0.01,
    p_showdetails     varchar DEFAULT 'N'
)
    RETURNS TABLE
            (
                -- -------------------------------------------------------
                -- Level indicator
                -- -------------------------------------------------------
                report_level             text,    -- 'SUMMARY' | 'DETAIL'

                -- -------------------------------------------------------
                -- Mandatory dimensions (both levels)
                -- -------------------------------------------------------
                c_acctschema_id          numeric,
                ad_org_id                numeric,
                organization             text,
                account_id               numeric,
                account_value            varchar,
                account_name             varchar,
                c_bpartner_id            numeric,
                partnerValue             varchar,
                partnerName              varchar,
                main_currency_id         numeric,
                main_currency            char(3),
                c_currency_id            numeric,
                currency                 char(3),

                -- -------------------------------------------------------
                -- Summary-level amounts
                -- -------------------------------------------------------
                oi_open_amt              numeric, -- SUM(OI_OpenAmount) of surviving keys
                oi_open_amt_unreconciled numeric, -- SUM where IsOpenItemsReconciled='N'
                oldest_open_dateacct     timestamp without time zone,

                -- -------------------------------------------------------
                -- Data-quality flags (summary level)
                -- -------------------------------------------------------
                has_open_items           char(1), -- 'Y' if gl_open_balance_amt > tolerance

                -- -------------------------------------------------------
                -- Detail-level columns (populated only when p_show_details='Y')
                -- -------------------------------------------------------
                fact_acct_id             numeric,
                dateacct                 timestamp without time zone,
                documentno               text,
                oi_trxtype               varchar,
                openitemkey              varchar,
                isopenitemsreconciled    char(1),
                amt_acct_dr              numeric,
                amt_acct_cr              numeric,
                amt_source_dr            numeric,
                amt_source_cr            numeric,
                description              text,
                oi_openamount            numeric,

                -- -------------------------------------------------------
                -- Diagnostics
                -- -------------------------------------------------------
                total_fact_lines         numeric,
                open_fact_lines          numeric,
                overall_count            numeric,
                param_acct_schema        text,
                param_account            text,
                param_bpartner           text
            )
    LANGUAGE plpgsql
AS
$BODY$
    # VARIABLE_CONFLICT USE_COLUMN
DECLARE
    v_rowcount          numeric;
    v_main_currency_id  numeric;
    v_main_currency     char(3);
    v_overall           numeric;
    v_param_acct_schema text;
    v_param_account     text;
    v_param_bpartner    text;
BEGIN

    /* ================================================================
         Resolve accounting currency
       ================================================================ */

    SELECT s.c_currency_id, s.name
    INTO v_main_currency_id,v_param_acct_schema
    FROM C_AcctSchema s
    WHERE s.C_AcctSchema_ID = p_c_acctschema_id;

    RAISE NOTICE 'Resolved C_Currency_ID: % for C_AcctSchema_ID: %', v_main_currency_id, p_c_acctschema_id;


    SELECT c.iso_code
    INTO v_main_currency
    FROM c_currency c
    WHERE c.c_currency_id = v_main_currency_id;

    SELECT c.value || ' ' || c.name
    INTO v_param_account
    FROM c_elementvalue c
    WHERE c.c_elementvalue_id = p_account_id;

    SELECT bp.value || ' ' || bp.name
    INTO v_param_bpartner
    FROM c_bpartner bp
    WHERE bp.c_bpartner_id = p_c_bpartner_id;


    /* ================================================================
       STEP 1 — Resolve open-item accounts.
       Only accounts with C_ElementValue.IsOpenItem = 'Y' participate.
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_accounts;
    CREATE TEMPORARY TABLE tmp_oib_accounts ON COMMIT DROP AS
    SELECT ev.C_ElementValue_ID AS account_id,
           ev.Value             AS account_value,
           ev.Name              AS account_name
    FROM C_ElementValue ev
    WHERE ev.IsOpenItem = 'Y'
      AND ev.IsActive = 'Y'
      AND ev.AD_Org_ID IN (p_ad_org_id, 0)
      AND (p_account_id IS NULL OR ev.C_ElementValue_ID = p_account_id);

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'STEP 1 — Open-item accounts resolved: %', v_rowcount;


    /* ================================================================
       STEP 2 — Base fact lines.
       Pull all Fact_Acct lines up to p_date for the resolved accounts.
       All amount expressions are centralised here.
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_fact_base;
    CREATE TEMPORARY TABLE tmp_oib_fact_base ON COMMIT DROP AS
    SELECT fa.Fact_Acct_ID             AS fact_acct_id,
           fa.AD_Client_ID             AS ad_client_id,
           fa.AD_Org_ID                AS ad_org_id,
           o.value || ' ' || o.name    AS organization,
           fa.C_AcctSchema_ID          AS c_acctschema_id,
           fa.Account_ID               AS account_id,
           fa.C_BPartner_ID            AS c_bpartner_id,
           bp.value                    AS partnerValue,
           bp.name                     AS partnerName,
           fa.C_Currency_ID            AS c_currency_id,
           c.iso_code                  AS currency,

        /* --- Open Item fields --- */
           fa.OpenItemKey              AS openitemkey,
           fa.IsOpenItemsReconciled    AS isopenitemsreconciled,
           fa.OI_TrxType               AS oi_trxtype,
           fa.OI_OpenAmount            AS oi_openamount,

        /* --- Accounting amounts --- */
           COALESCE(fa.AmtAcctDr, 0)   AS amt_acct_dr,
           COALESCE(fa.AmtAcctCr, 0)   AS amt_acct_cr,

        /* --- Source amounts --- */
           COALESCE(fa.AmtSourceDr, 0) AS amt_source_dr,
           COALESCE(fa.AmtSourceCr, 0) AS amt_source_cr,

        /* --- Detail fields --- */
           fa.DateAcct                 AS dateacct,
           fa.DocumentNo               AS documentno,
           fa.Description              AS description
    FROM Fact_Acct fa
             LEFT JOIN c_bpartner bp ON fa.c_bpartner_id = bp.c_bpartner_id
             JOIN c_currency c ON fa.c_currency_id = c.c_currency_id
             JOIN AD_org o ON fa.ad_org_id = o.ad_org_id
             JOIN tmp_oib_accounts oa
                  ON oa.account_id = fa.Account_ID
    WHERE TRUE
      AND fa.DateAcct <= p_date
      AND fa.C_AcctSchema_ID = p_c_acctschema_id
      AND fa.AD_Org_ID = p_ad_org_id
      AND (p_c_bpartner_id IS NULL OR fa.C_BPartner_ID = p_c_bpartner_id);

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'STEP 2 — Base fact lines loaded: %', v_rowcount;


    /* ================================================================
       STEP 3 — Per-OpenItemKey aggregation.
       This is the atomic matching unit:
         (account, bpartner, currency, openitemkey)
       The authoritative clearing signal is gl_key_balance_amt — the
       net DR-CR per key.  This catches manual GL journals that offset
       an invoice on the same key without going through the normal
       payment/allocation process.
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_keys;
    CREATE TEMPORARY TABLE tmp_oib_keys ON COMMIT DROP AS
    SELECT f.ad_client_id,
           f.ad_org_id,
           f.organization,
           f.c_acctschema_id,
           f.account_id,
           f.c_bpartner_id,
           f.c_currency_id,
           f.currency,
           f.openitemkey,

        /* OI helper amounts (may lag for manual journals) */
           SUM(COALESCE(f.oi_openamount, 0))                     AS oi_open_amt,
           SUM(
                   CASE
                       WHEN f.isopenitemsreconciled = 'N'
                           THEN COALESCE(f.oi_openamount, 0)
                           ELSE 0
                   END
           )                                                     AS oi_open_amt_unreconciled,

        /* aging anchor */
           MIN(
                   CASE
                       WHEN f.isopenitemsreconciled = 'N' THEN f.dateacct
                   END
           )                                                     AS oldest_open_dateacct,

           COUNT(*)                                              AS total_fact_lines,
           COUNT(*) FILTER (WHERE f.isopenitemsreconciled = 'N') AS open_fact_lines
    FROM tmp_oib_fact_base f
    GROUP BY f.ad_client_id,
             f.ad_org_id,
             f.organization,
             f.c_acctschema_id,
             f.account_id,
             f.c_bpartner_id,
             f.c_currency_id,
             f.currency,
             f.openitemkey;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'STEP 3 — OpenItemKey groups computed: %', v_rowcount;


    /* ================================================================
       STEP 4 — Suppress fully balanced / reconciled accounts.
       ================================================================ */

    DELETE
    FROM tmp_oib_keys
    WHERE ABS(oi_open_amt_unreconciled) <= p_tolerance;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'STEP 4 — Cleared key groups suppressed: %', v_rowcount;


    /* ================================================================
       STEP 5 — Roll up surviving keys to account-level summary.
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_summary;
    CREATE TEMPORARY TABLE tmp_oib_summary ON COMMIT DROP AS
    SELECT k.ad_client_id,
           k.ad_org_id,
           k.organization,
           k.c_acctschema_id,
           k.account_id,
           k.c_bpartner_id,
           bp.value                        AS partnerValue,
           bp.name                         AS partnerName,

           k.c_currency_id,
           k.currency,

           SUM(k.oi_open_amt)              AS oi_open_amt,
           SUM(k.oi_open_amt_unreconciled) AS oi_open_amt_unreconciled,
           MIN(k.oldest_open_dateacct)     AS oldest_open_dateacct,

           SUM(k.total_fact_lines)         AS total_fact_lines,
           SUM(k.open_fact_lines)          AS open_fact_lines
    FROM tmp_oib_keys k
             LEFT JOIN c_bpartner bp ON k.c_bpartner_id = bp.c_bpartner_id
    GROUP BY k.ad_client_id,
             k.ad_org_id,
             k.organization,
             k.c_acctschema_id,
             k.account_id,
             k.c_bpartner_id,
             bp.value,
             bp.name,
             k.c_currency_id,
             k.currency;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'STEP 5 — Account-level summary rows: %', v_rowcount;


    /* ================================================================
       STEP 6 — Assemble final report table.
       Combines:
         - Level 'SUMMARY' rows  (always)
         - Level 'DETAIL'  rows  (only when p_show_details = 'Y')

       DETAIL lines join to tmp_oib_keys (not tmp_oib_summary) so that
       each fact line matches exactly its own key group — no fan-out /
       duplicate rows even when multiple keys exist per account/currency.
       ================================================================ */
    DROP TABLE IF EXISTS tmp_oib_final;
    CREATE TEMPORARY TABLE tmp_oib_final ON COMMIT DROP AS

        /* ----------------------------------------------------------
           SUMMARY rows
           ---------------------------------------------------------- */
    SELECT 'SUMMARY'::text                   AS report_level,
           s.c_acctschema_id,
           s.ad_org_id,
           s.organization,
           s.account_id,
           oa.account_value,
           oa.account_name,
           s.c_bpartner_id,
           s.partnerValue,
           s.partnerName,
           s.c_currency_id,
           s.currency,

        /* summary amounts */
           s.oi_open_amt,
           s.oi_open_amt_unreconciled,
           s.oldest_open_dateacct,

        /* data quality flag — GL is authoritative */
           CASE
               WHEN ABS(s.oi_open_amt_unreconciled) > p_tolerance
                   THEN 'Y'
                   ELSE 'N'
           END::char(1)                      AS has_open_items,

        /* detail columns: NULL at summary level */
           NULL::numeric                     AS fact_acct_id,
           NULL::timestamp without time zone AS dateacct,
           NULL::text                        AS documentno,
           NULL::varchar                     AS oi_trxtype,
           NULL::varchar                     AS openitemkey,
           NULL::char(1)                     AS isopenitemsreconciled,
           NULL::numeric                     AS amt_acct_dr,
           NULL::numeric                     AS amt_acct_cr,
           NULL::numeric                     AS amt_source_dr,
           NULL::numeric                     AS amt_source_cr,
           NULL::numeric                     AS oi_openamount,
           NULL::text                        AS description,

        /* diagnostics */
           s.total_fact_lines,
           s.open_fact_lines,
           NULL::numeric                     AS overall_count -- patched in STEP 7

    FROM tmp_oib_summary s
             JOIN tmp_oib_accounts oa
                  ON oa.account_id = s.account_id

    UNION ALL

    /* ----------------------------------------------------------
       DETAIL rows — only when p_show_details = 'Y'.
       Join to tmp_oib_keys (includes openitemkey) to avoid
       fan-out duplicates when multiple keys exist per account.
       ---------------------------------------------------------- */
    SELECT 'DETAIL'::text                    AS report_level,

           f.c_acctschema_id,
           f.ad_org_id,
           f.organization,
           f.account_id,
           oa.account_value,
           oa.account_name,
           f.c_bpartner_id,
           f.partnerValue,
           f.partnerName,
           f.c_currency_id,
           f.currency,

        /* summary amounts: NULL at detail level */
           NULL::numeric                     AS oi_open_amt,
           NULL::numeric                     AS oi_open_amt_unreconciled,
           NULL::timestamp without time zone AS oldest_open_dateacct,
           NULL::char(1)                     AS has_open_items,

        /* detail columns */
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

        /* diagnostics: NULL at detail level */
           NULL::numeric                     AS total_fact_lines,
           NULL::numeric                     AS open_fact_lines,
           NULL::numeric                     AS overall_count -- patched in STEP 7

    FROM tmp_oib_fact_base f
             JOIN tmp_oib_accounts oa
                  ON oa.account_id = f.account_id
        /* Join to surviving key groups — exact match including openitemkey.
           This is the critical join that prevents fan-out duplicates. */
             JOIN tmp_oib_keys k
                  ON k.c_acctschema_id = f.c_acctschema_id
                      AND k.account_id = f.account_id
                      AND k.c_bpartner_id IS NOT DISTINCT FROM f.c_bpartner_id
                      AND k.c_currency_id = f.c_currency_id
                      AND k.openitemkey IS NOT DISTINCT FROM f.openitemkey
    WHERE p_showdetails = 'Y';

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'STEP 6 — Final rows assembled (before overall_count patch): %', v_rowcount;


    /* ================================================================
       STEP 7 — Patch overall_count.
       ================================================================ */
    SELECT COUNT(*) INTO v_overall FROM tmp_oib_final;
    UPDATE tmp_oib_final SET overall_count = v_overall;

    RAISE NOTICE 'STEP 7 — overall_count patched: %', v_overall;


    /* ================================================================
       STEP 8 — Return result.
       Order:
         - Account value (alphabetical)
         - Partner (NULLs last)
         - Currency
         - SUMMARY before DETAIL within each group
         - Within DETAIL: OpenItemKey, then DateAcct
       ================================================================ */
    RETURN QUERY
        SELECT r.report_level,
               r.c_acctschema_id,
               r.ad_org_id,
               r.organization,
               r.account_id,
               r.account_value,
               r.account_name,
               r.c_bpartner_id,
               r.partnerValue,
               r.partnerName,
               v_main_currency_id,
               v_main_currency,
               r.c_currency_id,
               r.currency,
               r.oi_open_amt,
               r.oi_open_amt_unreconciled,
               r.oldest_open_dateacct,
               r.has_open_items,
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
               r.description,
               r.oi_openamount,
               r.total_fact_lines,
               r.open_fact_lines,
               r.overall_count,
               v_param_acct_schema,
               v_param_account,
               v_param_bpartner
        FROM tmp_oib_final r
        ORDER BY r.account_value,
                 r.c_bpartner_id NULLS LAST,
                 r.c_currency_id,
                 r.currency,
                 CASE r.report_level WHEN 'SUMMARY' THEN 0 ELSE 1 END,
                 r.openitemkey NULLS LAST,
                 r.dateacct NULLS LAST;

END;
$BODY$
;