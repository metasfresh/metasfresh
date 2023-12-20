-- NotInvoicedReceipts_Acct - Noch nicht in Rechnung gestellte Wareneingänge
-- Fact_Acct.AD_Table_ID: M_MatchInv, GL_Journal, M_InOut
-- => common dimension: M_InOutLine_ID

DROP FUNCTION IF EXISTS de_metas_acct.report_not_invoiced_receipts
(
    numeric,
    numeric,
    date
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.report_not_invoiced_receipts(
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
                product         text,
                inout_docno     varchar,
                m_inout_id      numeric,
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

    DROP TABLE IF EXISTS tmp_fact_acct_prefiltered_not_invoiced_receipts;
    CREATE TEMPORARY TABLE tmp_fact_acct_prefiltered_not_invoiced_receipts
    (
        fact_acct_id   numeric,
        tablename      text,
        M_InOutLine_ID numeric
    );

    INSERT INTO tmp_fact_acct_prefiltered_not_invoiced_receipts (fact_acct_id, tablename, M_InOutLine_ID)
    SELECT fa.fact_acct_id,
           t.tablename,
           (CASE
                WHEN t.tablename = 'M_MatchInv' THEN (SELECT mi.m_inoutline_id FROM m_matchinv mi WHERE mi.m_matchinv_id = fa.record_id)
                WHEN t.tablename = 'M_InOut'    THEN fa.line_id
               --END) AS c_invoiceline_id
            END) AS M_InOutLine_ID
    FROM fact_acct fa
             INNER JOIN ad_table t ON t.ad_table_id = fa.ad_table_id
    WHERE fa.account_id = p_C_ElementValue_ID
      AND fa.c_acctschema_id = p_C_AcctSchema_ID
      AND (p_EndDate IS NULL OR fa.dateacct <= p_EndDate);

    CREATE UNIQUE INDEX ON tmp_fact_acct_prefiltered_not_invoiced_receipts (fact_acct_id);

    --
    --

    DROP TABLE IF EXISTS tmp_fact_acct_not_invoiced_receipts;
    CREATE TEMPORARY TABLE tmp_fact_acct_not_invoiced_receipts AS
    SELECT (CASE
                WHEN io.m_inout_id IS NOT NULL THEN LEAST(io.m_inout_id, io.reversal_id) || '_' || iol.line::text
                                               ELSE (t.tablename || '_' || t.record_id)::text
            END) AS aggregationKey,
           io.m_inout_id,
           t.*
    FROM (SELECT sel.M_InOutLine_ID,
                 sel.tablename,
                 ev.value AS accountno,
                 ev.name  AS accountname,
                 fa.*
          FROM tmp_fact_acct_prefiltered_not_invoiced_receipts sel
                   INNER JOIN fact_acct fa ON (fa.fact_acct_id = sel.fact_acct_id)
                   INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id) t
             LEFT OUTER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = t.M_InOutLine_ID
             LEFT OUTER JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID;

    CREATE INDEX ON tmp_fact_acct_not_invoiced_receipts (c_acctschema_id, aggregationKey, accountno);

    --
    -- Aggregate
    --
    --

    DROP TABLE IF EXISTS tmp_fact_acct_unbalanced_agg_not_invoiced_receipts;
    CREATE TEMPORARY TABLE tmp_fact_acct_unbalanced_agg_not_invoiced_receipts AS
    SELECT t.accountno,
           io.documentno                                                                            AS invoice_docNo,
           io.dateacct                                                                              AS invoice_dateAcct,
           iol.line                                                                                 AS line,
           (SELECT p.value || '_' || p.name FROM m_product p WHERE p.m_product_id = t.m_product_id) AS product,
           COALESCE(SUM(t.amtacctdr), 0) - COALESCE(SUM(t.amtacctcr), 0)                            AS balance,
           io.m_inout_id,
           t.aggregationKey,
           t.c_acctschema_id,
           ARRAY_AGG(DISTINCT t.dateacct)                                                           AS dateaccts
    FROM tmp_fact_acct_not_invoiced_receipts t
             LEFT OUTER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = t.M_InOutLine_ID
             LEFT OUTER JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID
    GROUP BY t.c_acctschema_id, t.aggregationKey, t.accountno,
             t.m_product_id, io.documentno, io.dateacct, iol.line, io.m_inout_id
    HAVING COALESCE(SUM(t.amtacctdr), 0) != COALESCE(SUM(t.amtacctcr), 0);

    CREATE
        INDEX ON tmp_fact_acct_unbalanced_agg_not_invoiced_receipts (c_acctschema_id, aggregationKey, accountno);
    --
    -- Get detailed report
    --

    RETURN QUERY SELECT --
                        t.accountno,
                        t.dateacct::date,
                        t.amtacctdr,
                        t.amtacctcr,
                        SUM(t.amtacctdr - t.amtacctcr) OVER (ORDER BY t.aggregationkey, t.dateacct, t.fact_acct_id)      AS rolling_balance,
                        t.tablename,
                        t.record_id,
                        t.documentno,
                        (SELECT rl.name FROM ad_ref_list rl WHERE rl.ad_reference_id = 183 AND rl.value = t.docbasetype) AS doctype,
                        (SELECT rl.name FROM ad_ref_list rl WHERE rl.ad_reference_id = 131 AND rl.value = t.docstatus)   AS docstatus,
                        (SELECT bp.value || '_' || bp.name FROM c_bpartner bp WHERE bp.c_bpartner_id = t.c_bpartner_id)  AS bpartner,
                        (SELECT p.value || '_' || p.name FROM m_product p WHERE p.m_product_id = t.m_product_id)         AS product,
                        io.documentno                                                                                    AS inout_docno,
                        io.m_inout_id,
                        t.aggregationkey
                 FROM tmp_fact_acct_not_invoiced_receipts t
                          LEFT OUTER JOIN M_InOutLine iol ON iol.M_InOutLine_ID = t.M_InOutLine_ID
                          LEFT OUTER JOIN M_InOut io ON io.M_InOut_ID = iol.M_InOut_ID
                 WHERE TRUE
                   AND EXISTS(SELECT 1
                              FROM tmp_fact_acct_unbalanced_agg_not_invoiced_receipts agg
                              WHERE agg.c_acctschema_id = t.c_acctschema_id
                                AND agg.aggregationKey = t.aggregationKey
                                AND agg.accountno = t.accountno)
                 ORDER BY t.aggregationkey, t.dateacct, t.fact_acct_id;
END
$$
;
