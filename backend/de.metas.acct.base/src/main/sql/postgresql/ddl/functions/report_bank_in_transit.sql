-- Bank in Transit
-- Fact_Acct.AD_Table_ID: C_Payment, GL_Journal, C_BankStatement
-- => common dimension: C_Payment_ID

DROP FUNCTION IF EXISTS de_metas_acct.report_bank_in_transit
(
    numeric,
    numeric,
    date
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.report_bank_in_transit(
    p_C_ElementValue_ID numeric,
    p_C_AcctSchema_ID   numeric = NULL,
    p_EndDate           date = NULL
)
    RETURNS table
            (
                accountno       varchar,
                dateacct        date,
                amtacctdr       numeric,
                amtacctcr       numeric,
                rolling_balance numeric,
                tablename       text,
                record_id       numeric,
                documentno      varchar,
                doctype         varchar,
                docstatus       varchar,
                bpartner        text,
                payment_docno   varchar,
                c_payment_id    numeric,
                aggrKey         text
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF p_C_ElementValue_ID IS NULL THEN
        RAISE EXCEPTION 'No ElementValue provided';
    END IF;

    -- If no accounting schema provided but only one available, use it
    IF (p_C_AcctSchema_ID IS NULL OR p_C_AcctSchema_ID <= 0) AND (SELECT COUNT(1) FROM metasfresh.public.c_acctschema WHERE isactive = 'Y') = 1 THEN
        p_C_AcctSchema_ID = (SELECT c_acctschema_id FROM metasfresh.public.c_acctschema WHERE isactive = 'Y');
    END IF;

    DROP TABLE IF EXISTS tmp_fact_acct_prefiltered_bank_in_transit;
    CREATE TEMPORARY TABLE tmp_fact_acct_prefiltered_bank_in_transit
    (
        fact_acct_id numeric,
        tablename    text,
        c_payment_id numeric
    );

    INSERT INTO tmp_fact_acct_prefiltered_bank_in_transit (fact_acct_id, tablename, c_payment_id)
    SELECT fa.fact_acct_id,
           t.tablename,
           (CASE
                WHEN t.tablename = 'C_Payment'                                     THEN fa.record_id
                WHEN t.tablename = 'C_BankStatement' AND fa.subline_id IS NOT NULL THEN (SELECT bslr.c_payment_id FROM c_bankstatementline_ref bslr WHERE bslr.c_bankstatementline_ref_id = fa.subline_id)
                WHEN t.tablename = 'C_BankStatement' AND fa.line_id IS NOT NULL    THEN (SELECT bsl.c_payment_id FROM c_bankstatementline bsl WHERE bsl.c_bankstatementline_id = fa.line_id)
            END) AS c_payment_id
    FROM fact_acct fa
             INNER JOIN ad_table t ON t.ad_table_id = fa.ad_table_id
    WHERE fa.account_id = p_C_ElementValue_ID
      AND fa.c_acctschema_id = p_C_AcctSchema_ID
      AND (p_EndDate IS NULL OR fa.dateacct <= p_EndDate);

    CREATE UNIQUE INDEX ON tmp_fact_acct_prefiltered_bank_in_transit (fact_acct_id);

    --
    --
    --

    DROP TABLE IF EXISTS tmp_fact_acct_bank_in_transit;
    CREATE TEMPORARY TABLE tmp_fact_acct_bank_in_transit AS
    SELECT (CASE
                WHEN p.c_payment_id IS NOT NULL THEN LEAST(p.c_payment_id, p.reversal_id)::text
                                                ELSE (t.tablename || '_' || t.record_id)::text
            END) AS aggregationKey,
           t.*
    FROM (SELECT sel.c_payment_id,
                 sel.tablename,
                 ev.value AS accountno,
                 ev.name  AS accountname,
                 fa.*
          FROM tmp_fact_acct_prefiltered_bank_in_transit sel
                   INNER JOIN fact_acct fa ON (fa.fact_acct_id = sel.fact_acct_id)
                   INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
             --
         ) t
             LEFT OUTER JOIN c_payment p ON p.c_payment_id = t.c_payment_id;

    CREATE INDEX ON tmp_fact_acct_bank_in_transit (c_acctschema_id, aggregationKey, accountno);

    --
    -- Aggregate
    --

    DROP TABLE IF EXISTS tmp_fact_acct_unbalanced_agg_bank_in_transit;
    CREATE TEMPORARY TABLE tmp_fact_acct_unbalanced_agg_bank_in_transit AS
    SELECT t.c_acctschema_id, t.aggregationKey, t.accountno
    FROM tmp_fact_acct_bank_in_transit t
    GROUP BY t.c_acctschema_id, t.aggregationKey, t.accountno
    HAVING COALESCE(SUM(t.amtacctdr), 0) != COALESCE(SUM(t.amtacctcr), 0);

    CREATE UNIQUE INDEX ON tmp_fact_acct_unbalanced_agg_bank_in_transit (c_acctschema_id, aggregationKey, accountno);

    --
    --
    --

    RETURN QUERY SELECT --
                        t.accountno,
                        t.dateacct::date,
                        t.amtacctdr,
                        t.amtacctcr,
                        SUM(t.amtacctdr - t.amtacctcr) OVER (PARTITION BY t.accountno ORDER BY t.aggregationkey, t.dateacct, t.fact_acct_id) AS rolling_balance,
                        t.tablename,
                        t.record_id,
                        t.documentno,
                        (SELECT rl.name FROM ad_ref_list rl WHERE rl.ad_reference_id = 183 AND rl.value = t.docbasetype)                     AS doctype,
                        (SELECT rl.name FROM ad_ref_list rl WHERE rl.ad_reference_id = 131 AND rl.value = t.docstatus)                       AS docstatus,
                        (SELECT bp.value || '_' || bp.name FROM c_bpartner bp WHERE bp.c_bpartner_id = t.c_bpartner_id)                      AS bpartner,
                        p.documentno                                                                                                         AS payment_docno,
                        p.c_payment_id,
                        t.aggregationkey
                 FROM tmp_fact_acct_bank_in_transit t
                          LEFT OUTER JOIN c_payment p ON p.c_payment_id = t.c_payment_id
                 WHERE TRUE
                   AND EXISTS(SELECT 1
                              FROM tmp_fact_acct_unbalanced_agg_bank_in_transit agg
                              WHERE agg.c_acctschema_id = t.c_acctschema_id
                                AND agg.aggregationKey = t.aggregationKey
                                AND agg.accountno = t.accountno)
                 ORDER BY t.aggregationkey, t.dateacct, t.fact_acct_id;

END
$$
;
